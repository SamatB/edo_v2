package org.example.repository;

import org.example.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью Address.
 */
@Repository
public interface AddressDetailsRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByFullAddress(String fullAddress);
}
