package com.agency.model;

import com.agency.dto.userprofile.UserProfileDetailsDto;

public class UserDetailsModel {

    public static UserProfileDetailsDto defaultUserDetails(){
        return new UserProfileDetailsDto(
                "admin",
                "admin@example.com",
                "firstName",
                "lastName"
        );
    }
}
