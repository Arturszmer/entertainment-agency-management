package com.agency.controller.project;

import com.agency.dict.project.ProjectStatus;
import com.agency.dto.project.*;
import com.agency.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/project")
@PreAuthorize("hasAuthority('PROJECT_MANAGEMENT')")
public class ProjectController {

    private final ProjectService service;

    @PostMapping
    ResponseEntity<ProjectDto> create(@RequestBody ProjectCreateDto request){
        return ResponseEntity.ok(service.addProject(request));
    }

    @PutMapping("/status")
    ResponseEntity<ProjectDto> updateStatus(@RequestParam("contract-number") String contractNumber,
                                            @RequestParam("status")ProjectStatus status){
        return ResponseEntity.ok(service.updateStatus(contractNumber, status));
    }

    @PutMapping("/assign")
    ResponseEntity<ProjectContractorAssignResponse> assignContractors(@RequestBody ProjectContractorAssignDto projectContractorAssignDto){

        return ResponseEntity.ok(service.assignContractors(projectContractorAssignDto));
    }

    @PutMapping("/remove-contractor")
    ResponseEntity<ProjectContractorAssignResponse> removeContractor(@RequestBody ProjectContractorRemoveDto projectContractorRemoveDto){
        return ResponseEntity.ok(service.removeContractor(projectContractorRemoveDto));
    }

}
