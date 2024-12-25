package com.agency.project.model;

import com.agency.contractmanagement.model.ContractWork;
import com.agency.dto.project.CostCreateDto;
import com.agency.exception.AgencyException;
import com.agency.exception.CostErrorResult;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CostCreator {

    private final Project costOwner;

    public ProjectCost addProjectTypeCost(Project project) {
        return null;
    }

    public ProjectCost addContractWorkTypeCost(ContractWork contractWork) {
        return ProjectCost.createGeneratedCost(
                "CONTRACT_WORK",
                contractWork.getContractNumber(),
                getContractorWorkDescriptionCost(contractWork),
                contractWork.getSalary(),
                costOwner
        );
    }

    public ProjectCost addCustomCost(CostCreateDto costCreateDto) {
        if (costCreateDto.value().signum() < 0) {
            throw new AgencyException(CostErrorResult.VALUE_CANNOT_BE_NEGATIVE);
        }
        return ProjectCost.createNotGeneratedCost(
                costCreateDto.costType(),
                costCreateDto.costReference(),
                costCreateDto.description(),
                costCreateDto.value(),
                costOwner
        );
    }

    private String getContractorWorkDescriptionCost(ContractWork contractWork) {
        String firstName = contractWork.getContractor().getFirstName();
        String lastName = contractWork.getContractor().getLastName();
        return firstName + " " + lastName;
    }
}
