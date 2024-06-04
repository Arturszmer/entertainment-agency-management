package com.agency.controller.project;

import com.agency.BaseIntegrationTestSettings;
import com.agency.contractmanagement.model.contract.ContractWork;
import com.agency.contractmanagement.model.contractor.Contractor;
import com.agency.contractmanagement.repository.ContractWorkRepository;
import com.agency.contractmanagement.repository.ContractorRepository;
import com.agency.dto.contractwork.ContractWorkCreateDto;
import com.agency.dict.contract.ContractWorkStatus;
import com.agency.dto.project.ProjectDto;
import com.agency.project.model.Project;
import com.agency.project.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static com.agency.model.ContractWorkModel.*;
import static org.junit.jupiter.api.Assertions.*;

@WithMockUser(authorities = "CONTRACT_MANAGEMENT")
@Sql(scripts = "/sql-init/project-contracts-controller-init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/sql-init/project-contracts-controller-clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
@Transactional
class ProjectContractsControllerTest extends BaseIntegrationTestSettings {

    @Autowired
    private ProjectRepository repository;
    @Autowired
    private ContractWorkRepository contractWorkRepository;
    @Autowired
    private ContractorRepository contractorRepository;

    private static final String urlPath = "/project/contract-work";

    @Test
    public void should_add_contract_to_existing_project() throws Exception {
        // given
        ContractWorkCreateDto contractWorkCreateDto = getContractWorkCreateDto();
        String body = mapper.writeValueAsString(contractWorkCreateDto);

        // when
        MvcResult mvcResult = postRequest(urlPath, body).andReturn();
        ProjectDto projectDto = mapper.readValue(mvcResult.getResponse().getContentAsString(), ProjectDto.class);

        Optional<Contractor> contractorByPublicId = contractorRepository.findContractorByPublicId(UUID.fromString(CONTRACTOR_PUBLIC_ID));
        Optional<Project> projectOpt = repository.findByContractNumber(PROJECT_NUMBER);

        assertTrue(projectOpt.isPresent());
        Optional<ContractWork> contractOpt = contractWorkRepository.findByContractNumber(projectDto.contracts().get(0).contractNumber());

        // then
        assertTrue(contractorByPublicId.isPresent());
        assertTrue(contractOpt.isPresent());

        assertEquals(1, projectOpt.get().getContracts().size());
        assertEquals(1, contractorByPublicId.get().getContract().size());

        assertEquals(UUID.fromString(CONTRACTOR_PUBLIC_ID), contractOpt.get().getContractor().getPublicId());
        assertEquals(ContractWorkStatus.DRAFT, contractOpt.get().getStatus());

        assertNotNull(contractOpt.get().getContractor());
    }

}
