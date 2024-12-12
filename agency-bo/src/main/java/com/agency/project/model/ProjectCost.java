package com.agency.project.model;

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

    public ProjectCost(String costType,
                       String costReference,
                       String description,
                       BigDecimal value,
                       Project project) {
        super(costType, costReference, description, value, UUID.randomUUID());
        this.project = project;
    }

    public String getProjectNumber(){
        return project.getContractNumber();
    }
}
