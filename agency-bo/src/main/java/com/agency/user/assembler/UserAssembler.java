package com.agency.user.assembler;

import com.agency.dto.userprofile.UserProfileDetailsDto;
import com.agency.user.model.UserProfile;

public class UserAssembler {

    public static UserProfileDetailsDto toUserProfileDetailsDto(UserProfile userProfile) {
        return new UserProfileDetailsDto(
                userProfile.getUsername(),
                userProfile.getEmail(),
                userProfile.getFirstName(),
                userProfile.getLastName()
        );
    }
}
