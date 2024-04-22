package com.agency.service;

import com.agency.dto.project.ProjectCreateDto;
import com.agency.dto.project.ProjectDto;

public interface ProjectService {

    ProjectDto addProject(ProjectCreateDto projectCreateDto);
}
