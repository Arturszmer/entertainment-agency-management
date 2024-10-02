package com.agency.controller.project;

import com.agency.dto.project.CostDto;
import com.agency.project.service.CostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/project/cost")
@PreAuthorize("hasAnyAuthority('PROJECT_MANAGEMENT')")
@RequiredArgsConstructor
public class ProjectCostController {

    private final CostService costService;

    @GetMapping("{public-id}")
    ResponseEntity<List<CostDto>> getAllCostsForProject(@PathVariable("public-id") String publicId) {
        return ResponseEntity.ok(costService.getAllCostsForProject(publicId));
    }
}
