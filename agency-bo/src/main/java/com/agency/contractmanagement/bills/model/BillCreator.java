package com.agency.contractmanagement.bills.model;

import com.agency.contractmanagement.bills.service.BillCalculationResult;
import com.agency.contractmanagement.calculation.model.TaxConfiguration;
import com.agency.contractmanagement.contractwork.model.AbstractContract;
import com.agency.dto.bill.BillType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BillCreator {

    private final TaxConfiguration taxConfiguration;
    private final AbstractContract abstractContract;
    private final BillCalculator billCalculator;
    private final BillType billType;

    public BillCreator(TaxConfiguration taxConfiguration,
                       AbstractContract abstractContract,
                       BillCalculator billCalculator,
                       BillType billType) {
        this.taxConfiguration = taxConfiguration;
        this.abstractContract = abstractContract;
        this.billCalculator = billCalculator;
        this.billType = billType;
    }

    public Bill create(BigDecimal grossAmount, LocalDate paymentDate){

        BillCalculationResult calculateResult = billCalculator.calculate(
                grossAmount, taxConfiguration, abstractContract, paymentDate);

        String billNumber = getBillNumber(abstractContract.getContractNumber(), abstractContract.getBillsNumber());

        return BillFactory.create(calculateResult, billNumber, billType);

    }

    private String getBillNumber(String contractNumber, int billsNumber) {
        int number = billsNumber + 1;
        return contractNumber + "-" + number;
    }
}
