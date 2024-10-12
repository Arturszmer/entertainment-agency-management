package com.agency.organizer.service;

import com.agency.dto.organizer.OrganizerDto;
import com.agency.dto.organizer.OrganizerSearchResultDto;
import com.agency.organizer.assembler.OrganizerAssembler;
import com.agency.organizer.model.Organizer;
import com.agency.organizer.repository.OrganizerRepository;
import com.agency.service.OrganizerSearchService;
import com.agency.user.helper.SecurityContextUsers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizerSearchServiceImpl implements OrganizerSearchService {

    private final OrganizerRepository repository;

    @Override
    public Page<OrganizerSearchResultDto> findAll(int page, int size, String sort, String order) {
        String mappedSort = mapAddressSortColumn(sort);
        Pageable pagesRequest = OrganizerSearchFilter.forPageable(page, size, mappedSort, order).getPageable();
        Page<Organizer> organizersPage = repository.findAll(pagesRequest);
        List<OrganizerSearchResultDto> organizersDto = organizersPage.getContent().stream().map(OrganizerAssembler::mapToSearchResult).toList();

        return new PageImpl<>(organizersDto, pagesRequest, organizersPage.getTotalElements());
    }

    @Override
    public OrganizerDto findByPublicId(String publicId) {
        return OrganizerAssembler.toDto(repository.findOrganizerByPublicId(UUID.fromString(publicId))
                .orElseThrow(() -> new IllegalArgumentException("Organizer with public id " + publicId + " not found")));
    }

    @Override
    public List<OrganizerDto> findByUsername() {
        return repository.findAllByUsername(SecurityContextUsers.getUsernameFromAuthenticatedUser()).stream()
                .map(OrganizerAssembler::toDto)
                .toList();
    }

    @Override
    public Page<OrganizerSearchResultDto> findAllByOrganizerName(int page, int size, String sort, String order, String organizerName) {
        String mappedSort = mapAddressSortColumn(sort);
        Pageable pagesRequest = OrganizerSearchFilter.forPageable(page, size, mappedSort, order).getPageable();
        Page<Organizer> organizersPage = repository.findAllByOrganizerName(organizerName, pagesRequest);
        List<OrganizerSearchResultDto> organizersDto = organizersPage.stream().map(OrganizerAssembler::mapToSearchResult).toList();

        return new PageImpl<>(organizersDto, pagesRequest, organizersPage.getTotalElements());
    }

    private String mapAddressSortColumn(String sort) {
        if(sort != null){
            return switch (sort) {
                case "voivodeship" -> "address.voivodeship";
                case "city" -> "address.city";
                default -> sort;
            };
        } else {
          return null;
        }
    }

}
