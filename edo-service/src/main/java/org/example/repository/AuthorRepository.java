package org.example.repository;

import org.example.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("SELECT a FROM Author a WHERE LOWER(CONCAT(a.lastName, ' ', a.firstName, ' ', a.middleName)) LIKE LOWER(:search)")
    List<Author> searchByFullName(@Param("search") String search);
}
