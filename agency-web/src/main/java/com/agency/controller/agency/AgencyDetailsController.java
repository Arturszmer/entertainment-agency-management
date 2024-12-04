package com.agency.controller.agency;

import com.agency.agencydetails.service.AgencyDetailsService;
import com.agency.dto.agencydetails.AgencyDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/agency-details")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
public class AgencyDetailsController {

    private final AgencyDetailsService service;

    @PostMapping
    @PreAuthorize("hasAuthority('AGENCY_MANAGEMENT')")
    ResponseEntity<AgencyDetailsDto> initializeAgency(@RequestBody AgencyDetailsDto agencyDetailsDto){
        return ResponseEntity.ok(service.initializeAgency(agencyDetailsDto));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('AGENCY_MANAGEMENT')")
    ResponseEntity<AgencyDetailsDto> updateAgencyDetails(@RequestBody AgencyDetailsDto agencyDetailsDto){
        return ResponseEntity.ok(service.updateAgencyDetails(agencyDetailsDto));
    }

    @GetMapping
    ResponseEntity<AgencyDetailsDto> getAgencyDetails(){
        return ResponseEntity.ok(service.getAgencyDetails());
    }
}
