package com.agency.project.assembler;

import com.agency.dto.project.CostDto;
import com.agency.project.model.ProjectCost;

public class CostAssembler {

    public static CostDto toDto(ProjectCost projectCost) {
        return new CostDto(
                projectCost.getPublicId(),
                projectCost.getCostType(),
                projectCost.getCostReference(),
                projectCost.getDescription(),
                projectCost.getModifiedBy(),
                projectCost.getValue(),
                projectCost.isGenerated()
        );
    }
}
