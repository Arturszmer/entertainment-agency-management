package com.agency.dto.bill;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContractWorkBillDto(
        String contractorName,
        String contractNumber,
        String billNumber,
        LocalDate paymentDate,
        BigDecimal grossAmount,
        BigDecimal taxAmount,
        BigDecimal netAmount,
        BillStatus billStatus
) {
}
