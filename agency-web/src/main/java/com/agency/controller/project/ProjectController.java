package com.agency.controller.project;

import com.agency.dto.project.ProjectCreateDto;
import com.agency.dto.project.ProjectDto;
import com.agency.dto.project.ProjectStatus;
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
    ResponseEntity<ProjectDto> create(@RequestBody ProjectCreateDto request){
        return ResponseEntity.ok(service.addProject(request));
    }

    @PutMapping("/status")
    ResponseEntity<ProjectDto> updateStatus(@RequestParam("contract-number") String contractNumber,
                                            @RequestParam("status")ProjectStatus status){
        return ResponseEntity.ok(service.updateStatus(contractNumber, status));
    }

}
