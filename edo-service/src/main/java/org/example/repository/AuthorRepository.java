package org.example.repository;

import org.example.dto.AuthorDto;
import org.example.entity.Author;
import org.example.entity.FilePool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByLastNameStartingWithIgnoreCaseAndFirstNameStartingWithIgnoreCaseAndMiddleNameStartingWithIgnoreCase(String searchLastName, String searchFirstName, String searchMiddleName);
}
