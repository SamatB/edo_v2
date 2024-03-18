package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Question;
import org.example.repository.QuestionRepository;
import org.example.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    /**
     * Получение списка вопросов по идентификатору обращения
     *
     * @param appealId идентификатор обращения
     * @return список вопросов
     */
    public List<Question> getAllQuestionsByAppealId(Long appealId) {
        return questionRepository.findAllByAppealId(appealId);
    }
}
