package com.agency.contractmanagement.project.assembler;

import com.agency.contractmanagement.bills.model.ContractWorkBill;
import com.agency.contractmanagement.contractwork.model.ContractWork;
import com.agency.contractor.model.Contractor;
import com.agency.dto.bill.ContractWorkBillDto;

public class BillsAssembler {

    public static ContractWorkBillDto toContractWorkBillDto(final ContractWorkBill bill,
                                                            final ContractWork contractWork) {
        Contractor contractor = contractWork.getContractor();
        return new ContractWorkBillDto(
                contractor.getFirstName() + " " + contractor.getLastName(),
                contractWork.getContractNumber(),
                bill.getBillNumber(),
                bill.getPaymentDate(),
                bill.getGrossAmount(),
                bill.getTaxAmount(),
                bill.getNetAmount(),
                bill.getStatus());
    }
}
