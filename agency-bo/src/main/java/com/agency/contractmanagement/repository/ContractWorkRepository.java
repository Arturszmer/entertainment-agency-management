package com.agency.contractmanagement.repository;

import com.agency.contractmanagement.model.ContractWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractWorkRepository extends JpaRepository<ContractWork, Long>, QuerydslPredicateExecutor<ContractWork> {

    @Query("SELECT COUNT(cw) FROM ContractWork cw WHERE YEAR(cw.signDate) = :year")
    int getNumberOfContractsByYear(int year);

}
