package com.agency.contractmanagement.contractnumber.repository;

import com.agency.contractmanagement.contractnumber.model.ContractNumber;
import com.agency.dict.contract.ContractType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractNumberRepository extends JpaRepository<ContractNumber, Long> {

    Optional<ContractNumber> findContractNumberByCompleteNumber(String completeNumber);
    @Query("SELECT MAX(c.number) FROM ContractNumber c " +
            "WHERE c.year = :year " +
            "AND c.contractType = :contractType")
    Optional<Integer> findMaxNumberByContractType(
            @Param("year") int year,
            @Param("contractType") ContractType contractType
    );
}
