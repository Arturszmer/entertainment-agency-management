package com.agency.auth;

public record RegistrationRequest(
        String username,
        String email,
        String password,
        RoleType roleType
) {
    public static RegistrationRequest fromAdminInitializer(AdminInitializerDto admin){
        return new RegistrationRequest(
                admin.username(), admin.email(), admin.password(), RoleType.ADMIN
        );
    }
}
