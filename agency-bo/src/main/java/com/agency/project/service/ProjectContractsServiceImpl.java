package com.agency.project.service;

import com.agency.contractmanagement.model.contract.ContractWork;
import com.agency.contractmanagement.model.contractor.Contractor;
import com.agency.contractmanagement.repository.ContractorRepository;
import com.agency.dto.contractwork.ContractType;
import com.agency.dto.contractwork.ContractWorkCreateDto;
import com.agency.dto.project.ProjectDto;
import com.agency.dto.project.ProjectStatus;
import com.agency.exception.AgencyErrorResult;
import com.agency.exception.AgencyException;
import com.agency.project.assembler.ProjectAssembler;
import com.agency.project.model.Project;
import com.agency.project.repository.ProjectRepository;
import com.agency.service.ProjectContractsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectContractsServiceImpl implements ProjectContractsService {

    private final ProjectRepository repository;
    private final ContractorRepository contractorRepository;
    private final ContractNumberGenerator numberGenerator;

    @Override
    @Transactional
    public ProjectDto createContract(ContractWorkCreateDto contractWorkCreateDto) {
        Project project = repository.findByContractNumber(contractWorkCreateDto.projectContractNumber())
                .orElseThrow(() -> new AgencyException(AgencyErrorResult.PROJECT_DOES_NOT_EXIST_EXCEPTION));

        validateProjectStatus(project.getStatus());

        Contractor contractor = contractorRepository.findContractorByPublicId(UUID.fromString(contractWorkCreateDto.contractorPublicId()))
                .orElseThrow(() -> new AgencyException(AgencyErrorResult.CONTRACTOR_DOES_NOT_EXISTS));

        String contractWorkNumber = numberGenerator.generateContractNumber(contractWorkCreateDto.contractDetailsDto().signDate(), ContractType.CONTRACT_WORK);
        ContractWork contractWork = ContractWork.create(contractWorkNumber, contractWorkCreateDto, contractor, project);
        project.addContractWork(contractWork);
        Project savedProject = repository.save(project);
        return ProjectAssembler.toDto(savedProject);
    }

    private void validateProjectStatus(ProjectStatus status) {
        if(ProjectStatus.TERMINATED == status){
            throw new AgencyException(AgencyErrorResult.ONLY_ONE_AGENCY_CAN_EXIST);
        }
    }
}
