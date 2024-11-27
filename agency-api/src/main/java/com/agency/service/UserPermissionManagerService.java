package com.agency.service;

import com.agency.dict.userProfile.Permission;

import java.util.Set;

public interface UserPermissionManagerService {

    void updateUserPermissions(String username, Set<Permission> updatedPermissions);
}
