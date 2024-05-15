package com.agency.service;

import com.agency.dto.organizer.OrganizerDto;

import java.util.List;

public interface OrganizerSearchService {

    List<OrganizerDto> findAll();
    OrganizerDto findByPublicId(String publicId);
}
