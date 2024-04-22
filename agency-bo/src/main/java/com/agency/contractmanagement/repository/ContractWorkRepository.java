package com.agency.contractmanagement.repository;

import com.agency.contractmanagement.model.contract.ContractWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractWorkRepository extends JpaRepository<ContractWork, Long> {

    Optional<ContractWork> findByContractNumber(String contractNumber);

    @Query("SELECT COUNT(cw) FROM ContractWork cw WHERE YEAR(cw.signDate) = :year")
    int getNumberOfContractsByYear(int year);

}
