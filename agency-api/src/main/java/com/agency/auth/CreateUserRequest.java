package com.agency.auth;

public record CreateUserRequest(
        String username,
        String email,
        String firstName,
        String lastName,
        RoleType roleType

) {
}
