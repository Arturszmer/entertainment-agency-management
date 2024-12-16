package com.agency.user.service;

import com.agency.auth.ChangePasswordRequest;
import com.agency.dict.userProfile.Permission;
import com.agency.dto.userprofile.AgencyPermissionsDto;
import com.agency.dto.userprofile.UserProfileDetailsDto;
import com.agency.exception.AgencyException;
import com.agency.service.UserService;
import com.agency.user.assembler.UserAssembler;
import com.agency.user.helper.SecurityContextUsers;
import com.agency.user.model.UserProfile;
import com.agency.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

import static com.agency.exception.UserExceptionResult.*;
import static com.agency.user.constant.UserLogsMessage.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserProfileRepository repository;

    @Override
    public void changePassword(ChangePasswordRequest request, Principal principal) {
        String username = principal.getName();

        UserProfile user = getUserProfileByUsername(username);

        isCurrentPasswordCorrect(request.currentPassword(), user);
        isNewPasswordMatches(request.newPassword(), request.confirmationPassword());
        isNewPasswordDifferentThanCurrent(request.currentPassword(), request.newPassword());

        user.setNewPassword(passwordEncoder.encode(request.newPassword()));

        repository.save(user);
        log.info(PASSWORD_CHANGED, user.getUsername());
    }

    @Override
    public void blockUser(String usernameOrEmail) {
        UserProfile userProfile = getUserProfileByUsernameOfEmail(usernameOrEmail);
        isUserCurrentlyLogged(userProfile.getUsername());
        userProfile.lockUserAccount();
        repository.save(userProfile);
        log.info(BLOCKED_SUCCESSFULLY, userProfile.getUsername());
    }

    @Override
    public void unblockUser(String usernameOrEmail) {
        UserProfile userProfile = getUserProfileByUsernameOfEmail(usernameOrEmail);

        userProfile.unblockUserAccount();
        repository.save(userProfile);
        log.info(UNBLOCKED_SUCCESSFULLY, userProfile.getUsername());
    }

    @Override
    public String getLoggedUsername() {
        return SecurityContextUsers.getUsernameFromAuthenticatedUser();
    }

    @Override
    public UserProfileDetailsDto getUserDetails() {
        String username = SecurityContextUsers.getUsernameFromAuthenticatedUser();
        UserProfile userProfile = getUserProfileByUsername(username);
        log.info("User details of: {} has been fetched", userProfile.getUsername());
        return UserAssembler.toUserProfileDetailsDto(userProfile);
    }

    @Override
    public UserProfileDetailsDto changeCurrentUserDetails(UserProfileDetailsDto userProfileDetailsDto) {
        String username = SecurityContextUsers.getUsernameFromAuthenticatedUser();
        return editUserDetails(userProfileDetailsDto, username);
    }

    @Override
    public UserProfileDetailsDto changeUserDetails(UserProfileDetailsDto userProfileDetailsDto, String currentUsername) {
        return editUserDetails(userProfileDetailsDto, currentUsername);
    }

    @Override
    @Transactional(readOnly = true)
    public AgencyPermissionsDto getUserPermissions(String username) {
        UserProfile userProfile = getUserProfileByUsername(username);

        return new AgencyPermissionsDto(
                Permission.getPermissionsToManage(userProfile.getRole().getName()),
                userProfile.getPermissions());
    }

    @NotNull
    private UserProfileDetailsDto editUserDetails(UserProfileDetailsDto userProfileDetailsDto, String username) {
        UserProfile userProfile = getUserProfileByUsername(username);
        userProfile.updateUser(userProfileDetailsDto);
        UserProfile updatedUser = repository.save(userProfile);
        log.info("User details of: {} has been updated", userProfile.getUsername());
        return UserAssembler.toUserProfileDetailsDto(updatedUser);
    }

    private UserProfile getUserProfileByUsername(String username) {
        return repository.findUserProfileByUsername(username)
                .orElseThrow(() -> new RuntimeException(String.format(USER_NOT_FOUND.getMessage(), username)));
    }

    private UserProfile getUserProfileByUsernameOfEmail(String usernameOrEmail) {
        return repository.findUserProfileByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new RuntimeException(String.format(USER_NOT_FOUND.getMessage(), usernameOrEmail)));
    }

    private void isNewPasswordMatches(String newPassword, String confirmationPassword) {
        if(!newPassword.equals(confirmationPassword)){
            throw new AgencyException(NEW_PASSWORD_DOES_NOT_MATCH);
        }
    }

    private void isCurrentPasswordCorrect(String currentPassword, UserProfile user){
        if(!passwordEncoder.matches(currentPassword, user.getPassword())){
            throw new AgencyException(CURRENT_PASSWORD_DOES_NOT_MATCH);
        }
    }

    private void isUserCurrentlyLogged(String username) {
        if(username.equals(SecurityContextUsers.getUsernameFromAuthenticatedUser())){
            throw new AgencyException(CANNOT_BLOCK_YOURSELF);
        }
    }

    private void isNewPasswordDifferentThanCurrent(String currentPassword, String newPassword) {
        if(currentPassword.equals(newPassword)){
            throw new AgencyException(NEW_PASSWORD_MUST_BE_DIFFERENT_THAN_CURRENT);
        }
    }
}
