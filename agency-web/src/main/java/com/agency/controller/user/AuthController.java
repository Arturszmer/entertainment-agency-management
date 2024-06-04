package com.agency.controller.user;

import com.agency.auth.*;
import com.agency.authentication.AuthenticationService;
import com.agency.dto.userprofile.UserProfileDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authService;

    @GetMapping("/is-initialized")
    public ResponseEntity<Boolean> isNeedToBeInitialized(){
        return ResponseEntity.ok(authService.isInitialized());
    }

    @PostMapping("/admin-initializer")
    public ResponseEntity<AuthenticationResponse> adminInitialize(@RequestBody AdminInitializerDto request){
        return ResponseEntity.ok(authService.adminInitialize(request));
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserProfileDetailsDto> register(@RequestBody CreateUserRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PutMapping("/reset-password/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> resetPassword(@PathVariable("username") String username){
        authService.resetPassword(username);
        return ResponseEntity.ok(String.format("User %s password has been reset successfully", username));
    }
}
