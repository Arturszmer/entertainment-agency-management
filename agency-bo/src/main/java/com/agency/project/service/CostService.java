package com.agency.project.service;

import com.agency.contractmanagement.model.contract.AbstractContract;
import com.agency.dto.project.CostCreateDto;
import com.agency.dto.project.CostDto;
import com.agency.project.model.Project;
import com.agency.project.model.ProjectCost;

import java.util.List;

public interface CostService {

    List<CostDto> getAllCostsForProject(String projectPublicId);
    CostDto addCost(CostCreateDto costDto);
    ProjectCost addContractTypeCost(AbstractContract contract, Project costOwner);
}
