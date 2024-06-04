package com.agency.contractmanagement.model.contract;

import com.agency.common.BaseEntity;
import com.agency.dict.contract.ContractType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
public abstract class AbstractContract extends BaseEntity<Long> {

    @Column(name = "contract_number", nullable = false)
    private String contractNumber;
    @Column(name = "sign_date")
    private LocalDate signDate;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "subject_of_the_contract")
    private String subjectOfTheContract;
    @Column(name = "salary")
    private BigDecimal salary;
    @Column(name = "additional_information")
    private String additionalInformation;
    @Enumerated(EnumType.STRING)
    private ContractType contractType;

}
