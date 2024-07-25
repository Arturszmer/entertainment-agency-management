package com.agency.service;

import com.agency.dict.project.ProjectStatus;
import com.agency.dto.project.ProjectContractorAssignDto;
import com.agency.dto.project.ProjectCreateDto;
import com.agency.dto.project.ProjectDto;
import com.agency.dto.project.ProjectSearchDto;

public interface ProjectService {

    ProjectDto addProject(ProjectCreateDto projectCreateDto);

    ProjectDto updateStatus(String contractNumber, ProjectStatus status);

    ProjectSearchDto assignContractors(ProjectContractorAssignDto projectContractorAssignDto);
}
