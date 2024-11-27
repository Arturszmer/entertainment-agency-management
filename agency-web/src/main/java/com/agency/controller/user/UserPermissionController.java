package com.agency.controller.user;

import com.agency.dict.userProfile.Permission;
import com.agency.service.UserPermissionManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/v1/user/permission")
@PreAuthorize("hasAuthority('USER_MANAGEMENT')")
@Slf4j
@RequiredArgsConstructor
public class UserPermissionController {

    private final UserPermissionManagerService userPermissionManagerService;

    @PutMapping("/{username}")
    ResponseEntity<Void> updatePermissions(@PathVariable("username") String username, @RequestBody Set<Permission> permissions) {
        userPermissionManagerService.updateUserPermissions(username, permissions);
        return ResponseEntity.ok().build();
    }
}
