package com.agency.project.service;

import com.agency.contractmanagement.repository.ContractorRepository;
import com.agency.dto.contractwork.ContractWorkCreateDto;
import com.agency.dict.project.ProjectStatus;
import com.agency.exception.AgencyException;
import com.agency.project.model.Project;
import com.agency.project.model.ProjectBuilder;
import com.agency.project.repository.ProjectRepository;
import com.agency.service.ProjectContractsService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.agency.project.model.ProjectModel.PROJECT_NUMBER;
import static com.agency.project.model.ProjectModel.getContractWorkCreateDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectContractsServiceImplTest {

    private ProjectContractsService service;
    private final ProjectRepository repository = mock(ProjectRepository.class);
    private final ContractorRepository contractorRepository = mock(ContractorRepository.class);
    private final ContractNumberGenerator generator = mock(ContractNumberGenerator.class);

    @Test
    public void should_throw_an_exception_when_project_status_is_terminated() {
        //TODO: implement after changes of logic -> assign contractor to project
//        // given
//        service = new ProjectContractsServiceImpl(repository, contractorRepository, generator);
//        ContractWorkCreateDto contractWorkCreateDto = getContractWorkCreateDto();
//
//        Project project = ProjectBuilder.aProjectBuilder()
//                .withProjectStatus(ProjectStatus.TERMINATED)
//                .withContractNumber(PROJECT_NUMBER)
//                .buildProject();
//
//        when(repository.findByContractNumber(PROJECT_NUMBER)).thenReturn(Optional.of(project));
//
//        // then
//        assertThrows(AgencyException.class, () -> service.createContract(contractWorkCreateDto));
    }

}
