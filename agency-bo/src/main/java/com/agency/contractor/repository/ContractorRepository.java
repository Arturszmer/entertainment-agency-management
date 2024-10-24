package com.agency.contractor.repository;

import com.agency.contractor.model.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContractorRepository extends JpaRepository<Contractor, Long>, QuerydslPredicateExecutor<Contractor> {

    Optional<Contractor> findByPesel(String pesel);
    Optional<Contractor> findContractorByPublicId(UUID publicId);
}
