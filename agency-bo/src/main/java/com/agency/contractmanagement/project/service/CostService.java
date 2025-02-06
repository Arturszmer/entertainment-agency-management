package com.agency.contractmanagement.project.service;

import com.agency.contractmanagement.contractwork.model.AbstractContract;
import com.agency.dto.project.CostCreateDto;
import com.agency.dto.project.CostDto;
import com.agency.contractmanagement.project.model.Project;
import com.agency.contractmanagement.project.model.ProjectCost;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface CostService {

    BigDecimal getBalance(String projectPublicId);
    List<CostDto> getAllCostsForProject(String projectPublicId);

    CostDto addCost(CostCreateDto costDto);

    ProjectCost addContractTypeCost(AbstractContract contract, Project costOwner);

    void deleteCost(UUID publicId);

    void removeCostsByCostReference(String costReference);

    void updateCost(CostDto updateCost);
}
