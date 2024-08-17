package com.agency.service;

import com.agency.dict.project.ProjectStatus;
import com.agency.dto.project.*;

public interface ProjectService {

    ProjectDto addProject(ProjectCreateDto projectCreateDto);

    ProjectDto updateStatus(String contractNumber, ProjectStatus status);

    ProjectContractorAssignResponse assignContractors(ProjectContractorAssignDto projectContractorAssignDto);
}
