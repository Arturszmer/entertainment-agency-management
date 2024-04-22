package com.agency.project.service;

import com.agency.dto.project.ProjectCreateDto;
import com.agency.dto.project.ProjectDto;
import com.agency.project.assembler.ProjectAssembler;
import com.agency.project.model.Project;
import com.agency.project.repository.ProjectRepository;
import com.agency.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;

    @Override
    public ProjectDto addProject(ProjectCreateDto projectCreateDto) {
        String contractNumber = generateContractNumber(projectCreateDto.signDate());
        Project project = repository.save(Project.create(contractNumber, projectCreateDto));
        log.info("New project with contract number {} and DRAFT status has been created.", contractNumber);
        return ProjectAssembler.toDto(project);
    }

    /*
    Contract number definition: year of sign / month / PRO + number of prepared contracts this year
     */
    private String generateContractNumber(LocalDate signDate) {
        int year = signDate.getYear();
        Month month = signDate.getMonth();
        int numberOfContractsByYear = repository.getNumberOfContractsByYear(year);
        String polishMonth = month.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pl-PL"))
                .substring(0, 3)
                .toUpperCase();
        return String.format("%d/%s/PRO%d", year, polishMonth, numberOfContractsByYear + 1);
    }
}
