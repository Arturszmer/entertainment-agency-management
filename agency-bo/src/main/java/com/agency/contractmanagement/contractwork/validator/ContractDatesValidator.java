package com.agency.contractmanagement.contractwork.validator;

import com.agency.dto.contractwork.BasicContractDetailsDto;
import com.agency.dto.contractwork.ContractWorkCreateDto;
import com.agency.exception.AgencyException;
import com.agency.exception.ContractErrorResult;
import com.agency.contractmanagement.project.model.Project;

import java.time.LocalDate;

public class ContractDatesValidator {

    public static void isContractHasCorrectDatesForProject(Project projectForContract, ContractWorkCreateDto createdContractWork) {
        BasicContractDetailsDto basicContractDetailsDto = createdContractWork.contractDetailsDto();

        LocalDate contractStartDate = basicContractDetailsDto.startDate();
        LocalDate contractEndDate = basicContractDetailsDto.endDate();

        LocalDate projectStartDate = projectForContract.getStartDate();
        LocalDate projectEndDate = projectForContract.getEndDate();

        if(contractStartDate.isAfter(contractEndDate)){
            throw new AgencyException(ContractErrorResult.CONTRACT_DATE_ERROR);
        }

        if(basicContractDetailsDto.signDate().isAfter(contractStartDate)){
            throw new AgencyException(ContractErrorResult.CONTRACT_SING_DATE_ERROR);
        }

        if(contractStartDate.isBefore(projectStartDate)
                || contractStartDate.isAfter(projectEndDate)
                || contractEndDate.isBefore(projectStartDate)
                || contractEndDate.isAfter(projectEndDate)){
            throw new AgencyException(ContractErrorResult.CONTRACT_DATE_PERIOD_ERROR);
        }

    }
}
