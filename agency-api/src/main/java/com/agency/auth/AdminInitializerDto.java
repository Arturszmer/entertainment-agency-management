package com.agency.auth;

public record AdminInitializerDto(
        String username,
        String email,
        String password
) {
}
