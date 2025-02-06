package com.agency.contractmanagement.bills.model;

import com.agency.contractmanagement.bills.service.BillCalculationResult;
import com.agency.contractmanagement.bills.service.ContractWorkBillCalculationResult;
import com.agency.dto.bill.BillType;

class BillFactory {

    static Bill create(BillCalculationResult billCalculationResult, String billNumber, BillType billType) {
        switch (billType){
            case CONTRACT_WORK_BILL -> {
                return ContractWorkBill.fromCalculationResult((ContractWorkBillCalculationResult) billCalculationResult, billNumber);
            }
            case MANDATORY_WORK_BILL, INVOICE -> {
                return null;
            }
            default -> throw new IllegalArgumentException("Invalid bill type");
        }
    }
}
