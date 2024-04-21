package com.agency.controller.agency;

import com.agency.BaseIntegrationTestSettings;
import com.agency.agencydetails.model.AgencyDetails;
import com.agency.agencydetails.repository.AgencyDetailsRepository;
import com.agency.dto.AddressDto;
import com.agency.dto.agencydetails.AgencyDetailsDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@WithMockUser(roles = "ADMIN")
class AgencyDetailsControllerTest extends BaseIntegrationTestSettings {

    @Autowired
    AgencyDetailsRepository repository;
    private final static String urlPath = "/agency-details";

    @Test
    public void should_initialize_new_agency() throws Exception {
        // given
        AgencyDetailsDto agencyDetailsDto = getAgencyDetails("Agency");
        String body = mapper.writeValueAsString(agencyDetailsDto);

        // when
        postRequest(urlPath, body);
        Optional<AgencyDetails> agencyDetailsOptional = repository.findAll().stream().findFirst();

        // then
        assertTrue(agencyDetailsOptional.isPresent());

    }

    @Test
    @Sql(scripts = "/sql-init/agency-details-init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void should_update_existing_agency() throws Exception {
        // given
        String editetAgencyName = "Entertainment Agency";
        AgencyDetailsDto agencyDetailsDto = getAgencyDetails(editetAgencyName);
        String body = mapper.writeValueAsString(agencyDetailsDto);

        // when
        putRequest(urlPath, body);
        Optional<AgencyDetails> agencyDetailsOptional = repository.findAll().stream().findFirst();

        // then
        assertTrue(agencyDetailsOptional.isPresent());
        assertEquals(editetAgencyName, agencyDetailsOptional.get().getName());

    }

    private AgencyDetailsDto getAgencyDetails(String name) {
        return new AgencyDetailsDto(
                name,
                "6611235511",
                "123456789",
                null,
                null,
                new AddressDto(
                        "Warsza", "Zwycięstwa", "00-001", "1", "1"
                )
        );
    }
}
