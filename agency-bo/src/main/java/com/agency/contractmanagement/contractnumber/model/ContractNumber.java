package com.agency.contractmanagement.contractnumber.model;

import com.agency.common.BaseEntity;
import com.agency.dict.contract.ContractType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contract_number")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ContractNumber extends BaseEntity<Long> {

    @Column(name = "complete_number", unique = true, nullable = false)
    private String completeNumber;
    @Column(name = "number", nullable = false)
    private int number;
    @Column(name = "year", nullable = false)
    private int year;
    @Column(name = "month", nullable = false)
    private String month;
    @Enumerated(EnumType.STRING)
    @Column(name = "contract_type", nullable = false)
    private ContractType contractType;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ContractNumberStatus contractNumberStatus;

    public void updateByContractNumberStatus(ContractNumberStatus contractNumberStatus) {
        this.contractNumberStatus = contractNumberStatus;
        completeNumber = contractNumberStatus.getStatus() + completeNumber.substring(1);
    }
}
