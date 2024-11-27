package com.agency.dto.userprofile;

import com.agency.dict.userProfile.Permission;

import java.util.Set;

public record AgencyPermissionsDto(
        Set<Permission> permissions,
        Set<Permission> userPermissions
) {
}
