package com.agency.project.service;

import com.agency.contractmanagement.model.AbstractContract;
import com.agency.contractmanagement.model.ContractWork;
import com.agency.dto.project.CostCreateDto;
import com.agency.dto.project.CostDto;
import com.agency.exception.AgencyException;
import com.agency.exception.ProjectErrorResult;
import com.agency.project.assembler.CostAssembler;
import com.agency.project.model.Project;
import com.agency.project.model.ProjectCost;
import com.agency.project.model.costcreator.CostCreator;
import com.agency.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CostContractServiceImpl implements CostService {

    private final ProjectRepository projectRepository;

    @Override
    public List<CostDto> getAllCostsForProject(String projectPublicId) {
        List<ProjectCost> projectCosts = projectRepository.findProjectCosts(UUID.fromString(projectPublicId));
        return projectCosts.stream()
                .map(CostAssembler::toDto)
                .toList();
    }

    @Override
    public CostDto addCost(CostCreateDto costDto) {
        Project project = projectRepository.findByContractNumber(costDto.projectNumber())
                .orElseThrow(() -> new AgencyException(ProjectErrorResult.PROJECT_NOT_FOUND, costDto.projectNumber()));
        ProjectCost newCost = new ProjectCost(
                costDto.costType(),
                costDto.costReference(),
                costDto.costDescription(),
                costDto.value(),
                project);

        project.addCost(newCost);
        projectRepository.save(project);
        return CostAssembler.toDto(newCost);
    }

    @Override
    public ProjectCost addContractTypeCost(AbstractContract contract, Project costOwner) {
        CostCreator costCreator = new CostCreator(costOwner);
        switch (contract.getContractType()){
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
}
