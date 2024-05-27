package com.agency.service;

import com.agency.dto.userprofile.UserProfileDetailsDto;
import org.springframework.data.domain.Page;

public interface UserSearchService {

    Page<UserProfileDetailsDto> getAllUsers(int page, int size, String sort, String order);
}
