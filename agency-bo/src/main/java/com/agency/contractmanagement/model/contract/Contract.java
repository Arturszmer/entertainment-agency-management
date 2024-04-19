package com.agency.contractmanagement.model.contract;

import com.agency.common.BaseEntity;
import com.agency.contractmanagement.model.contractor.Contractor;
import com.agency.dto.contract.ContractType;
import jakarta.persistence.Column;
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
import org.springframework.lang.NonNull;

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
    @ManyToOne
    @NonNull
    private Contractor contractor;
    @Enumerated(EnumType.STRING)
    private ContractType contractType;

}
