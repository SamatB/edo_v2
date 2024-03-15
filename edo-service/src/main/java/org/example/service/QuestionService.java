package org.example.service;

import org.example.entity.Question;

import java.util.List;

public interface QuestionService {

    List<Question> getAllQuestionsByAppealId(Long appealId);
}
