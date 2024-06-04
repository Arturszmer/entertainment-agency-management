package com.agency.user.assembler;

import com.agency.dto.userprofile.RoleDto;
import com.agency.dto.userprofile.UserProfileDetailsDto;
import com.agency.user.model.Role;
import com.agency.user.model.UserProfile;

public class UserAssembler {

    public static UserProfileDetailsDto toUserProfileDetailsDto(UserProfile userProfile) {
        return new UserProfileDetailsDto(
                userProfile.getUsername(),
                userProfile.getEmail(),
                toRoleDto(userProfile.getRole()),
                userProfile.getFirstName(),
                userProfile.getLastName(),
                !userProfile.isAccountNonLocked()
        );
    }

    private static RoleDto toRoleDto(Role role) {
        return new RoleDto(
                role.getName(),
                role.getPermissions()
        );
    }
}
