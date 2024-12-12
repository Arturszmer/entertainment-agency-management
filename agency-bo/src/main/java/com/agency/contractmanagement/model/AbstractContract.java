package com.agency.contractmanagement.model;

import com.agency.common.BaseEntity;
import com.agency.common.ExcludeFromPlaceholders;
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
    private String contractNumber;

    @Column(name = "sign_date")
    private LocalDate signDate;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "contract_subject")
    private String contractSubject;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "additional_information")
    private String additionalInformation;

    @Column(name = "contract_type")
    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    public abstract void checkForDelete();
}
