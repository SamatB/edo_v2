package org.example.repository;

import org.example.entity.AgreementList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgreementListRepository extends JpaRepository<AgreementList, Long> {
}
