package com.agency.service;

import com.agency.dto.project.*;

public interface ProjectService {

    ProjectDto addProject(ProjectCreateDto projectCreateDto);

    ProjectDto updateStatus(ProjectStatusUpdateRequest request);

    ProjectContractorAssignResponse assignContractors(ProjectContractorAssignDto projectContractorAssignDto);

    ProjectContractorAssignResponse removeContractor(ProjectContractorRemoveDto projectContractorRemoveDto);
}
