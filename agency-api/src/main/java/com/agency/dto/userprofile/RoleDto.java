package com.agency.dto.userprofile;

import com.agency.auth.RoleType;
import com.agency.dict.userProfile.Permission;

import java.util.Set;

public record RoleDto(
        RoleType roleType,
        Set<Permission> permission
) {
}
