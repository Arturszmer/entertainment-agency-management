package com.agency.contractmanagement.model.contract;

import com.agency.common.BaseEntity;
import com.agency.contractmanagement.model.contractor.Contractor;
import com.agency.dto.contract.ContractType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "contract")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Contract extends BaseEntity<Long> {

    private String contractNumber;
    private LocalDate signDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String subjectOfTheContract;
    private BigDecimal salary;
    private String additionalInformation;
    @ManyToOne
    private Contractor contractor;
    @Enumerated(EnumType.STRING)
    private ContractType contractType;

}
