package com.agency.service;

import com.agency.dto.organizer.OrganizerCreate;
import com.agency.dto.organizer.OrganizerDto;

public interface OrganizerService {

    OrganizerDto add(OrganizerCreate organizerDto);
}
