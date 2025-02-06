package com.agency.contractmanagement.contractnumber.service;

import com.agency.contractmanagement.contractnumber.model.ContractNumber;
import com.agency.contractmanagement.contractnumber.model.ContractNumberStatus;
import com.agency.contractmanagement.contractnumber.repository.ContractNumberRepository;
import com.agency.dict.contract.ContractType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.TextStyle;
import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@Component
public class ContractGenerator {

    private final ContractNumberRepository contractNumberRepository;

    public ContractNumber generateContractNumber(LocalDate signDate, ContractType contractType) {

        int year = signDate.getYear();
        Month month = signDate.getMonth();
        int numberOfContractsByTypeAndYear = getNumberByContractTypeAndYear(contractType, year);
        int createdContractNumber = numberOfContractsByTypeAndYear + 1;

        String polishMonth = month.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pl-PL"))
                .substring(0, 3)
                .toUpperCase();
        String preId = getPreIdByContractType(contractType);
        String completeNumber = String.format("%s%d/%s/%s%d",
                ContractNumberStatus.DRAFT.getStatus(), year, polishMonth, preId, createdContractNumber);

        ContractNumber contractNumber = new ContractNumber(
                completeNumber, createdContractNumber, year, polishMonth, contractType, ContractNumberStatus.DRAFT
        );
        log.info("Generated new contract number: {}", contractNumber);
        return contractNumber;
    }

    private int getNumberByContractTypeAndYear(ContractType contractType, int year) {
        return contractNumberRepository.findMaxNumberByContractType(
                year, contractType).orElse(0);
    }

    private String getPreIdByContractType(ContractType contractType) {
        return switch (contractType){
            case PROJECT -> "PRO";
            case CONTRACT_WORK -> "UoD";
            case MANDATORY_CONTRACT -> "UZ";
        };
    }
}
