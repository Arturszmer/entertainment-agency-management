package com.agency.controller.project;

import com.agency.dto.project.ProjectDto;
import com.agency.dto.project.ProjectSearchDto;
import com.agency.dto.project.ProjectToAssignContractorDto;
import com.agency.service.ProjectSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/project")
@PreAuthorize("hasAnyAuthority('PROJECT_VIEW')")
@RequiredArgsConstructor
public class ProjectSearchController {

    private final ProjectSearchService searchService;

    @GetMapping
    public ResponseEntity<Page<ProjectSearchDto>> getAll(@RequestParam("page") int page,
                                                         @RequestParam("size") int size,
                                                         @RequestParam(required = false, value = "sort") String sort,
                                                         @RequestParam(required = false, value = "order") String order){
        return ResponseEntity.ok(searchService.findAll(page, size, sort, order));
    }

    @GetMapping("/{public-id}")
    public ResponseEntity<ProjectDto> getProjectDetails(@PathVariable("public-id") String publicId){
        return ResponseEntity.ok(searchService.getProjectDetails(publicId));
    }

    @GetMapping("/to-assign")
    public ResponseEntity<List<ProjectToAssignContractorDto>> getAllProjectsToAssign(@RequestParam("public-id") String assignedContractorPublicId){
        return ResponseEntity.ok(searchService.findAllToAssign(assignedContractorPublicId));
    }
}
