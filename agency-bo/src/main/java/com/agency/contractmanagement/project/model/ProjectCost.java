package com.agency.contractmanagement.project.model;

import com.agency.dto.project.CostDto;
import com.agency.exception.AgencyException;
import com.agency.exception.CostErrorResult;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "project_cost")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProjectCost extends Cost {

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;
    @Column(name = "is_generated")
    private boolean isGenerated;

    ProjectCost(String costType,
                String costReference,
                String description,
                BigDecimal value,
                Project project,
                boolean isGenerated) {
        super(costType, costReference, description, value, UUID.randomUUID());
        this.project = project;
        this.isGenerated = isGenerated;
    }

    static ProjectCost createGeneratedCost(String costType,
                                           String costReference,
                                           String description,
                                           BigDecimal value,
                                           Project project) {
        return new ProjectCost(costType, costReference, description, value, project, true);
    }

    static ProjectCost createNotGeneratedCost(String costType,
                                              String costReference,
                                              String description,
                                              BigDecimal value,
                                              Project project) {
        return new ProjectCost(costType, costReference, description, value, project, false);
    }

    public String getProjectNumber() {
        return project.getContractNumber();
    }

    public void updateCost(CostDto updateCost) {
        updateValidator(updateCost);
        this.setCostType(updateCost.costType());
        this.setCostReference(updateCost.costReference());
        this.setDescription(updateCost.description());
        this.setValue(updateCost.value());
    }

    private void updateValidator(CostDto updateCost) {
        if (this.isGenerated) {
            throw new AgencyException(CostErrorResult.GENERATED_COST_CANNOT_BE_CHANGED);
        }
        if (updateCost.value().signum() < 0) {
            throw new AgencyException(CostErrorResult.VALUE_CANNOT_BE_NEGATIVE);
        }
    }
}
