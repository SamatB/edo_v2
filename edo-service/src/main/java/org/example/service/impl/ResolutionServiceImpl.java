package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.EmailDto;
import org.example.dto.ResolutionDto;
import org.example.entity.Resolution;
import org.example.enums.StatusType;
import org.example.mapper.ResolutionMapper;
import org.example.repository.ResolutionRepository;
import org.example.service.ResolutionService;
import org.example.enums.ResolutionType;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.example.configuration.RabbitConfiguration.GET_EMAIL_DTO;

/**
 * Сервис для работы с сущностью Resolution.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ResolutionServiceImpl implements ResolutionService {

    private static final String NULL_ERROR = "значение должно быть не null.";
    private static final String VALUE_TYPE_ERROR = "должен быть значением ResolutionType.";
    private static final String VALUE_POSITIVE_ERROR = "значение должно быть положительным.";

    private final ResolutionRepository resolutionRepository;
    private final ResolutionMapper resolutionMapper;
    private final RabbitTemplate rabbitTemplate;

    /**
     * Сохраняет резолюцию в базе данных, обновляя даты создания и последнего действия на текущее время.
     * Статусы связанных вопросов и апелляций также обновляются перед сохранением.
     * В случае успешного сохранения отправляет на почту создателя уведомление о создании резолюции.
     * После чего возвращает DTO обновленной резолюции.
     * Если переданное DTO резолюции равно null, метод выбрасывает исключение IllegalArgumentException.
     *
     * @param resolutionDto DTO резолюции, которую необходимо сохранить. Не должно быть null.
     * @return ResolutionDto DTO обновленной резолюции после сохранения.
     * @throws IllegalArgumentException если переданное DTO резолюции равно null.
     */
    @Override
    @Transactional
    public ResolutionDto saveResolution(ResolutionDto resolutionDto) {
        return Optional.ofNullable(resolutionDto)
                .map(resolutionMapper::dtoToEntity)
                .map(resolution -> {
                    resolution.setCreationDate(ZonedDateTime.now());
                    resolution.setLastActionDate(ZonedDateTime.now());
                    updateStatusQuestionAndAppeal(resolution);
                    return resolution;
                })
                .map(resolution -> {
                    log.info("Идет сохранение Resolution");
                    resolutionRepository.save(resolution);
                    log.info("Сохранение успешно прошло. Подготовка данных для отправки сообщения");

                    rabbitTemplate.convertAndSend(GET_EMAIL_DTO,
                            creatorEmailDtoForRabbit(
                                    resolution.getCreator().getEmail(),
                                    String.valueOf(resolution.getId()),
                                    resolution.getCreator().getFioNominative()
                            )
                    );

                    log.info("Рассылка произведена успешно");

                    return resolution;

                })
                .map(resolutionMapper::entityToDto)
                .orElseThrow(() -> new IllegalArgumentException("Ошибка сохранения резолюции: резолюция не должна быть null"));
    }

    /**
     * Создает объект EmailDto для отправки через RabbitMQ.
     * Метод формирует объект EmailDto для отправки сообщения
     * получателю о создании новой резолюции, в которой он является исполнителем.
     *
     * @param to      Адрес электронной почты получателя.
     * @param subject Идентификатор резолюции, используемый для формирования темы письма.
     * @param text    Имя получателя, используемое в тексте уведомления.
     * @return EmailDto объект, содержащий информацию для отправки электронного письма.
     */
    private EmailDto creatorEmailDtoForRabbit(String to, String subject, String text) {
        EmailDto emailDto = new EmailDto();
        emailDto.setEmail(List.of(to));
        emailDto.setSubject(String.format("Вы создали новую резолюцию под номером %s", subject));
        emailDto.setText(String.format("Добрый день! Уважаемый, %s! Была создана резолюция " +
                "к обращению http://localhost:8761/resolutions/%s в которой вы являетесь исполнителем!", text, subject));

        log.info("Данные готовы и передаются");

        return emailDto;
    }


    /**
     * Обновляет статусы вопроса и связанной апелляции, если они существуют и статус вопроса является "Не зарегистрирован".
     * Статус устанавливается в "На рассмотрении" для обеих сущностей.
     *
     * @param resolution Резолюция, содержащая вопрос, статус которого необходимо обновить.
     *                   Если вопрос связан с апелляцией, статус апелляции также будет обновлен.
     */
    private void updateStatusQuestionAndAppeal(Resolution resolution) {
        Optional.ofNullable(resolution)
                .map(Resolution::getQuestion)
                .filter(question -> question.getStatusType() == null || question.getStatusType().equals(StatusType.NOT_REGISTERED))
                .ifPresent(question -> {
                    question.setStatusType(StatusType.ON_THE_CARPET);
                    log.info("Статус Question изменился \"На рассмотрении\"");
                    Optional.ofNullable(question.getAppeal())
                            .ifPresent(appeal -> {
                                appeal.setStatusType(StatusType.ON_THE_CARPET);
                                log.info("Статус Appeal изменился на \"На рассмотрении\"");
                            });
                });
    }

    /**
     * Переносит резолюцию в архив.
     *
     * @param id идентификатор резолюции
     * @return обновленный объект DTO резолюции
     */
    @Override
    @Transactional
    public ResolutionDto archiveResolution(Long id) {

        try {
            resolutionRepository.archiveResolution(id, ZonedDateTime.now());
            return resolutionMapper.entityToDto(resolutionRepository.findById(id).get());
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    /**
     * Возвращает резолюцию по идентификатору.
     *
     * @param id идентификатор резолюции
     * @return объект DTO резолюции
     */
    @Override
    public ResolutionDto getResolution(Long id) {
        return resolutionRepository.findById(id)
                .map(resolutionMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Resolution with id " + id + " not found"));
    }

    /**
     * Обновляет существующую резолюцию по ID, используя данные из переданного DTO.
     * Дата последнего действия обновляется на текущее время. Также обновляются статусы связанных вопросов и апелляций.
     * В случае успешного обновления возвращает DTO обновленной резолюции.
     * Если резолюция с указанным ID не найдена, метод выбрасывает исключение EntityNotFoundException.
     *
     * @param id            ID резолюции, которую необходимо обновить. Должно соответствовать существующей записи в базе данных.
     * @param resolutionDto DTO с новыми данными для обновления резолюции. Не должно быть null.
     * @return ResolutionDto DTO обновленной резолюции после сохранения.
     * @throws EntityNotFoundException если резолюция с указанным ID не найдена в базе данных.
     */
    @Override
    public ResolutionDto updateResolution(Long id, ResolutionDto resolutionDto) {
        return resolutionRepository.findById(id)
                .map(resolution -> {
                    resolutionMapper.updateEntity(resolutionDto, resolution);
                    resolution.setLastActionDate(ZonedDateTime.now());
                    updateStatusQuestionAndAppeal(resolution);
                    return resolution;
                })
                .map(resolutionRepository::save)
                .map(resolutionMapper::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Resolution with id " + id + " not found"));
    }

    /**
     * Получает List резолюций в зависимости от их типа архивации.
     *
     * @return List резолюций.
     */

    @Override
    @Transactional(readOnly = true)
    public List<ResolutionDto> findResolution(Boolean archivedType) {
        return resolutionRepository.findResolutions(archivedType);
    }

    /**
     * Проверяет корректность полей резолюции
     * Проверяемые поля: type, serialNumber, signerId
     *
     * @param resolutionDto резолюция
     *                      При выявлении некорректных полей метод бросает исключение
     *                      содержащее некорректные поля с описанием ошибок.
     */
    @Override
    public void validateResolution(ResolutionDto resolutionDto) {
        log.info("Валидация резолюции.");

        StringBuilder message = new StringBuilder();

        String type = resolutionDto.getType();
        final String typeError = String.format("Тип резолюции \"%s\" некорректный - ", type);

        if (type == null) {
            log.error(typeError + NULL_ERROR);
            message.append(typeError)
                    .append(NULL_ERROR)
                    .append(System.lineSeparator());
        } else {
            try {
                ResolutionType.valueOf(type);
            } catch (IllegalArgumentException e) {
                log.error(typeError + VALUE_TYPE_ERROR);
                message.append(typeError)
                        .append(VALUE_TYPE_ERROR)
                        .append(System.lineSeparator());
            }
        }

        Long signerId = resolutionDto.getSignerId();
        final String signerIdError = String.format("Идентификатор подписанта \"%d\" некорректный - ", signerId);
        if (signerId == null) {
            log.error(signerIdError + NULL_ERROR);
            message.append(signerIdError)
                    .append(NULL_ERROR)
                    .append(System.lineSeparator());
        } else if (signerId <= 0) {
            log.error(signerIdError + VALUE_POSITIVE_ERROR);
            message.append(signerIdError)
                    .append(VALUE_POSITIVE_ERROR)
                    .append(System.lineSeparator());
        }

        Integer serialNumber = resolutionDto.getSerialNumber();
        final String serialNumberError = String.format("Серийный номер \"%d\" некорректный - ", serialNumber);
        if (serialNumber == null) {
            log.error(serialNumberError + NULL_ERROR);
            message.append(serialNumberError)
                    .append(NULL_ERROR)
                    .append(System.lineSeparator());
        } else if (serialNumber <= 0) {
            log.error(serialNumberError + VALUE_POSITIVE_ERROR);
            message.append(serialNumberError)
                    .append(VALUE_POSITIVE_ERROR)
                    .append(System.lineSeparator());
        }

        if (!message.isEmpty()) {
            throw new IllegalArgumentException(message.toString());
        }
        log.info("Ошибки не выявлены.");
    }
}
