package com.agency.contractmanagement.validator;

import com.agency.contractmanagement.model.contractor.Contractor;
import com.agency.exception.AgencyException;
import com.agency.exception.ProjectErrorResult;
import org.springframework.stereotype.Component;

@Component
public class ContractorValidator {

    public void hasActiveContractInProject(Contractor contractorToRemove, String projectNumber) {
        if(contractorToRemove.getContracts().stream()
                .anyMatch(contract -> contract.getProjectNumber().equals(projectNumber))){
            throw new AgencyException(ProjectErrorResult.CONTRACTOR_HAS_ACTIVE_CONTRACT_IN_PROJECT, contractorToRemove.getPublicId(), projectNumber);
        }
    }
}
