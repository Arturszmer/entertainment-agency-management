package com.agency.contractmanagement.bills.model;

import com.agency.contractmanagement.bills.service.ContractWorkBillCalculationResult;
import com.agency.contractmanagement.calculation.model.TaxConfiguration;
import com.agency.contractmanagement.contractwork.model.AbstractContract;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.math.RoundingMode.HALF_UP;
import static java.math.RoundingMode.UNNECESSARY;

@NoArgsConstructor
public class ContractWorkBillCalculation implements BillCalculator {

    public ContractWorkBillCalculationResult calculate(final BigDecimal grossAmount,
                                                       TaxConfiguration configuration,
                                                       AbstractContract abstractContract,
                                                       LocalDate paymentDate) {

        BigDecimal incomeCost = getIncomeCost(grossAmount, configuration, abstractContract.isWithCopyrights());

        BigDecimal taxBase = grossAmount.subtract(incomeCost)
                .setScale(2, UNNECESSARY);

        BigDecimal tax = taxBase.setScale(0, HALF_UP)
                .multiply(configuration.getFirstTaxThresholdRate())
                .setScale(0, HALF_UP);

        BigDecimal net = grossAmount.subtract(tax);

        return ContractWorkBillCalculationResult.builder()
                .grossAmount(grossAmount)
                .incomeCost(incomeCost)
                .taxBase(taxBase)
                .taxAmount(tax)
                .netAmount(net)
                .contractNumber(abstractContract.getContractNumber())
                .paymentDate(paymentDate)
                .build();
    }

    @NotNull
    private static BigDecimal getIncomeCost(BigDecimal grossAmount,
                                            TaxConfiguration configuration,
                                            boolean isWithCopyrights) {
        return isWithCopyrights
                ? grossAmount.multiply(configuration.getIncomeCostCopyrightsRate()).setScale(2, UNNECESSARY)
                : grossAmount.multiply(configuration.getIncomeCostRate()).setScale(2, UNNECESSARY);
    }
}
