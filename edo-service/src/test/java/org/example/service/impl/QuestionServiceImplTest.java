package org.example.service.impl;

import org.example.dto.AppealDto;
import org.example.entity.Appeal;
import org.example.entity.BaseEntity;
import org.example.entity.Question;
import org.example.mapper.AppealMapper;
import org.example.repository.QuestionRepository;
import org.example.service.AppealService;
import org.example.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class QuestionServiceImplTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Mock
    private AppealMapper appealMapper;

    @Mock
    private AppealService appealService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * тест метода для получения списка Вопросов по идентификатору обращения
     */
    @Test
    public void testGetQuestions() {
        Appeal appeal = mock(Appeal.class);

        Question question = mock(Question.class);

        List<Question> questions = List.of(question);

        when(questionRepository.findByAppeal(appealMapper.dtoToEntity(appealService.getAppeal(appeal.getId())))).thenReturn(questions);

        List<Question> questionList= questionService.getQuestions(appeal.getId());

        assertEquals(questions, questionList);

    }

}
