package com.agency.controller.contractor;

import com.agency.dto.contractor.ContractorDto;
import com.agency.service.ContractorSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
