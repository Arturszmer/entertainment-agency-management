package com.agency.controller;

import com.agency.BaseIntegrationTestSettings;
import com.agency.auth.AdminInitializerDto;
import com.agency.auth.AuthenticationResponse;
import com.agency.auth.RegistrationRequest;
import com.agency.auth.RoleType;
import com.agency.user.model.Role;
import com.agency.user.model.UserProfile;
import com.agency.user.repository.UserProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends BaseIntegrationTestSettings {

    @Autowired
    private UserProfileRepository repository;

    @Test
    public void shouldReturn401WhenUserIsNotAuthenticated() throws Exception {
        // given
        mockMvc.perform(get("/"))
                .andExpect(status().isUnauthorized());
        getRequest("/", new LinkedMultiValueMap<>()).andExpect(status().isUnauthorized());
    }

    @Test
    public void should_create_admin_account() throws Exception {
        String username = "agency-admin";
        AdminInitializerDto request = new AdminInitializerDto(username, "admin@admin.pl", "password");
        String body = mapper.writeValueAsString(request);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth/admin-initializer")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        AuthenticationResponse authenticationResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), AuthenticationResponse.class);

        // then
        assertNotNull(authenticationResponse.accessToken());
        assertEquals(200, mvcResult.getResponse().getStatus());

        Optional<UserProfile> userProfileByUsername = repository.findUserProfileByUsername(username);
        assertTrue(userProfileByUsername.isPresent());
        RoleType roleType = userProfileByUsername.get().getRoles().stream()
                .filter(role -> RoleType.ADMIN == role.getName())
                .findFirst()
                .map(Role::getName)
                .orElseThrow();
        assertEquals(RoleType.ADMIN, roleType);
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    public void should_register_new_user() throws Exception {
        // given
        String username = "user-auth-controller";
        RegistrationRequest request = new RegistrationRequest(username, "user@user.pl", "password", RoleType.USER);
        String body = mapper.writeValueAsString(request);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth/register")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        AuthenticationResponse authenticationResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), AuthenticationResponse.class);

        // then
        assertNotNull(authenticationResponse.accessToken());
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertTrue(repository.findUserProfileByUsername(username).isPresent());
    }
}
