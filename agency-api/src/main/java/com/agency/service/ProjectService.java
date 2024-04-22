package com.agency.service;

import com.agency.dto.project.ProjectCreateDto;
import com.agency.dto.project.ProjectDto;
import com.agency.dto.project.ProjectStatus;

public interface ProjectService {

    ProjectDto addProject(ProjectCreateDto projectCreateDto);

    ProjectDto updateStatus(String contractNumber, ProjectStatus status);
}
