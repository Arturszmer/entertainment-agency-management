package com.agency.controller.user;

import com.agency.auth.ChangePasswordRequest;
import com.agency.dto.userprofile.UserProfileDetailsDto;
import com.agency.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.Principal;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request,
                                            Principal connectedUser) throws UserPrincipalNotFoundException {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/block/{usernameOrEmail}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> blockUser(@PathVariable("usernameOrEmail") String usernameOrEmail){
        userService.blockUser(usernameOrEmail);
        return ResponseEntity.ok(String.format("User with email %s has been blocked", usernameOrEmail));
    }

    @PutMapping("/unblock/{usernameOrEmail}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> unblockUser(@PathVariable("usernameOrEmail") String usernameOrEmail) {
        userService.unblockUser(usernameOrEmail);
        return ResponseEntity.ok(String.format("User with email %s has been blocked", usernameOrEmail));
    }

    @PutMapping("/edit")
    public ResponseEntity<UserProfileDetailsDto> editUserDetails(@RequestBody UserProfileDetailsDto userProfileDetailsDto){
        return ResponseEntity.ok(userService.changeUserDetails(userProfileDetailsDto));
    }

    @GetMapping("/username")
    public ResponseEntity<String> getUsername(){
        return ResponseEntity.ok(userService.getLoggedUsername());
    }

    @GetMapping("/details")
    public ResponseEntity<UserProfileDetailsDto> getDetails(){
        return ResponseEntity.ok(userService.getUserDetails());
    }
}
