package com.agency.service;

import com.agency.dto.organizer.OrganizerDto;
import com.agency.dto.organizer.OrganizerSearchResultDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrganizerSearchService {

    Page<OrganizerSearchResultDto> findAll(int page, int size, String sort, String order);
    OrganizerDto findByPublicId(String publicId);
    List<OrganizerDto> findByUsername();
}
