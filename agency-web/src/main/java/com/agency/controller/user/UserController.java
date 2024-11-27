package com.agency.controller.user;

import com.agency.auth.ChangePasswordRequest;
import com.agency.dto.userprofile.AgencyPermissionsDto;
import com.agency.dto.userprofile.UserProfileDetailsDto;
import com.agency.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.Principal;

@RestController
@RequestMapping("api/v1/user")
@Slf4j
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
    public ResponseEntity<Void> blockUser(@PathVariable("usernameOrEmail") String usernameOrEmail){
        userService.blockUser(usernameOrEmail);
        log.info("User with email {} has been blocked", usernameOrEmail);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/unblock/{usernameOrEmail}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> unblockUser(@PathVariable("usernameOrEmail") String usernameOrEmail) {
        userService.unblockUser(usernameOrEmail);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit-user/{username}")
    @PreAuthorize("hasAuthority('USER_MANAGEMENT')")
    public ResponseEntity<UserProfileDetailsDto> editUserByUsername(@PathVariable("username") String currentUsername, @RequestBody UserProfileDetailsDto userProfileDetailsDto){
        return ResponseEntity.ok(userService.changeUserDetails(userProfileDetailsDto, currentUsername));
    }

    @PutMapping("/edit")
    public ResponseEntity<UserProfileDetailsDto> editUserDetails(@RequestBody UserProfileDetailsDto userProfileDetailsDto){
        return ResponseEntity.ok(userService.changeCurrentUserDetails(userProfileDetailsDto));
    }

    @GetMapping("/username")
    public ResponseEntity<String> getUsername(){
        return ResponseEntity.ok(userService.getLoggedUsername());
    }

    @GetMapping("/details")
    public ResponseEntity<UserProfileDetailsDto> getDetails(){
        return ResponseEntity.ok(userService.getUserDetails());
    }

    @GetMapping("/roles/{username}")
    @PreAuthorize("hasAuthority('USER_MANAGEMENT')")
    public ResponseEntity<AgencyPermissionsDto> getUserRoles(@PathVariable("username") String currentUsername){
        return ResponseEntity.ok(userService.getUserPermissions(currentUsername));
    }
}
