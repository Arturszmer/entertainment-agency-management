package com.agency.authentication;

import com.agency.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import static com.agency.exception.UserExceptionResult.USER_NOT_FOUND;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserProfileRepository repository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findUserProfileByUsername(username)
                .orElseGet(() -> repository.findUserProfileByEmail(username)
                .orElseThrow(() -> new RuntimeException(String.format(USER_NOT_FOUND.getMessage(), username))));
    }
}
