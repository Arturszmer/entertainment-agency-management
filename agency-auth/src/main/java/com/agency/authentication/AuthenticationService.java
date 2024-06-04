package com.agency.authentication;

import com.agency.auth.*;
import com.agency.config.JwtService;
import com.agency.dto.userprofile.UserProfileDetailsDto;
import com.agency.exception.AgencyException;
import com.agency.user.assembler.UserAssembler;
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

import static com.agency.exception.UserExceptionResult.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserProfileRepository userProfileRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private static final String STARTED_PASSWORD = "Agency2024";
    private static final String ADMIN_STATEMENT_EXC = "Admin user exists, you cannot create another one";

    @Transactional
    public AuthenticationResponse adminInitialize(AdminInitializerDto request) {
        if(userProfileRepository.findByRoleType(RoleType.ADMIN).isPresent()){
            throw new IllegalStateException(ADMIN_STATEMENT_EXC);
        }
        return createUser(RegistrationRequest.fromAdminInitializer(request));
    }

    public UserProfileDetailsDto register(CreateUserRequest request){
        if(request.roleType() == RoleType.ADMIN){
            throw new IllegalStateException(ADMIN_STATEMENT_EXC);
        }

        return createUser(request);
    }

    public void resetPassword(String username) {
        UserProfile userProfile = userProfileRepository.findUserProfileByUsername(username)
                .orElseThrow(() -> new AgencyException(USER_NOT_FOUND, username));
        userProfile.setNewPassword(passwordEncoder.encode(STARTED_PASSWORD));
        userProfileRepository.save(userProfile);
        log.info("Password for user {} has been reset successfully.", username);
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

    private UserProfileDetailsDto createUser(CreateUserRequest request){
//        String tempPassword = UUID.randomUUID().toString().substring(0, 13);
        UserProfile userProfile = UserProfile.create(request, passwordEncoder.encode(STARTED_PASSWORD));

        UserProfile savedUser = userProfileRepository.save(userProfile);
        return UserAssembler.toUserProfileDetailsDto(savedUser);
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

    public Boolean isInitialized() {
        return !userProfileRepository.findAll().isEmpty();
    }
}
