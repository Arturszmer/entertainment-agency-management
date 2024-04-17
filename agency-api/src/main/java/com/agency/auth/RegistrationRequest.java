package com.agency.auth;

public record RegistrationRequest(
        String username,
        String email,
        String password,
        RoleType roleType
) {
}
