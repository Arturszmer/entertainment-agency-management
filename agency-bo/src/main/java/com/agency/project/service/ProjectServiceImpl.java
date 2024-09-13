package com.agency.project.service;

import com.agency.contractmanagement.model.contractor.Contractor;
import com.agency.contractmanagement.repository.ContractorRepository;
import com.agency.dict.contract.ContractType;
import com.agency.dict.project.ProjectStatus;
import com.agency.dto.contractor.ContractorAssignDto;
import com.agency.dto.project.*;
import com.agency.exception.AgencyException;
import com.agency.exception.OrganizerErrorResult;
import com.agency.exception.ProjectErrorResult;
import com.agency.organizer.model.Organizer;
import com.agency.organizer.repository.OrganizerRepository;
import com.agency.project.assembler.ProjectAssembler;
import com.agency.project.model.Project;
import com.agency.project.repository.ProjectRepository;
import com.agency.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.agency.contractmanagement.validator.ContractorValidator.hasActiveContractInProject;
import static com.agency.exception.ProjectErrorResult.PROJECT_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;
    private final ContractNumberGenerator numberGenerator;
    private final OrganizerRepository organizerRepository;
    private final ContractorRepository contractorRepository;

    @Override
    public ProjectDto addProject(ProjectCreateDto projectCreateDto) {
        // TODO: protect from creating a project before initialize agency
        String projectNumber = numberGenerator.generateContractNumber(projectCreateDto.signDate(), ContractType.PROJECT);
        Project project = Project.create(projectNumber, projectCreateDto);
        if(!projectCreateDto.isInternal()){
          project.addOrganizer(getExistingOrganizer(projectCreateDto.organizerPublicId()));
        }
        repository.save(project);
        log.info("New project with contract number {} and DRAFT status has been created.", projectNumber);
        return ProjectAssembler.toDto(project);
    }

    @Override
    @Transactional
    public ProjectDto updateStatus(String contractNumber, ProjectStatus status) {
        Project project = getProject(contractNumber);
        ProjectStatus oldStatus = project.getStatus();
        project.updateStatus(status);
        Project projectWithUpdatedStatus = repository.save(project);
        log.info("The Project status has been changed successfully, \n old status: {} \n new status: {}",
                oldStatus, projectWithUpdatedStatus.getStatus());
        return ProjectAssembler.toDto(projectWithUpdatedStatus);
    }

    @Override
    public ProjectContractorAssignResponse assignContractors(ProjectContractorAssignDto projectContractorAssignDto) {
        Project project = getProject(projectContractorAssignDto.projectNumber());
        assign(projectContractorAssignDto.contractors(), project);
        Project savedProject = repository.save(project);
        log.info("The operation of assigning the contractors to the project nr {} is finished successfully. " +
                "The actual number of contractors is: {}", savedProject.getContractNumber(), savedProject.getContractors().size());
        return ProjectAssembler.toProjectContractorAssignResponse(savedProject);
    }

    @Override
    @Transactional
    public ProjectContractorAssignResponse removeContractor(ProjectContractorRemoveDto projectContractorRemoveDto) {
        String projectNumber = projectContractorRemoveDto.projectNumber();
        Project project = getProject(projectNumber);

        Contractor contractorToRemove = findContractorToRemove(projectContractorRemoveDto, project, projectNumber);
        hasActiveContractInProject(contractorToRemove, projectNumber);

        project.getContractors().remove(contractorToRemove);
        Project updatedProject = repository.save(project);

        log.info("Contractor with public id number {} has been removed succesfully from project number {}",
                projectContractorRemoveDto.contractorPublicId(), projectNumber);

        return ProjectAssembler.toProjectContractorAssignResponse(updatedProject);
    }

    private Project getProject(String contractNumber) {
        return repository.findByContractNumber(contractNumber)
                .orElseThrow(() -> new AgencyException(PROJECT_NOT_FOUND, contractNumber));
    }

    private Organizer getExistingOrganizer(String organizerPublicId) {
        return organizerRepository.findOrganizerByPublicId(UUID.fromString(organizerPublicId))
                .orElseThrow(() -> new AgencyException(OrganizerErrorResult.ORGANIZER_NOT_FOUND, organizerPublicId));
    }

    private void assign(List<ContractorAssignDto> contractorsToAssign, Project project) {
        contractorsToAssign.forEach(contractorToAssign ->
            contractorRepository.findContractorByPublicId(UUID.fromString(contractorToAssign.publicId()))
                    .ifPresent(contractor -> {
                        project.assignContractor(contractor);
                        log.info("Contractor with id: {} to project number {} has been added succesfully.",
                                contractor.getPublicId(), project.getContractNumber());
                    })
        );
    }

    private static Contractor findContractorToRemove(ProjectContractorRemoveDto projectContractorRemoveDto, Project project, String projectNumber) {
        return project.getContractors().stream()
                .filter(contractor -> contractor.getPublicId().toString()
                        .equals(projectContractorRemoveDto.contractorPublicId()))
                .findFirst()
                .orElseThrow(() -> new AgencyException(ProjectErrorResult.CONTRACTOR_NOT_EXISTS_INTO_PROJECT, projectContractorRemoveDto.contractorPublicId(), projectNumber));
    }
}
