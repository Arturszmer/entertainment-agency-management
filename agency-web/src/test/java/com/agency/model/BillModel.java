package com.agency.model;

import com.agency.contractmanagement.bills.service.BillCalculationRequest;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BillModel {

    public static BillCalculationRequest getBillCalculationRequest() {
        return new BillCalculationRequest(
                "D2024/STY/UoD1", LocalDate.of(2024, 1, 15), BigDecimal.valueOf(1000)
        );
    }
}
