package com.agency.project.service;

import com.agency.dict.contract.ContractType;
import com.agency.dict.project.ProjectStatus;
import com.agency.dto.project.ProjectCreateDto;
import com.agency.exception.AgencyException;
import com.agency.organizer.repository.OrganizerRepository;
import com.agency.project.model.Project;
import com.agency.project.model.ProjectBuilder;
import com.agency.project.repository.ProjectRepository;
import com.agency.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Optional;

import static com.agency.project.model.ProjectModel.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceImplTest {

    private ProjectService service;
    private final ProjectRepository repository = mock(ProjectRepository.class);
    private final ContractNumberGenerator generator = mock(ContractNumberGenerator.class);
    private final OrganizerRepository organizerRepository = mock(OrganizerRepository.class);

    @BeforeEach
    void setup(){
        service = new ProjectServiceImpl(repository, generator, organizerRepository);
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
        String contractNumber = "2024/STY/PRO1";
        Project project = ProjectBuilder.aProjectBuilder()
                .withProjectStatus(projectStatus)
                .withContractNumber(contractNumber)
                .buildProject();

        when(repository.findByContractNumber(contractNumber)).thenReturn(Optional.of(project));

        // then
        assertThrows(AgencyException.class, () -> service.updateStatus(contractNumber, ProjectStatus.PROPOSITION));
    }

}
