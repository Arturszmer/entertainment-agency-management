package com.agency.contractmanagement.bills.service;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
public class ContractWorkBillCalculationResult implements BillCalculationResult {

    private final BigDecimal grossAmount;
    private final BigDecimal netAmount;
    private final BigDecimal incomeCost;
    private final BigDecimal taxBase;
    private final BigDecimal taxAmount;
    private final String contractNumber;
    private final LocalDate paymentDate;

}
