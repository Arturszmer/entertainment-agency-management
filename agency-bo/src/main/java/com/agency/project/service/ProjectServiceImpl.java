package com.agency.project.service;

import com.agency.dict.contract.ContractType;
import com.agency.dto.project.ProjectCreateDto;
import com.agency.dto.project.ProjectDto;
import com.agency.dict.project.ProjectStatus;
import com.agency.exception.ContractorErrorResult;
import com.agency.exception.AgencyException;
import com.agency.exception.OrganizerErrorResult;
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

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;
    private final ContractNumberGenerator numberGenerator;
    private final OrganizerRepository organizerRepository;

    @Override
    public ProjectDto addProject(ProjectCreateDto projectCreateDto) {
        String projectNumber = numberGenerator.generateContractNumber(projectCreateDto.signDate(), ContractType.PROJECT);
        Project project = Project.create(projectNumber, projectCreateDto);
        if(!projectCreateDto.isInternal()){
          project.addOrganizer(addOrganizer(projectCreateDto.organizerPublicId()));
        }
        repository.save(project);
        log.info("New project with contract number {} and DRAFT status has been created.", projectNumber);
        return ProjectAssembler.toDto(project);
    }

    private Organizer addOrganizer(String organizerPublicId) {
        return organizerRepository.findOrganizerByPublicId(UUID.fromString(organizerPublicId))
                .orElseThrow(() -> new AgencyException(OrganizerErrorResult.ORGANIZER_NOT_FOUND));
    }

    @Override
    @Transactional
    public ProjectDto updateStatus(String contractNumber, ProjectStatus status) {
        Project project = repository.findByContractNumber(contractNumber)
                .orElseThrow(() -> new AgencyException(ContractorErrorResult.PROJECT_DOES_NOT_EXIST_EXCEPTION));
        ProjectStatus oldStatus = project.getStatus();
        project.updateStatus(status);
        Project projectWithUpdatedStatus = repository.save(project);
        log.info("The Project status has been changed successfully, \n old status: {} \n new status: {}",
                oldStatus, projectWithUpdatedStatus.getStatus());
        return ProjectAssembler.toDto(projectWithUpdatedStatus);
    }
}
