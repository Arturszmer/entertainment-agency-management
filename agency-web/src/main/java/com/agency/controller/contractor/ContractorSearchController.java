package com.agency.controller.contractor;

import com.agency.dto.contractor.ContractorAssignDto;
import com.agency.dto.contractor.ContractorDto;
import com.agency.dto.contractor.ContractorSearchRequest;
import com.agency.dto.contractor.ContractorShortInfoDto;
import com.agency.service.ContractorSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/contractor")
@RequiredArgsConstructor
@Slf4j
public class ContractorSearchController {

    private final ContractorSearchService service;

    @GetMapping("/{public-id}")
    @PreAuthorize("hasAuthority('CONTRACTOR_MANAGEMENT')")
    public ResponseEntity<ContractorDto> getContractorDetails(@PathVariable("public-id") String publicId){
        ContractorDto contractorFullInfo = service.getContractorFullInfo(publicId);
        log.info("Fetch contractor full info, public id: {}", publicId);
        return ResponseEntity.ok(contractorFullInfo);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('CONTRACTORS_VIEW')")
    public ResponseEntity<Page<ContractorShortInfoDto>> getContractorsShortInfo(ContractorSearchRequest request) {
        return ResponseEntity.ok(service.getContractorsShortInfo(request));
    }

    @PostMapping("/to-assign")
    @PreAuthorize("hasAnyAuthority('CONTRACTORS_VIEW')")
    public ResponseEntity<List<ContractorAssignDto>> getContractorsList(@RequestBody String projectNumber){
        return ResponseEntity.ok(service.getContractorsForAssign(projectNumber));
    }
}
