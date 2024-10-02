package com.agency.project.model.costcreator;

import com.agency.contractmanagement.model.contract.ContractWork;
import com.agency.project.model.Project;
import com.agency.project.model.ProjectCost;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CostCreator {

    private final Project costOwner;

    public ProjectCost addProjectTypeCost(Project project) {
        return null;
    }
    public ProjectCost addContractWorkTypeCost(ContractWork contractWork) {
        return new ProjectCost(
                "CONTRACT_WORK",
                contractWork.getContractNumber(),
                getContractorWorkDescriptionCost(contractWork),
                contractWork.getSalary(),
                costOwner
        );
    }

    private String getContractorWorkDescriptionCost(ContractWork contractWork) {
        String firstName = contractWork.getContractor().getFirstName();
        String lastName = contractWork.getContractor().getLastName();
        return firstName + " " + lastName;
    }
}
