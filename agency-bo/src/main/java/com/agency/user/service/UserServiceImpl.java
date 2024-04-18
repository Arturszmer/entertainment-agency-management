package com.agency.user.service;

import com.agency.auth.ChangePasswordRequest;
import com.agency.service.UserService;
import com.agency.user.model.UserProfile;
import com.agency.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserProfileRepository repository;

    @Override
    public void changePassword(ChangePasswordRequest request, Principal principal) {
        String username = principal.getName();

        UserProfile user = repository.findUserProfileByUsername(username)
                .orElseThrow(() ->  new UsernameNotFoundException(String.format("User %s is not found", username)));

        isCurrentPasswordCorrect(request.currentPassword(), user);
        isNewPasswordMatches(request.newPassword(), request.confirmationPassword());

        user.setNewPassword(passwordEncoder.encode(request.newPassword()));

        repository.save(user);
        log.info("Password for user: {} has been changed", user.getUsername());
    }

    @Override
    public void blockUser(String usernameOrEmail) {
        UserProfile userProfile = repository.findUserProfileByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User has not fount by username(or email): " + usernameOrEmail));

        userProfile.lockUserAccount();
        repository.save(userProfile);
        log.info("User {} has been blocked succesfully", userProfile.getUsername());
    }

    @Override
    public void unblockUser(String usernameOrEmail) {
        UserProfile userProfile = repository.findUserProfileByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User has not fount by username(or email): " + usernameOrEmail));

        userProfile.unblockUserAccount();
        repository.save(userProfile);
        log.info("User {} has been unblocked succesfully", userProfile.getUsername());
    }

    private void isNewPasswordMatches(String newPassword, String confirmationPassword) {
        if(!newPassword.equals(confirmationPassword)){
            throw new IllegalStateException("New password is not matched with confirmation password");
        }
    }

    private void isCurrentPasswordCorrect(String currentPassword, UserProfile user){
        if(!passwordEncoder.matches(currentPassword, user.getPassword())){
            throw new IllegalStateException("The current password is incorrect");
        }
    }
}
