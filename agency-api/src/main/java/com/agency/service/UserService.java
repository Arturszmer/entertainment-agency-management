package com.agency.service;

import com.agency.auth.ChangePasswordRequest;
import com.agency.dto.userprofile.UserProfileDetailsDto;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.Principal;

public interface UserService {

    void changePassword(ChangePasswordRequest request, Principal principal) throws UserPrincipalNotFoundException;

    void blockUser(String usernameOrEmail);

    void unblockUser(String usernameOrEmail);

    String getLoggedUsername();

    UserProfileDetailsDto getUserDetails();

    UserProfileDetailsDto changeCurrentUserDetails(UserProfileDetailsDto userProfileDetailsDto);

    UserProfileDetailsDto changeUserDetails(UserProfileDetailsDto userProfileDetailsDto, String currentUsername);
}
