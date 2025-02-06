package com.agency.contractmanagement.bills.model;

import com.agency.contractmanagement.bills.service.ContractWorkBillCalculationResult;
import com.agency.dto.bill.BillStatus;
import com.agency.dto.bill.BillType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contract_work_bill")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ContractWorkBill extends Bill {

    private BigDecimal taxBase;
    private BigDecimal taxAmount;
    private BigDecimal incomeCosts;

    public ContractWorkBill(String billNumber,
                            LocalDate paymentDate,
                            BigDecimal grossAmount,
                            BigDecimal netAmount,
                            BigDecimal incomeCosts,
                            BigDecimal taxBase,
                            BigDecimal taxAmount,
                            BillStatus status) {
        super(billNumber, paymentDate, grossAmount, netAmount, status);
        this.taxBase = taxBase;
        this.taxAmount = taxAmount;
        this.incomeCosts = incomeCosts;
    }

    static ContractWorkBill fromCalculationResult(ContractWorkBillCalculationResult calcResult, String billNumber) {
        return new ContractWorkBill(
                billNumber,
                calcResult.getPaymentDate(),
                calcResult.getGrossAmount(),
                calcResult.getNetAmount(),
                calcResult.getIncomeCost(),
                calcResult.getTaxBase(),
                calcResult.getTaxAmount(),
                BillStatus.DRAFT
        );
    }

    @Override
    public BillType getBillType() {
        return BillType.CONTRACT_WORK_BILL;
    }
}
