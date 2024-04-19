package com.agency.controller.contractor;

import com.agency.dto.contractor.ContractorCreateRequest;
import com.agency.dto.contractor.ContractorDto;
import com.agency.service.ContractorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasAuthority('CONTRACTOR_MANAGEMENT')")
@RequestMapping("api/v1/contractor")
@RequiredArgsConstructor
public class ContractorController {

    private final ContractorService service;

    @PostMapping
    public ResponseEntity<ContractorDto> add(@RequestBody ContractorCreateRequest request){
        return ResponseEntity.ok(service.add(request));
    }

    @PutMapping("/{public-id}")
    public ResponseEntity<ContractorDto> edit(@PathVariable("public-id") String publicId, @RequestBody ContractorCreateRequest request){
        return ResponseEntity.ok(service.edit(publicId, request));
    }

    @DeleteMapping("/{public-id}")
    public ResponseEntity<Void> delete(@PathVariable("public-id") String publicId){
        service.delete(publicId);
        return ResponseEntity.ok().build();
    }
}
