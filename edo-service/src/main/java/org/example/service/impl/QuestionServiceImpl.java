package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Appeal;
import org.example.entity.Question;
import org.example.mapper.AppealMapper;
import org.example.repository.QuestionRepository;
import org.example.service.AppealService;
import org.example.service.QuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private final AppealService appealService;
    private final AppealMapper appealMapper;


    /**
     * Получаем список вопросов по идентификатору обращения
     * @param appealId - идентификатор обращения
     * @return
     */
    @Override
    public List<Question> getQuestions(Long appealId) {
        return questionRepository.findByAppeal(appealMapper.dtoToEntity(appealService.getAppeal(appealId)));
    }
}
