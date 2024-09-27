package com.agency.controller;

import com.agency.BaseIntegrationTestSettings;
import com.agency.auth.*;
import com.agency.authentication.AuthenticationService;
import com.agency.dto.userprofile.UserProfileDetailsDto;
import com.agency.user.model.UserProfile;
import com.agency.user.repository.UserProfileRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Optional;

import static com.agency.model.UserDetailsModel.defaultUserDetails;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@Sql(scripts = "/sql-init/10-user-profile-init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/sql-init/10-user-profile-clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
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
        authenticationService.register(createUserProfileForTest(USER, USER_EMAIL));

        // when
        putRequest("/user/change-password", getBodyToPasswordChange());

        // then
        Optional<UserProfile> user = repository.findUserProfileByUsername(USER);
        assertTrue(user.isPresent());
        assertTrue(passwordEncoder.matches("Agency2024", user.get().getPassword()));
    }

    @Test
    @Order(1)
    @WithMockUser(authorities = "ROLE_ADMIN")
    public void should_block_user() throws Exception {
        // given
        String username = "USER2";
        String email = "USER2@email.pl";
        authenticationService.register(createUserProfileForTest(username, email));

        // when
        putRequest("/user/block/" + username, "").andReturn();

        // then
        Optional<UserProfile> userProfileByUsername = repository.findUserProfileByUsername(username);

        assertTrue(userProfileByUsername.isPresent());
        assertFalse(userProfileByUsername.get().isAccountNonLocked());

        AuthenticationRequest request = new AuthenticationRequest(username, PASSWORD);
        String body = mapper.writeValueAsString(request);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth/login")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertTrue(responseBody.contains("ERR001"));

    }

    @Test
    @Order(2)
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

    @Test
    @WithMockUser(username = "userTest")
    void should_get_user_details() throws Exception {
        // given
        // when
        MvcResult mvcResult = getRequest("/user/details", new LinkedMultiValueMap<>()).andReturn();

        // then
        UserProfileDetailsDto userProfileDetailsDto = mapper.readValue(mvcResult.getResponse().getContentAsString(), UserProfileDetailsDto.class);
        assertEquals("userTest", userProfileDetailsDto.username());
        assertEquals("test@example.com", userProfileDetailsDto.email());
        assertEquals("userTest", userProfileDetailsDto.firstName());
        assertEquals("user", userProfileDetailsDto.lastName());
    }

    @Test
    @WithMockUser(username = "admin")
    void should_change_user_details() throws Exception {
        // given
        UserProfileDetailsDto user = defaultUserDetails();
        Optional<UserProfile> userBeforeEdition = repository.findUserProfileByUsername(user.username());
        assertTrue(userBeforeEdition.isPresent());
        assertNotEquals(userBeforeEdition.get().getFirstName(), user.firstName());
        assertNotEquals(userBeforeEdition.get().getLastName(), user.lastName());

        // when - change firstName and lastName
        putRequest("/user/edit", mapper.writeValueAsString(user)).andReturn();

        // then
        Optional<UserProfile> editedUser = repository.findUserProfileByUsername(user.username());

        assertTrue(editedUser.isPresent());
        assertEquals(user.firstName(), editedUser.get().getFirstName());
        assertEquals(user.lastName(), editedUser.get().getLastName());

    }

    private CreateUserRequest createUserProfileForTest(String username, String email) {
        return new CreateUserRequest(username, email, "FirstName", "LastName", RoleType.USER);
    }

    private String getBodyToPasswordChange() throws JsonProcessingException {
        return mapper.writeValueAsString(new ChangePasswordRequest(PASSWORD,
                "new-password", "new-password"));
    }

}
