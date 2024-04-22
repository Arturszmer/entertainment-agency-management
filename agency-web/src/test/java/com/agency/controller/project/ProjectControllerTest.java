package com.agency.controller.project;

import com.agency.BaseIntegrationTestSettings;
import com.agency.dto.project.ProjectCreateDto;
import com.agency.dto.project.ProjectDto;
import com.agency.project.model.Project;
import com.agency.project.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static com.agency.model.ProjectModel.getProjectCreateDto;
import static org.junit.jupiter.api.Assertions.*;

@WithMockUser(authorities = "PROJECT_MANAGEMENT")
class ProjectControllerTest extends BaseIntegrationTestSettings {

    @Autowired
    private ProjectRepository repository;
    private final static String urlPath = "/project";

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
        assertEquals("2024/STY/PRO1", projectOpt.get().getContractNumber());

    }
}
