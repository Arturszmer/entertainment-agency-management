package com.agency.contractmanagement.bills.service;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface BillCalculationResult {

    BigDecimal getGrossAmount();
    BigDecimal getNetAmount();
    String getContractNumber();
    LocalDate getPaymentDate();


}
