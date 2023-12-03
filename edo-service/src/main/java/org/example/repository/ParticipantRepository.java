package org.example.repository;

import org.example.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью Participant
 */
@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}


//TODO разобраться и сделать миграцию - flyway java миграция spring boot