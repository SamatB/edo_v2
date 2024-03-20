package org.example.utils;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.EmailDto;
import org.example.entity.Participant;
import org.springframework.stereotype.Component;

/**
 * Вспомогательный класс для работы с электронной почтой.
 */
@Component
@Slf4j
public class EmailHelper {

    /**
     * Метод для генерации DTO для отправки электронной почты с уведомлением о приходе Листа Согласования.
     * @param participant сущность участника согласования
     * @param appealNumber номер обращения
     * @return DTO для электронной почты с уведомлением для участника
     */
    public EmailDto getAgreementListEmailDto(Participant participant, String appealNumber) {
        EmailDto emailDto = new EmailDto();
        String emailText = "Добрый день, " + participant.getEmployee().getFioNominative() + "! На ваше имя пришёл Лист Согласования по обращению номер " + appealNumber + ".";

        emailDto.setEmployeeId(participant.getId());
        emailDto.setSubject("Уведомление");
        emailDto.setBody(emailText);
        emailDto.setTo(participant.getEmployee().getEmail());

        return emailDto;
    }
}
