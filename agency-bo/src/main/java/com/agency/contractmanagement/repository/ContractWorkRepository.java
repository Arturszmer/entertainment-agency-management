package com.agency.contractmanagement.repository;

import com.agency.contractmanagement.model.ContractWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContractWorkRepository extends JpaRepository<ContractWork, Long>, QuerydslPredicateExecutor<ContractWork> {

    @Query("SELECT COUNT(cw) FROM ContractWork cw WHERE YEAR(cw.signDate) = :year")
    int getNumberOfContractsByYear(int year);

    Optional<ContractWork> findContractWorkByPublicId(UUID publicId);
    Optional<ContractWork> findContractWorkByContractNumber(String contractNumber);

}
