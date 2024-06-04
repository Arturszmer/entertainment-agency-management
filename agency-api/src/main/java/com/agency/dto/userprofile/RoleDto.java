package com.agency.dto.userprofile;

import com.agency.auth.RoleType;
import com.agency.dict.userProfile.Permission;

import java.util.List;

public record RoleDto(
        RoleType roleType,
        List<Permission> permission
) {
}
