package com.agency.controller.agency;

import com.agency.agencydetails.service.AgencyDetailsService;
import com.agency.dto.agencydetails.AgencyDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/agency-details")
@PreAuthorize("hasRole('ADMIN')")
public class AgencyDetailsController {

    private final AgencyDetailsService service;

    @PostMapping
    ResponseEntity<AgencyDetailsDto> initializeAgency(@RequestBody AgencyDetailsDto agencyDetailsDto){
        return ResponseEntity.ok(service.initializeAgency(agencyDetailsDto));
    }

    @PutMapping
    ResponseEntity<AgencyDetailsDto> updateAgencyDetails(@RequestBody AgencyDetailsDto agencyDetailsDto){
        return ResponseEntity.ok(service.updateAgencyDetails(agencyDetailsDto));
    }
}
