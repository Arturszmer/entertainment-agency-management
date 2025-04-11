package com.agency.contractmanagement.contractwork.model;

import com.agency.common.BaseEntity;
import com.agency.common.ExcludeFromPlaceholders;
import com.agency.common.PlaceholderOrder;
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
import java.util.UUID;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
public abstract class AbstractContract extends BaseEntity<Long> {

    @Column(name = "public_id", nullable = false, unique = true)
    @ExcludeFromPlaceholders
    private UUID publicId;

    @Column(name = "contract_number", nullable = false, unique = true)
//    @PlaceholderOrder
    private String contractNumber;

    @Column(name = "sign_date")
    @PlaceholderOrder(order = 1)
    private LocalDate signDate;

    @Column(name = "start_date")
    @PlaceholderOrder(order = 2)
    private LocalDate startDate;

    @Column(name = "end_date")
    @PlaceholderOrder(order = 3)
    private LocalDate endDate;

    @Column(name = "contract_subject")
    @PlaceholderOrder(order = 4)
    private String contractSubject;

    @Column(name = "salary")
    @PlaceholderOrder(order = 5)
    private BigDecimal salary;

    @Column(name = "additional_information")
    @PlaceholderOrder(order = 7)
    private String additionalInformation;

    @Column(name = "contract_type")
    @Enumerated(EnumType.STRING)
    @ExcludeFromPlaceholders
    private ContractType contractType;

    public abstract BigDecimal getContractBalance();

    public abstract void checkForDelete();

    public void updateContractNumber(String completeNumber) {
        contractNumber = completeNumber;
    }

    public abstract boolean isWithCopyrights();

    public abstract int getBillsNumber();
}
