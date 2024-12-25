package com.agency.controller.project;

import com.agency.dto.project.CostCreateDto;
import com.agency.dto.project.CostDto;
import com.agency.project.service.CostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/project/cost")
@PreAuthorize("hasAnyAuthority('PROJECT_MANAGEMENT')")
@RequiredArgsConstructor
public class ProjectCostController {

    private final CostService costService;

    @GetMapping("{public-id}")
    ResponseEntity<List<CostDto>> getAllCostsForProject(@PathVariable("public-id") String publicId) {
        return ResponseEntity.ok(costService.getAllCostsForProject(publicId));
    }

    @PostMapping
    ResponseEntity<CostDto> createCost(@RequestBody CostCreateDto costCreateDto) {
        return ResponseEntity.ok(costService.addCost(costCreateDto));
    }

    @PutMapping
    ResponseEntity<Void> updateCost(@RequestBody CostDto updateCost) {
        costService.updateCost(updateCost);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{public-id}")
    ResponseEntity<Void> deleteCost(@PathVariable("public-id") UUID publicId) {
        costService.deleteCost(publicId);
        return ResponseEntity.noContent().build();
    }
}
