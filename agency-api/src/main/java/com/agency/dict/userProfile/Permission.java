package com.agency.dict.userProfile;

import com.agency.auth.RoleType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum Permission {

    AGENCY_MANAGEMENT(List.of(RoleType.USER)),
    CONTRACT_MANAGEMENT(List.of(RoleType.USER)),
    CONTRACT_VIEW(List.of()),
    EVENT_MANAGEMENT(List.of(RoleType.USER)),
    EVENT_VIEW(List.of()),
    USER_MANAGEMENT(List.of(RoleType.USER)),
    USER_VIEW(List.of()),
    CONTRACTOR_MANAGEMENT(List.of(RoleType.USER)),
    CONTRACTORS_VIEW(List.of(RoleType.USER)),
    PROJECT_MANAGEMENT(List.of(RoleType.USER)),
    PROJECT_VIEW(List.of()),
    ORGANIZER_MANAGEMENT(List.of()),
    ORGANIZER_VIEW(List.of()),
    DOCUMENT_MANAGEMENT(List.of(RoleType.USER)),
    DOCUMENT_VIEW(List.of(RoleType.USER)),
    DOCUMENT_GENERATE(List.of(RoleType.USER)),;

    private final List<RoleType> roleTypesNotAvailable;

    public static Set<Permission> getPermissionsToManage(RoleType roleType) {
        return Arrays.stream(Permission.values())
                .filter(permission -> !permission.getRoleTypesNotAvailable().contains(roleType))
                .collect(Collectors.toSet());
    }

    public boolean canBeUpdated(Permission updatedPermission, RoleType roleType) {
        return getPermissionsToManage(roleType).contains(updatedPermission);
    }
}
