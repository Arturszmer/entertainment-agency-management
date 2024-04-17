package com.agency.auth;

public record AuthenticationRequest(
        String usernameOrEmail,
        String password
) {
}
