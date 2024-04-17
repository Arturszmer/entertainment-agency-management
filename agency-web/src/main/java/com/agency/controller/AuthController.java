package com.agency.controller;

import com.agency.auth.AdminInitializerDto;
import com.agency.authentication.AuthenticationService;
import com.agency.auth.AuthenticationRequest;
import com.agency.auth.AuthenticationResponse;
import com.agency.auth.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authService;

    @PostMapping("/admini-initializer")
    public ResponseEntity<AuthenticationResponse> adminInitialize(@RequestBody AdminInitializerDto request){
        return ResponseEntity.ok(authService.adminInitialize(request));
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
