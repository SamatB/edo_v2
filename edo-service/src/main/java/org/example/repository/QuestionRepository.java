package org.example.repository;

import org.example.entity.Appeal;
import org.example.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Репозиторий для работы с сущностью Question.
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByAppeal(Appeal appeal);

}
