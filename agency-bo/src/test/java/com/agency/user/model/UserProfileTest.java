package com.agency.user.model;

import com.agency.auth.RoleType;
import com.agency.dict.userProfile.Permission;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserProfileTest {

    @Test
    public void shouldUpdatePermissions() {
        // given
        UserProfile userProfile = UserProfileBuilder.aUserProfile()
                .withBasicData()
                .withRoles(getRole())
                .build();

        // when
        Set<Permission> updatedPermissions = new HashSet<>();
        updatedPermissions.add(Permission.CONTRACTOR_MANAGEMENT);
        updatedPermissions.add(Permission.CONTRACTORS_VIEW);
        updatedPermissions.add(Permission.DOCUMENT_MANAGEMENT);
        userProfile.updatePermissions(updatedPermissions);

        // then
        assertEquals(3, userProfile.getPermissions().size());
        assertTrue(userProfile.getPermissions().contains(Permission.DOCUMENT_MANAGEMENT));

    }

    private List<Role> getRole() {
        Set<Permission> permissions = new HashSet<>();
        permissions.add(Permission.CONTRACTOR_MANAGEMENT);
        permissions.add(Permission.CONTRACTORS_VIEW);
        return List.of(RoleBuilder.aRole()
                        .withName(RoleType.MANAGER)
                        .withPermissions(permissions)
                .build());
    }

}
