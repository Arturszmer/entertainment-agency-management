package com.agency.contractmanagement.taxconfiguration;

import com.agency.contractmanagement.calculation.model.TaxConfiguration;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TaxConfigurationModel {

    public static TaxConfiguration getStandardTaxConfiguration() {
        return TaxConfiguration.builder()
                .validFrom(LocalDate.now())
                .validTo(LocalDate.now().plusDays(30))
                .firstTaxThresholdRate(BigDecimal.valueOf(0.12))
                .secondTaxThresholdRate(BigDecimal.valueOf(0.30))
                .incomeCostRate(BigDecimal.valueOf(0.20))
                .incomeCostCopyrightsRate(BigDecimal.valueOf(0.50))
                .healthInsuranceRate(BigDecimal.valueOf(0.9))
                .pensionInsuranceRate(BigDecimal.valueOf(0.0976))
                .disabilityInsuranceRate(BigDecimal.valueOf(0.0245))
                .sicknessInsuranceRate(BigDecimal.valueOf(0.245))
                .build();
    }
}
