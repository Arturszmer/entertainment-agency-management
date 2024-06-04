package com.agency.dto.userprofile;

public record UserProfileDetailsDto(
        String username,
        String email,
        RoleDto role,
        String firstName,
        String lastName,
        boolean blocked) {
}
