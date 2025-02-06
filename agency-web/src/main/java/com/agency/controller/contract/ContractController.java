package com.agency.controller.contract;

import com.agency.dto.contractwork.ContractWorkCreateDto;
import com.agency.dto.contractwork.ContractWorkDto;
import com.agency.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/confirm/{public-id}")
    public ResponseEntity<Void> confirmContract(@PathVariable("public-id") String publicId){
        service.confirmContract(publicId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/cancel-confirmation/{public-id}")
    public ResponseEntity<Void> cancelConfirmation(@PathVariable("public-id") String publicId){
        service.cancelConfirmation(publicId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{public-id}")
    public ResponseEntity<Void> deleteContract(@PathVariable("public-id") String publicId){
        service.deleteContractOfWork(publicId);
        return ResponseEntity.noContent().build();
    }
}
