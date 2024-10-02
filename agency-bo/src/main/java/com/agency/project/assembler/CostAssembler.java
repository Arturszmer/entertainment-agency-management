package com.agency.project.assembler;

import com.agency.dto.project.CostDto;
import com.agency.project.model.ProjectCost;

public class CostAssembler {

    public static CostDto toDto(ProjectCost projectCost) {
        return new CostDto(
                projectCost.getCostType(),
                projectCost.getCostReference(),
                projectCost.getDescription(),
                projectCost.getValue()
        );
    }
}
