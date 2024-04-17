package com.agency.authentication;

import com.agency.auth.*;
import com.agency.config.JwtService;
import com.agency.user.model.UserProfile;
import com.agency.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserProfileRepository userProfileRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private static final String ADMIN_STATEMENT_EXC = "Admin user exists, you cannot create another one";

    @Transactional
    public AuthenticationResponse adminInitialize(AdminInitializerDto request) {
        if(userProfileRepository.findByRoleType(RoleType.ADMIN).isPresent()){
            throw new IllegalStateException(ADMIN_STATEMENT_EXC);
        }
        return createUser(RegistrationRequest.fromAdminInitializer(request));
    }

    public AuthenticationResponse register(RegistrationRequest request){
        if(request.roleType() == RoleType.ADMIN){
            throw new IllegalStateException(ADMIN_STATEMENT_EXC);
        }

        return createUser(request);
    }

    @NotNull
    private AuthenticationResponse createUser(RegistrationRequest request) {
        UserProfile userProfile = UserProfile.create(
                request.username(), request.email(), passwordEncoder.encode(request.password()), request.roleType()
        );

        UserProfile savedUser = userProfileRepository.save(userProfile);
        String generatedToken = jwtService.generateToken(savedUser);
        return new AuthenticationResponse(generatedToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.usernameOrEmail(), request.password()));

        UserProfile user = findUser(request.usernameOrEmail());
        String generatedToken = jwtService.generateToken(user);

        return new AuthenticationResponse(generatedToken);
    }

    private UserProfile findUser(String user) {
        return userProfileRepository.findUserProfileByUsername(user)
                .orElseGet(() -> userProfileRepository.findUserProfileByEmail(user)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }
}
