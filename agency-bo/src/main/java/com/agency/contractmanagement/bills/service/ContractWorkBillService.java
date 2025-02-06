package com.agency.contractmanagement.bills.service;

import com.agency.contractmanagement.bills.model.Bill;
import com.agency.contractmanagement.bills.model.BillCreator;
import com.agency.contractmanagement.bills.model.ContractWorkBill;
import com.agency.contractmanagement.bills.model.ContractWorkBillCalculation;
import com.agency.contractmanagement.calculation.model.TaxConfiguration;
import com.agency.contractmanagement.calculation.repository.CalcConfigurationRepository;
import com.agency.contractmanagement.contractwork.model.ContractWork;
import com.agency.contractmanagement.contractwork.repository.ContractWorkRepository;
import com.agency.dict.contract.ContractWorkStatus;
import com.agency.dto.bill.BillType;
import com.agency.exception.AgencyException;
import com.agency.exception.BillErrorResult;
import com.agency.exception.ContractErrorResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractWorkBillService {

    private final CalcConfigurationRepository calcConfigurationRepository;
    private final ContractWorkRepository contractWorkRepository;

    @Transactional
    public Bill createBill(BillCalculationRequest billCalculationRequest) {
        String contractWorkNumber = billCalculationRequest.contractNumber();
        LocalDate billPaymentDate = billCalculationRequest.billPaymentDay();

        ContractWork contractWork = contractWorkRepository.findContractWorkByContractNumber(contractWorkNumber)
                .orElseThrow(() -> new AgencyException(ContractErrorResult.CONTRACT_NOT_EXISTS, contractWorkNumber));

        if(ContractWorkStatus.canCreateABill(contractWork.getStatus())){
            TaxConfiguration taxConfiguration = calcConfigurationRepository.findConfigurationByDate(billPaymentDate)
                    .orElseThrow(() -> new IllegalArgumentException("There are no tax configuration for bill payment date: " + billPaymentDate));

            BigDecimal contractBalance = contractWork.getContractBalance();
            validateContractBalance(contractBalance);

            BillCreator billCreator = new BillCreator(taxConfiguration, contractWork, new ContractWorkBillCalculation(), BillType.CONTRACT_WORK_BILL);
            ContractWorkBill contractWorkBill = (ContractWorkBill) billCreator.create(contractBalance, billPaymentDate);

            contractWork.addBill(contractWorkBill);
            contractWorkRepository.save(contractWork);
            log.info("New bill for contract {} has been added successfully", contractWorkNumber);
            return contractWorkBill;
        } else {
            throw new AgencyException(BillErrorResult.CONTRACT_MUST_BE_IN_ACTIVE_OR_SIGNED_STATUS);
        }
    }

    private void validateContractBalance(BigDecimal contractBalance) {
        if (contractBalance.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AgencyException(BillErrorResult.AMOUNT_MUST_BE_GREATER_THAN_ZERO);
        }
    }
}
