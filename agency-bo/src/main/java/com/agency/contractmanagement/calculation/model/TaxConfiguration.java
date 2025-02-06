package com.agency.contractmanagement.calculation.model;

import com.agency.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Entity
@Table(name = "tax_configuration")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TaxConfiguration extends BaseEntity<Long> {

    @Column(name = "valid_from", nullable = false)
    private LocalDate validFrom;
    @Column(name = "valid_to", nullable = false)
    private LocalDate validTo;
    @Column(nullable = false)
    private BigDecimal firstTaxThresholdRate;
    @Column(nullable = false)
    private BigDecimal secondTaxThresholdRate;
    @Column(nullable = false)
    private BigDecimal incomeCostRate;
    @Column(nullable = false)
    private BigDecimal incomeCostCopyrightsRate;
    @Column(nullable = false)
    private BigDecimal healthInsuranceRate;
    @Column(nullable = false)
    private BigDecimal pensionInsuranceRate;
    @Column(nullable = false)
    private BigDecimal disabilityInsuranceRate;
    @Column(nullable = false)
    private BigDecimal sicknessInsuranceRate;

}
