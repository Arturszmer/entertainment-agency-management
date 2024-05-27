package com.agency.user.service;

import com.agency.dto.userprofile.UserProfileDetailsDto;
import com.agency.search.SortableConfig;
import com.agency.service.UserSearchService;
import com.agency.user.assembler.UserAssembler;
import com.agency.user.model.UserProfile;
import com.agency.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSearchServiceImpl implements UserSearchService {

    private final UserProfileRepository repository;
    private final SortableConfig sortableConfig;

    @Override
    public Page<UserProfileDetailsDto> getAllUsers(int page, int size, String sort, String order) {
        Pageable pageRequest = sortableConfig.getPageable(page, size, sort, order);
        Page<UserProfile> usersPage = repository.findAll(pageRequest);
        List<UserProfileDetailsDto> userDetailsDto = usersPage.getContent().stream()
                .map(UserAssembler::toUserProfileDetailsDto)
                .toList();
        return new PageImpl<>(userDetailsDto, pageRequest, usersPage.getTotalElements());
    }
}
