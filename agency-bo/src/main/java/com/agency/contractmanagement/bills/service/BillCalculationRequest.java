package com.agency.contractmanagement.bills.service;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BillCalculationRequest(String contractNumber, LocalDate billPaymentDay, BigDecimal amount) {
}
