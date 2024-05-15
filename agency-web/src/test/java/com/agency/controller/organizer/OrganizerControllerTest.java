package com.agency.controller.organizer;

import com.agency.BaseIntegrationTestSettings;
import com.agency.dto.organizer.OrganizerCreate;
import com.agency.dto.organizer.OrganizerDto;
import com.agency.organizer.model.Organizer;
import com.agency.organizer.repository.OrganizerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static com.agency.model.OrganizerModel.createDefaultOrganizer;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@WithMockUser(authorities = "ORGANIZER_MANAGEMENT")
class OrganizerControllerTest extends BaseIntegrationTestSettings {

    @Autowired
    private OrganizerRepository repository;
    private final static String urlPath = "/organizer";

    @Test
    void should_add_new_organizer() throws Exception {

        // given
        OrganizerCreate organizerCreate = createDefaultOrganizer();
        String body = mapper.writeValueAsString(organizerCreate);

        // when
        MvcResult mvcResult = postRequest(urlPath, body).andReturn();
        OrganizerDto organizerDto = mapper.readValue(mvcResult.getResponse().getContentAsString(), OrganizerDto.class);

        // then
        Optional<Organizer> organizer = repository.findOrganizerByPublicId(UUID.fromString(organizerDto.publicId()));
        assertTrue(organizer.isPresent());
        assertNotNull(organizer.get().getUsername());
        assertEquals(organizerCreate.email(), organizer.get().getEmail());
    }
}