//package com.agency.contractmanagement.bills.service;
//
//import com.agency.contractmanagement.bills.model.ContractWorkBillCalculation;
//import com.agency.contractmanagement.calculation.model.TaxConfiguration;
//import com.agency.contractmanagement.contractwork.model.AbstractContract;
//import com.agency.contractmanagement.contractwork.model.ContractWork;
//import org.junit.jupiter.api.Test;
//
//import java.math.BigDecimal;
//
//import static com.agency.contractmanagement.taxconfiguration.TaxConfigurationModel.getStandardTaxConfiguration;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class ContractWorkBillCalculationTest {
//
//    @Test
//    public void shouldCalculateBillForContractWorkWithoutCopyrights() {
//        // given
//        BigDecimal salary = BigDecimal.valueOf(128.30);
//        TaxConfiguration taxConfiguration = getStandardTaxConfiguration();
//
//        // when
//        BillCalculationResult contractWorkBill = new ContractWorkBillCalculation().calculate(salary, taxConfiguration, ContractWork.create());
//
//        // then
//        assertEquals(salary, contractWorkBill.getGrossAmount());
//        assertEquals(BigDecimal.valueOf(25.66), contractWorkBill.getIncomeCost());
//        assertEquals(BigDecimal.ZERO, contractWorkBill.insuranceCost());
//        assertEquals(BigDecimal.valueOf(102.64), contractWorkBill.taxBase());
//        assertEquals(BigDecimal.valueOf(12), contractWorkBill.taxAmount());
//        assertEquals(BigDecimal.valueOf(116.30), contractWorkBill.netAmount());
//
//    }
//
//    @Test
//    public void shouldCalculateBillForContractWorkWithCopyrights() {
//        // given
//        BigDecimal salary = BigDecimal.valueOf(128.30);
//        TaxConfiguration taxConfiguration = getStandardTaxConfiguration();
//
//        // when
//        BillCalculationResult contractWorkBill = ContractWorkBillCalculation.calculate(salary, taxConfiguration, true);
//
//        // then
//        assertEquals(salary, contractWorkBill.grossAmount());
//        assertEquals(BigDecimal.valueOf(64.15), contractWorkBill.incomeCost());
//        assertEquals(BigDecimal.ZERO, contractWorkBill.insuranceCost());
//        assertEquals(BigDecimal.valueOf(64.15), contractWorkBill.taxBase());
//        assertEquals(BigDecimal.valueOf(8), contractWorkBill.taxAmount());
//        assertEquals(BigDecimal.valueOf(120.30), contractWorkBill.netAmount());
//
//    }
//}
