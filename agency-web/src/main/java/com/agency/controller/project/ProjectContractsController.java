package com.agency.controller.project;

import com.agency.dto.contractwork.ContractWorkCreateDto;
import com.agency.dto.project.ProjectDto;
import com.agency.service.ProjectContractsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/project/contract-work")
@PreAuthorize("hasAuthority('CONTRACT_MANAGEMENT')")
public class ProjectContractsController {

    private final ProjectContractsService service;

    @PostMapping
    ResponseEntity<ProjectDto> addContractToProject(@RequestBody ContractWorkCreateDto request){
        return ResponseEntity.ok(service.createContract(request));
    }

}
