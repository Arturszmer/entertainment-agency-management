package com.agency.service;

import com.agency.auth.ChangePasswordRequest;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.Principal;

public interface UserService {

    void changePassword(ChangePasswordRequest request, Principal principal) throws UserPrincipalNotFoundException;

    void blockUser(String usernameOrEmail);

    void unblockUser(String usernameOrEmail);
}
