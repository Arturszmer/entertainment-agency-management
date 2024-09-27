package com.agency.controller.project;

import com.agency.BaseIntegrationTestSettings;
import com.agency.dto.contractor.ContractorAssignDto;
import com.agency.dto.project.ProjectContractorAssignDto;
import com.agency.dto.project.ProjectContractorAssignResponse;
import com.agency.project.model.Project;
import com.agency.project.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.agency.model.ContractWorkModel.PROJECT_NUMBER;
import static com.agency.model.ContractorModel.*;
import static org.junit.jupiter.api.Assertions.*;

@WithMockUser(authorities = "PROJECT_MANAGEMENT")
@Sql(scripts = "/sql-init/21-project-contractors-controller-init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/sql-init/21-project-contractors-controller-clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
@Transactional
class ProjectContractorsControllerTest extends BaseIntegrationTestSettings {

    @Autowired
    private ProjectRepository repository;

    private static final String urlPath = "/project/assign";

    @Test
    public void should_add_contractors_to_existing_project() throws Exception {
        ContractorAssignDto johnDoe = contractorAssignDtoRequestBuild(PUBLIC_ID, "John Doe", false);
        ProjectContractorAssignDto projectContractorAssignDto = assignProjectContractorRequestBuild(PROJECT_NUMBER, List.of(johnDoe));
        String body = mapper.writeValueAsString(projectContractorAssignDto);

        // when
        MvcResult mvcResult = putRequest(urlPath, body).andReturn();
        ProjectContractorAssignResponse response = mapper.readValue(mvcResult.getResponse().getContentAsString(), ProjectContractorAssignResponse.class);

        Optional<Project> projectOpt = repository.findByContractNumber(PROJECT_NUMBER);

        assertTrue(projectOpt.isPresent());

        // then
        assertFalse(response.contractors().isEmpty());
        assertEquals(response.contractors().get(0).publicId(), johnDoe.publicId());

        assertEquals(1, projectOpt.get().getContractors().size());
    }
}
