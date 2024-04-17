package com.agency.contractmanagement.model.contract;

import com.agency.common.BaseEntity;
import com.agency.contractmanagement.model.contractor.Contractor;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "contract_of_piece")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ContractOfPiece extends BaseEntity<Long> {

    private String contractNumber;
    private LocalDate signDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String subjectOfTheContract;
    private BigDecimal salary;
    private String additionalInformation;
    @ManyToOne
    private Contractor contractor;

}
