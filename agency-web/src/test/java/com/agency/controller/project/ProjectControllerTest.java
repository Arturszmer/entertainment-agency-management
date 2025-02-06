package com.agency.controller.project;

import com.agency.BaseIntegrationTestSettings;
import com.agency.contractmanagement.project.model.Project;
import com.agency.contractmanagement.project.repository.ProjectRepository;
import com.agency.dict.project.ProjectStatus;
import com.agency.dto.project.ProjectCreateDto;
import com.agency.dto.project.ProjectDto;
import com.agency.dto.project.ProjectStatusUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static com.agency.model.ProjectModel.getProjectCreateDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@WithMockUser(authorities = "PROJECT_MANAGEMENT")
@Sql(scripts = "/sql-init/30-project-init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/sql-init/30-project-clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
class ProjectControllerTest extends BaseIntegrationTestSettings {

    @Autowired
    private ProjectRepository repository;
    private static final String urlPath = "/project";

    @Test
    public void should_add_project() throws Exception {
        // given
        ProjectCreateDto projectCreateDto = getProjectCreateDto();
        String body = mapper.writeValueAsString(projectCreateDto);

        // when
        MvcResult mvcResult = postRequest(urlPath, body).andReturn();
        ProjectDto projectDto = mapper.readValue(mvcResult.getResponse().getContentAsString(), ProjectDto.class);
        Optional<Project> projectOpt = repository.findByContractNumber(projectDto.contractNumber());

        // then
        assertTrue(projectOpt.isPresent());
        assertEquals("D2024/STY/PRO2", projectOpt.get().getContractNumber());

    }

    @Test
    public void should_update_project_status() throws Exception {
        // given
        String contractNumber = "D2024/STY/PRO1";
        ProjectStatusUpdateRequest updateRequest = new ProjectStatusUpdateRequest(contractNumber, ProjectStatus.PROPOSITION);
        String body = mapper.writeValueAsString(updateRequest);

        // when
        putRequest(urlPath + "/status", body);
        Optional<Project> projectOpt = repository.findByContractNumber(contractNumber);

        // then
        assertTrue(projectOpt.isPresent());
        assertEquals(ProjectStatus.PROPOSITION, projectOpt.get().getStatus());
    }
}
