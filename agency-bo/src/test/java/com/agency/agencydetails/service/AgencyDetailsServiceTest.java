package com.agency.agencydetails.service;

import com.agency.agencydetails.repository.AgencyDetailsRepository;
import com.agency.dto.agencydetails.AgencyDetailsDto;
import com.agency.exception.AgencyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.agency.agencydetails.model.AgencyDetailsModel.getAgencyDetails;
import static com.agency.agencydetails.model.AgencyDetailsModel.getAgencyDetailsDto;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AgencyDetailsServiceTest {

    private AgencyDetailsService service;
    private final AgencyDetailsRepository repository = mock(AgencyDetailsRepository.class);

    @BeforeEach
    void setup(){
        service = new AgencyDetailsService(repository);
    }

    @Test
    public void should_throw_an_exception_when_agency_exists() {
        // given
        AgencyDetailsDto agencyDetailsDto = getAgencyDetailsDto();
        when(repository.findAll()).thenReturn(List.of(getAgencyDetails()));

        // then
        assertThrows(AgencyException.class, () -> service.initializeAgency(agencyDetailsDto));

    }

    @Test
    public void should_throw_an_exception_when_agency_is_not_exists_but_we_want_to_update() {
        // given
        AgencyDetailsDto agencyDetailsDto = getAgencyDetailsDto();
        when(repository.findAll()).thenReturn(List.of());

        // then
        assertThrows(AgencyException.class, () -> service.updateAgencyDetails(agencyDetailsDto));

    }

}
