package com.agency.model;

import com.agency.auth.RoleType;
import com.agency.dto.userprofile.RoleDto;
import com.agency.dto.userprofile.UserProfileDetailsDto;

import java.util.List;

public class UserDetailsModel {

    public static UserProfileDetailsDto defaultUserDetails(){
        return new UserProfileDetailsDto(
                "admin",
                "admin@example.com",
                getRoleDto(),
                "firstName",
                "lastName",
                true
        );
    }

    private static RoleDto getRoleDto() {
        return new RoleDto(
                RoleType.ADMIN, List.of()
        );
    }
}
