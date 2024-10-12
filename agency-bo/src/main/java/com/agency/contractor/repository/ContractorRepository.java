package com.agency.contractor.repository;

import com.agency.contractor.model.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContractorRepository extends JpaRepository<Contractor, Long> {

    Optional<Contractor> findByPesel(String pesel);
    Optional<Contractor> findContractorByPublicId(UUID publicId);
}
