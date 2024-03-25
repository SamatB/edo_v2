package org.example.mapper;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.QuestionDto;
import org.example.entity.*;
import org.example.enums.StatusType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class QuestionMapperTest {
    @Autowired
    QuestionMapper questionMapper;
    private final ZonedDateTime dateTime = ZonedDateTime.now();

    @Test
    @DisplayName("Should correctly map Question to QuestionDto")
    void questionToQuestionDto() {
        QuestionDto questionDto = getQuestionDto();
        Question question = getQuestion();

        QuestionDto result = questionMapper.entityToDto(question);

        assertNotNull(result.getAppealId());

        assertEquals(questionDto, result);
    }

    @Test
    @DisplayName("Should correctly map QuestionDto to Question")
    void questionDtoToQuestion() {
        QuestionDto questionDto = getQuestionDto();
        Question question = getQuestion();

        Question result = questionMapper.dtoToEntity(questionDto);

        assertNotNull(result.getAppeal());
        log.info(result.getAppeal().getId().toString());
        assertNotNull(result.getAppeal().getQuestions());

        assertEquals(question, result);
        assertEquals(question.getAppeal().getId(), result.getAppeal().getId());
    }

    private QuestionDto getQuestionDto() {
        return QuestionDto.builder()
                .id(1L)
                .summary("Summary")
                .appealId(1L)
                .build();
    }

    private Question getQuestion() {
        return Question.builder()
                .id(1L)
                .summary("Summary")
                .appeal(Appeal.builder()
                        .id(1L)
                        .creationDate(dateTime)
                        .registrationDate(dateTime)
                        .number("1")
                        .reservedNumber("1")
                        .annotation("1")
                        .statusType(StatusType.REGISTERED)
                        .creator(getEmployee())
                        .singers(List.of(getEmployee()))
                        .addressee(List.of(getEmployee()))
                        .nomenclature(getNomenclature())
                        .questions(List.of(Question.builder().id(1L).summary("Summary").build()))
                        .build())
                .build();
    }

    private Employee getEmployee() {
        return Employee.builder()
                .id(1L)
                .firstName("1")
                .lastName("1")
                .addressDetails(getAddressDetails())
                .department(getDepartment())
                .build();
    }

    private Nomenclature getNomenclature() {
        return Nomenclature.builder()
                .department(getDepartment())
                .build();
    }

    private Department getDepartment() {
        return Department.builder()
                .addressDetails(getAddressDetails())
                .build();
    }

    private Address getAddressDetails() {
        return Address.builder()
                .fullAddress("1")
                .build();
    }
}