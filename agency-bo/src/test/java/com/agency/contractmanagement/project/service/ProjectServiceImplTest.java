package com.agency.contractmanagement.project.service;

import com.agency.agencydetails.service.AgencyDetailsService;
import com.agency.contractmanagement.contractnumber.service.ContractNumberService;
import com.agency.contractmanagement.project.repository.ProjectRepository;
import com.agency.contractor.repository.ContractorRepository;
import com.agency.dict.contract.ContractType;
import com.agency.dto.project.ProjectCreateDto;
import com.agency.exception.AgencyException;
import com.agency.organizer.repository.OrganizerRepository;
import com.agency.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.agency.contractmanagement.project.model.ProjectModel.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceImplTest {

    private ProjectService service;
    private final ProjectRepository repository = mock(ProjectRepository.class);
    private final OrganizerRepository organizerRepository = mock(OrganizerRepository.class);
    private final ContractorRepository contractorRepository = mock(ContractorRepository.class);
    private final ContractNumberService contractNumberService = mock(ContractNumberService.class);
    private final AgencyDetailsService agencyDetailsService = mock(AgencyDetailsService.class);

    @BeforeEach
    void setup(){
        service = new ProjectServiceImpl(repository, organizerRepository, contractorRepository, contractNumberService, agencyDetailsService);
    }

    @Test
    public void should_throw_exception_when_organizer_does_not_exists() {
        // given
        ProjectCreateDto projectCreateDto = getProjectCreateDto();

        when(contractNumberService.createContractNumber(SIGN_DATE, ContractType.PROJECT)).thenReturn(PROJECT_NUMBER);
        when(organizerRepository.findOrganizerByPublicId(any())).thenReturn(Optional.empty());

        // when
        assertThrows(AgencyException.class, () -> service.addProject(projectCreateDto));

    }
}
