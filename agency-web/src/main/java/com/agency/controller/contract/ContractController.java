package com.agency.controller.contract;

import com.agency.dto.contractwork.ContractWorkCreateDto;
import com.agency.dto.contractwork.ContractWorkDto;
import com.agency.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAuthority('CONTRACT_MANAGEMENT')")
@RequestMapping("api/v1/contract-work")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService service;

    @PostMapping
    public ResponseEntity<ContractWorkDto> createContract(@RequestBody ContractWorkCreateDto createDto){
        return ResponseEntity.ok(service.createContractOfWork(createDto));
    }

    @DeleteMapping("/{public-id}")
    public ResponseEntity<Void> deleteContract(@PathVariable("public-id") String publicId){
        service.deleteContractOfWork(publicId);
        return ResponseEntity.noContent().build();
    }
}
