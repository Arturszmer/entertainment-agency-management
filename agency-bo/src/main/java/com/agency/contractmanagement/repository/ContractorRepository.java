package com.agency.contractmanagement.repository;

import com.agency.contractmanagement.model.contractor.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContractorRepository extends JpaRepository<Contractor, Long> {

    Optional<Contractor> findByPesel(String pesel);
    Optional<Contractor> findContractorByPublicId(UUID publicId);
}
