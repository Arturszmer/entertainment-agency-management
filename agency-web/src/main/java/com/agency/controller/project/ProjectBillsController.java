package com.agency.controller.project;

import com.agency.dto.bill.ContractWorkBillDto;
import com.agency.service.ProjectSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/project")
@PreAuthorize("hasAnyAuthority('PROJECT_MANAGEMENT')")
@RequiredArgsConstructor
public class ProjectBillsController {

    private final ProjectSearchService projectSearchService;

    @GetMapping("/{public-id}/bills")
    ResponseEntity<Set<ContractWorkBillDto>> getProjectBills(@PathVariable("public-id") UUID publicId) {
        return ResponseEntity.ok(projectSearchService.getProjectBills(publicId));
    }
}
