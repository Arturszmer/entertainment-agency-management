package com.agency.calculate.service;

import com.agency.exception.AgencyException;
import com.agency.exception.ProjectErrorResult;
import com.agency.project.model.Project;
import com.agency.project.model.ProjectCost;
import com.agency.project.repository.ProjectRepository;
import com.agency.service.CalculateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectCalculateService implements CalculateService {

    private final ProjectRepository projectRepository;


    @Override
    @Transactional
    public BigDecimal getBalance(String publicId) {
        Project project = projectRepository.findProjectByPublicId(UUID.fromString(publicId))
                .orElseThrow(() -> new AgencyException(ProjectErrorResult.PROJECT_NOT_FOUND, publicId));
        BigDecimal salary = project.getSalary();
        BigDecimal totalCosts = project.getProjectCosts().stream()
                .map(ProjectCost::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return salary.subtract(totalCosts);
    }
}
