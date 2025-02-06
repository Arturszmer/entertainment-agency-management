package com.agency.controller.project;

import com.agency.dto.project.ProjectContractorAssignDto;
import com.agency.dto.project.ProjectContractorAssignResponse;
import com.agency.dto.project.ProjectContractorRemoveDto;
import com.agency.dto.project.ProjectCreateDto;
import com.agency.dto.project.ProjectDto;
import com.agency.dto.project.ProjectStatusUpdateRequest;
import com.agency.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/project")
@PreAuthorize("hasAuthority('PROJECT_MANAGEMENT')")
public class ProjectController {

    private final ProjectService service;

    @PostMapping
    ResponseEntity<ProjectDto> create(@RequestBody ProjectCreateDto request) {
        return ResponseEntity.ok(service.addProject(request));
    }

    @PutMapping("/status")
    ResponseEntity<ProjectDto> updateStatus(@RequestBody ProjectStatusUpdateRequest request) {
        return ResponseEntity.ok(service.updateStatus(request));
    }

    @PutMapping("/assign")
    ResponseEntity<ProjectContractorAssignResponse> assignContractors(@RequestBody ProjectContractorAssignDto projectContractorAssignDto) {

        return ResponseEntity.ok(service.assignContractors(projectContractorAssignDto));
    }

    @PutMapping("/remove-contractor")
    ResponseEntity<ProjectContractorAssignResponse> removeContractor(@RequestBody ProjectContractorRemoveDto projectContractorRemoveDto) {
        return ResponseEntity.ok(service.removeContractor(projectContractorRemoveDto));
    }

}
