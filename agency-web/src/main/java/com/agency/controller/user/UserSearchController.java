package com.agency.controller.user;

import com.agency.dto.userprofile.UserProfileDetailsDto;
import com.agency.service.UserSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserSearchController {

    private final UserSearchService service;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER_MANAGEMENT')")
    public ResponseEntity<Page<UserProfileDetailsDto>> getUserProfileDetails(@RequestParam("page") int page,
                                                               @RequestParam("size") int size,
                                                               @RequestParam(required = false, value = "sort") String sort,
                                                               @RequestParam(required = false, value = "order") String order) {
        return ResponseEntity.ok(service.getAllUsers(page, size, sort, order));
    }
}
