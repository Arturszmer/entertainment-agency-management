package com.agency.controller.contractor;

import com.agency.dto.contractor.ContractorDto;
import com.agency.dto.contractor.ShortContractorDto;
import com.agency.service.ContractorSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/contractor")
@RequiredArgsConstructor
@Slf4j
public class ContractorSearchController {

    private final ContractorSearchService service;

    @GetMapping("/{public-id}")
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('CONTRACTOR_MANAGEMENT')")
    public ResponseEntity<ContractorDto> getContractorDetails(@PathVariable("public-id") String publicId){
        ContractorDto contractorFullInfo = service.getContractorFullInfo(publicId);
        log.info("Fetch contractor full info, public id: {}", publicId);
        return ResponseEntity.ok(contractorFullInfo);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('CONTRACTORS_VIEW')")
    public ResponseEntity<Page<ShortContractorDto>> getContractorsShortInfo(@RequestParam("page") int page,
                                                                            @RequestParam("size") int size){
        return ResponseEntity.ok(service.getContractorsShortInfo(page, size));
    }
}
