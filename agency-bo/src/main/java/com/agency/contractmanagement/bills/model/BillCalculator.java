package com.agency.contractmanagement.bills.model;

import com.agency.contractmanagement.bills.service.BillCalculationResult;
import com.agency.contractmanagement.calculation.model.TaxConfiguration;
import com.agency.contractmanagement.contractwork.model.AbstractContract;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface BillCalculator {

    BillCalculationResult calculate(final BigDecimal grossAmount,
                                    TaxConfiguration configuration,
                                    AbstractContract abstractContract,
                                    LocalDate paymentDate);
}
