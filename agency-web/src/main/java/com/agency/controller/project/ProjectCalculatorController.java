package com.agency.controller.project;

import com.agency.contractmanagement.project.service.CostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/v1/project/balance")
@PreAuthorize("hasAnyAuthority('PROJECT_MANAGEMENT')")
@RequiredArgsConstructor
public class ProjectCalculatorController {

    private final CostService costService;

    @GetMapping("{public-id}")
    ResponseEntity<BigDecimal> getProjectBalance(@PathVariable("public-id") String publicId) {
        return ResponseEntity.ok(costService.getBalance(publicId));
    }
}
