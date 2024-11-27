package com.agency.user.model;

import com.agency.auth.RoleType;
import com.agency.dict.userProfile.Permission;

import java.util.HashSet;
import java.util.Set;

public class RoleBuilder {

    private RoleType name;
    private Set<Permission> permissions = new HashSet<>();

    public static RoleBuilder aRole() {
        return new RoleBuilder();
    }

    public RoleBuilder withName(RoleType name) {
        this.name = name;
        return this;
    }

    public RoleBuilder withPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
        return this;
    }

    public Role build() {
        return new Role(name, permissions);
    }

}
