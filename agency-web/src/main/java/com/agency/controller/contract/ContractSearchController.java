package com.agency.controller.contract;

import com.agency.dto.contractwork.ContractWorkSearchRequest;
import com.agency.dto.contractwork.ContractWorkSearchResultDto;
import com.agency.service.ContractSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasAuthority('CONTRACT_VIEW')")
@RequestMapping("api/v1/contract-work")
@RequiredArgsConstructor
public class ContractSearchController {

    private final ContractSearchService contractSearchService;

    @GetMapping("/find")
    ResponseEntity<Page<ContractWorkSearchResultDto>> findContractWork(ContractWorkSearchRequest searchRequest) {
        return ResponseEntity.ok(contractSearchService.findAllContracts(searchRequest));
    }
}
