package com.agency.project.service;

import com.agency.dict.project.ProjectStatus;
import com.agency.dto.project.ProjectDto;
import com.agency.dto.project.ProjectSearchDto;
import com.agency.dto.project.ProjectToAssignContractorDto;
import com.agency.exception.AgencyException;
import com.agency.exception.ProjectErrorResult;
import com.agency.project.assembler.ProjectAssembler;
import com.agency.project.model.Project;
import com.agency.project.repository.ProjectRepository;
import com.agency.search.SortableConfig;
import com.agency.service.ProjectSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectSearchServiceImpl implements ProjectSearchService {

    private final ProjectRepository repository;
    private final SortableConfig sortableConfig;

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectSearchDto> findAll(int page, int size, String sort, String order) {
        Pageable pagesRequest = sortableConfig.getPageable(page, size, sort, order);
        Page<Project> projectPage = repository.findAll(pagesRequest);
        List<ProjectSearchDto> projectsDto = projectPage.stream().map(ProjectAssembler::toSearchDto).toList();
        return new PageImpl<>(projectsDto, pagesRequest, projectPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectDto getProjectFullInfo(String publicId) {
        return ProjectAssembler.toDto(repository.findProjectByPublicId(UUID.fromString(publicId))
                .orElseThrow(() -> new AgencyException(ProjectErrorResult.PROJECT_NOT_FOUND)));
    }

    @Override
    public List<ProjectToAssignContractorDto> findAllToAssign(String assignedContractorPublicId) {
        List<Project> projects = repository.findAllByStatusNot(ProjectStatus.TERMINATED);
        return projects.stream()
                .filter(project -> project.getContractors().stream().noneMatch(contractor -> contractor.getPublicId().toString().equals(assignedContractorPublicId)))
                .map(ProjectAssembler::toProjectToAssignContractor)
                .toList();
    }
}
