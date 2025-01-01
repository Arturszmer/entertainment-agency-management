package com.agency.contractmanagement.project.service;

import com.agency.contractor.repository.ContractorRepository;
import com.agency.dict.contract.ContractType;
import com.agency.dict.project.ProjectStatus;
import com.agency.dto.project.ProjectCreateDto;
import com.agency.exception.AgencyException;
import com.agency.organizer.repository.OrganizerRepository;
import com.agency.contractmanagement.project.model.Project;
import com.agency.contractmanagement.project.model.ProjectBuilder;
import com.agency.contractmanagement.project.repository.ProjectRepository;
import com.agency.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Optional;

import static com.agency.contractmanagement.project.model.ProjectModel.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceImplTest {

    private ProjectService service;
    private final ProjectRepository repository = mock(ProjectRepository.class);
    private final ContractNumberGenerator generator = mock(ContractNumberGenerator.class);
    private final OrganizerRepository organizerRepository = mock(OrganizerRepository.class);
    private final ContractorRepository contractorRepository = mock(ContractorRepository.class);
    private final static String CONTRACT_NUMBER = "2024/STY/PRO1";


    @BeforeEach
    void setup(){
        service = new ProjectServiceImpl(repository, generator, organizerRepository, contractorRepository);
    }

    @Test
    public void should_throw_exception_when_organizer_does_not_exists() {
        // given
        ProjectCreateDto projectCreateDto = getProjectCreateDto();

        when(generator.generateContractNumber(SIGN_DATE, ContractType.PROJECT)).thenReturn(PROJECT_NUMBER);
        when(organizerRepository.findOrganizerByPublicId(any())).thenReturn(Optional.empty());

        // when
        assertThrows(AgencyException.class, () -> service.addProject(projectCreateDto));

    }

    @ParameterizedTest
    @EnumSource(value = ProjectStatus.class,
    names = {"SIGNED", "TERMINATED"},
    mode = EnumSource.Mode.INCLUDE)
    public void should_throw_exception_when_status_of_updated_project_is_terminated_or_signed(ProjectStatus projectStatus){
        // given
        Project project = ProjectBuilder.aProjectBuilder()
                .withProjectStatus(projectStatus)
                .withContractNumber(CONTRACT_NUMBER)
                .buildProject();

        when(repository.findByContractNumber(CONTRACT_NUMBER)).thenReturn(Optional.of(project));

        // then
        assertThrows(AgencyException.class, () -> service.updateStatus(CONTRACT_NUMBER, ProjectStatus.PROPOSITION));
    }
}
