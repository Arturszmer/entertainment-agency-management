package com.agency.controller;

import com.agency.BaseIntegrationTestSettings;
import com.agency.auth.ChangePasswordRequest;
import com.agency.auth.RegistrationRequest;
import com.agency.auth.RoleType;
import com.agency.authentication.AuthenticationService;
import com.agency.user.model.UserProfile;
import com.agency.user.repository.UserProfileRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    @WithMockUser(password = "old-password")
    public void should_change_password() throws Exception {

        // given
        authenticationService.register(createUserProfileForTest());

        // when
        postRequest("/user/change-password", getBodyToPasswordChange());

        // then
        Optional<UserProfile> user = repository.findUserProfileByUsername(USER);
        assertTrue(user.isPresent());
        assertTrue(passwordEncoder.matches("new-password", user.get().getPassword()));
    }

    private RegistrationRequest createUserProfileForTest() {
        return new RegistrationRequest(USER, USER_EMAIL, "old-password", RoleType.USER);
    }

    private String getBodyToPasswordChange() throws JsonProcessingException {
        return mapper.writeValueAsString(new ChangePasswordRequest("old-password",
                "new-password", "new-password"));
    }

}
