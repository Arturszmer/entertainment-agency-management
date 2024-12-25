package com.agency.project.service;

import com.agency.contractmanagement.model.AbstractContract;
import com.agency.dto.project.CostCreateDto;
import com.agency.dto.project.CostDto;
import com.agency.project.model.Project;
import com.agency.project.model.ProjectCost;

import java.util.List;
import java.util.UUID;

public interface CostService {

    List<CostDto> getAllCostsForProject(String projectPublicId);

    CostDto addCost(CostCreateDto costDto);

    ProjectCost addContractTypeCost(AbstractContract contract, Project costOwner);

    void deleteCost(UUID publicId);

    void removeCostsByCostReference(String costReference);

    void updateCost(CostDto updateCost);
}
