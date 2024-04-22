package com.agency.project.service;

import com.agency.contractmanagement.repository.ContractWorkRepository;
import com.agency.dto.contractwork.ContractType;
import com.agency.exception.AgencyErrorResult;
import com.agency.exception.AgencyException;
import com.agency.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ContractNumberGenerator {

    private final ProjectRepository projectRepository;
    private final ContractWorkRepository contractWorkRepository;

    public String generateContractNumber(LocalDate signDate, ContractType contractType) {
        int year = signDate.getYear();
        Month month = signDate.getMonth();
        int numberOfContractsByYear = getNumberByContractType(contractType, year);

        String polishMonth = month.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pl-PL"))
                .substring(0, 3)
                .toUpperCase();
        String preId = getPreIdByContractType(contractType);
        return String.format("%d/%s/%s%d", year, polishMonth, preId, numberOfContractsByYear + 1);
    }

    private int getNumberByContractType(ContractType contractType, int year) {
        return switch (contractType){
            case PROJECT -> projectRepository.getNumberOfContractsByYear(year);
            case CONTRACT_WORK -> contractWorkRepository.getNumberOfContractsByYear(year);
            default -> throw new AgencyException(AgencyErrorResult.CONTRACT_TYPE_DOES_NOT_EXIST);
        };
    }

    private String getPreIdByContractType(ContractType contractType) {
        return switch (contractType){
            case PROJECT -> "PRO";
            case CONTRACT_WORK -> "CON";
            default -> throw new AgencyException(AgencyErrorResult.CONTRACT_TYPE_DOES_NOT_EXIST);
        };
    }
}
