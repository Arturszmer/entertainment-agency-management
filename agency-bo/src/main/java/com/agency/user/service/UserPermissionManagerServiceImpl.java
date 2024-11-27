package com.agency.user.service;

import com.agency.auth.RoleType;
import com.agency.dict.userProfile.Permission;
import com.agency.exception.AgencyException;
import com.agency.exception.UserExceptionResult;
import com.agency.service.UserPermissionManagerService;
import com.agency.user.helper.SecurityContextUsers;
import com.agency.user.model.UserProfile;
import com.agency.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.agency.exception.UserExceptionResult.CANNOT_CHANGE_ADMIN_PERMISSIONS;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserPermissionManagerServiceImpl implements UserPermissionManagerService {

    private final UserProfileRepository userProfileRepository;

    @Override
    public void updateUserPermissions(String username, Set<Permission> updatedPermissions) {
        UserProfile changedUser = getUserProfile(username);
        String usernameFromContext = SecurityContextUsers.getUsernameFromAuthenticatedUser();
        UserProfile userFromContext = getUserProfile(usernameFromContext);
        validateIsUserFromContextCouldChangeAdminUser(changedUser, userFromContext);

        changedUser.updatePermissions(updatedPermissions);
        userProfileRepository.save(changedUser);
        log.info("User profile %s permissions updated: {}", updatedPermissions);
    }

    private void validateIsUserFromContextCouldChangeAdminUser(UserProfile changedUser, UserProfile userFromContext) {
        if(RoleType.ADMIN == userFromContext.getRole().getName()) {
            return;
        }
        if(RoleType.ADMIN == changedUser.getRole().getName()){
            throw new AgencyException(CANNOT_CHANGE_ADMIN_PERMISSIONS);
        }
    }

    private UserProfile getUserProfile(String username) {
        return userProfileRepository.findUserProfileByUsername(username)
                .orElseThrow(() -> new AgencyException(UserExceptionResult.USER_NOT_FOUND, username));
    }
}
