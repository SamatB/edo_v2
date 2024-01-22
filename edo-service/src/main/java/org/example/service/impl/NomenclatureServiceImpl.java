package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.NomenclatureDto;
import org.example.entity.Nomenclature;
import org.example.mapper.NomenclatureMapper;
import org.example.repository.NomenclatureRepository;
import org.example.service.DepartmentService;
import org.example.service.NomenclatureService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NomenclatureServiceImpl implements NomenclatureService {
    private final NomenclatureRepository nomenclatureRepository;
    private final NomenclatureMapper nomenclatureMapper;
    private final DepartmentService departmentService;

    @Override
    public NomenclatureDto saveNomenclature(NomenclatureDto nomenclatureDto) {
        log.info("запрос на сохранение прилетел");
        return Optional.ofNullable(nomenclatureDto)
                .map(nomenclature -> {
                    nomenclature.setDepartment(
                            Optional.ofNullable(nomenclature.getDepartment())
                                    .map(departmentService::saveDepartment)
                                    .orElse(null));
                    return nomenclature;
                })
                .map(nomenclatureMapper::dtoToEntity)
                .map(nomenclatureRepository::save)
                .map(nomenclatureMapper::entityToDto)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public void deleteNomenclature(Long id) {
        nomenclatureRepository.deleteById(id);
    }

    @Override
    public List<NomenclatureDto> getPaginatedNomenclature(int offset, int size) {
        return nomenclatureRepository.findAll(PageRequest.of(offset, size))
                .getContent()
                .stream()
                .map(nomenclatureMapper::entityToDto)
                .collect(Collectors.toList());
    }

    /**
     * @param id        - айди обрабатываемой сущности
     * @param archivate - true- сущность архиваируется, устанавливается дата архивации
     *                  false - сущность разархивируется, устанавливается null в поле archived_date
     */
    @Override
    public void ArchiveNomenclature(Long id, boolean archivate) {
        if (archivate) {
            nomenclatureRepository.setArchiveDate(id, ZonedDateTime.now());
        } else nomenclatureRepository.setNullLikeNotArchived(id);
    }

    /**
     * метод получения архивной/неархивной версии номенклатуры
     *
     * @param giveMeArchived - если true получаем архивную версию, false - неархивную
     * @return List<NomenclatureDto> - возвращает коллекцию архивных/неархивных сущностей
     */
    @Override
    public List<NomenclatureDto> getArchivedOrNoArchivedNomenclature(boolean giveMeArchived) {
        List<Nomenclature> listOfNomenclature = nomenclatureRepository.findAll();
        if (giveMeArchived) {
            return listOfNomenclature.stream()
                    .filter(x -> x.getArchivedDate() != null)
                    .map(nomenclatureMapper::entityToDto)
                    .collect(Collectors.toList());
        }
        return listOfNomenclature.stream()
                .filter(x -> x.getArchivedDate() == null)
                .map(nomenclatureMapper::entityToDto)
                .collect(Collectors.toList());
    }

    /**
     * метод генерации номера обращения, генерируется по шаблону %ИНДЕКС-%ГОД/2-%НОМЕР, где
     * %ИНДЕКС - значение index
     * %ГОД - текущий год
     * %НОМЕР - значение currentValue
     * @param nomenclature- номенклатура, по которой будет генерироваться номер
     * @return String - возвращает сгенерированный номер обращения
     */
    @Override
    public String generateNumberForAppeal(Nomenclature nomenclature) {
        nomenclatureRepository.incrementCurrentValue();
        Pattern patternIndex = Pattern.compile("%ИНДЕКС");
        Pattern patternYear = Pattern.compile("%ГОД");
        Pattern patternNumber = Pattern.compile("%НОМЕР");
        Matcher matcherIndex = patternIndex.matcher(nomenclature.getTemplate());
        var strIndex = matcherIndex.replaceAll(nomenclature.getIndex());
        Matcher matcherYear = patternYear.matcher(strIndex);
        var strYear = matcherYear.replaceAll(Year.now().toString());
        Matcher matcherNumber = patternNumber.matcher(strYear);
        return matcherNumber.replaceAll(Long.toString(nomenclature.getCurrentValue()));
    }


}
