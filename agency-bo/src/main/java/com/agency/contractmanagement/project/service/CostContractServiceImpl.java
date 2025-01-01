package com.agency.contractmanagement.project.service;

import com.agency.contractmanagement.contractwork.model.AbstractContract;
import com.agency.contractmanagement.contractwork.model.ContractWork;
import com.agency.dto.project.CostCreateDto;
import com.agency.dto.project.CostDto;
import com.agency.exception.AgencyException;
import com.agency.exception.ProjectErrorResult;
import com.agency.contractmanagement.project.assembler.CostAssembler;
import com.agency.contractmanagement.project.model.CostCreator;
import com.agency.contractmanagement.project.model.Project;
import com.agency.contractmanagement.project.model.ProjectCost;
import com.agency.contractmanagement.project.repository.ProjectCostRepository;
import com.agency.contractmanagement.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CostContractServiceImpl implements CostService {

    private final ProjectRepository projectRepository;
    private final ProjectCostRepository projectCostRepository;

    @Override
    public List<CostDto> getAllCostsForProject(String projectPublicId) {
        List<ProjectCost> projectCosts = projectCostRepository.findProjectCostsByProjectPublicId(UUID.fromString(projectPublicId));
        return projectCosts.stream()
                .map(CostAssembler::toDto)
                .toList();
    }

    @Override
    @Transactional
    public CostDto addCost(CostCreateDto costDto) {
        Project project = projectRepository.findProjectByPublicId(costDto.projectPublicId())
                .orElseThrow(() -> new AgencyException(ProjectErrorResult.PROJECT_NOT_FOUND, costDto.projectPublicId()));
        CostCreator costCreator = new CostCreator(project);
        ProjectCost newCost = costCreator.addCustomCost(costDto);

        project.addCost(newCost);
        projectRepository.save(project);
        log.info("Cost with public id {} for project number {} has been added.", newCost.getPublicId(), newCost.getProjectNumber());
        return CostAssembler.toDto(newCost);
    }

    @Override
    public ProjectCost addContractTypeCost(AbstractContract contract, Project costOwner) {
        CostCreator costCreator = new CostCreator(costOwner);
        switch (contract.getContractType()) {
            case PROJECT -> {
                return costCreator.addProjectTypeCost((Project) contract);
            }
            case CONTRACT_WORK -> {
                return costCreator.addContractWorkTypeCost((ContractWork) contract);
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    @Transactional
    public void deleteCost(UUID publicId) {
        projectCostRepository.deleteProjectCostByPublicId(publicId);
        log.info("Cost with public id {} has been removed", publicId);
    }

    @Override
    public void removeCostsByCostReference(String costReference) {
        List<ProjectCost> projectCostByCostReference = projectCostRepository.findProjectCostByCostReference(costReference);
        if (!projectCostByCostReference.isEmpty()) {
            log.info("Number of costs to remove from project number {}: {}",
                    projectCostByCostReference.get(0).getProjectNumber(),
                    projectCostByCostReference.size());
            projectCostRepository.deleteAll(projectCostByCostReference);
        }
    }

    @Override
    public void updateCost(CostDto updateCost) {
        ProjectCost projectCost = projectCostRepository.findProjectCostByPublicId(updateCost.publicId())
                .orElseThrow(() -> new AgencyException(ProjectErrorResult.COST_NOT_FOUND, updateCost.publicId()));

        projectCost.updateCost(updateCost);
        projectCostRepository.save(projectCost);
        log.info("Cost with id: {} has been updated", updateCost.publicId());
    }
}
