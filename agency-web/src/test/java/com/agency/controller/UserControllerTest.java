package com.agency.controller;

import com.agency.BaseIntegrationTestSettings;
import com.agency.auth.AuthenticationRequest;
import com.agency.auth.ChangePasswordRequest;
import com.agency.auth.RegistrationRequest;
import com.agency.auth.RoleType;
import com.agency.authentication.AuthenticationService;
import com.agency.user.model.UserProfile;
import com.agency.user.repository.UserProfileRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@Sql(scripts = "/sql-init/user-profile-init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/sql-init/user-profile-clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
class UserControllerTest extends BaseIntegrationTestSettings {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserProfileRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String USER = "user";
    private static final String USER_EMAIL = "user@user.com";
    private static final String PASSWORD = "password";

    @Test
    @WithMockUser
    public void should_change_password() throws Exception {

        // given
        authenticationService.register(createUserProfileForTest());

        // when
        putRequest("/user/change-password", getBodyToPasswordChange());

        // then
        Optional<UserProfile> user = repository.findUserProfileByUsername(USER);
        assertTrue(user.isPresent());
        assertTrue(passwordEncoder.matches("new-password", user.get().getPassword()));
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    public void should_block_user() throws Exception {
        // given
        String username = "userTest";

        // when
        putRequest("/user/block/" + username, "").andReturn();

        // then
        Optional<UserProfile> userProfileByUsername = repository.findUserProfileByUsername(username);

        assertTrue(userProfileByUsername.isPresent());
        assertFalse(userProfileByUsername.get().isAccountNonLocked());

        AuthenticationRequest request = new AuthenticationRequest("userTest", PASSWORD);
        String body = mapper.writeValueAsString(request);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth/login")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertTrue(responseBody.contains("LOGIN FAILED"));

    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    public void should_unblock_user() throws Exception {
        // given
        String username = "userTestLocked";

        // when
        putRequest("/user/unblock/" + username, "").andReturn();

        // then
        Optional<UserProfile> userProfileByUsername = repository.findUserProfileByUsername(username);

        assertTrue(userProfileByUsername.isPresent());
        assertTrue(userProfileByUsername.get().isAccountNonLocked());
    }

    private RegistrationRequest createUserProfileForTest() {
        return new RegistrationRequest(USER, USER_EMAIL, PASSWORD, RoleType.USER);
    }

    private String getBodyToPasswordChange() throws JsonProcessingException {
        return mapper.writeValueAsString(new ChangePasswordRequest(PASSWORD,
                "new-password", "new-password"));
    }

}
