package com.agency.contractmanagement.service;

import com.agency.contractmanagement.model.contractor.Contractor;
import com.agency.contractmanagement.repository.ContractorRepository;
import com.agency.dto.contractor.ContractorCreateRequest;
import com.agency.service.ContractorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.agency.contractmanagement.model.ContractorModel.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ContractorServiceImplTest {

    private ContractorService service;

    private final ContractorRepository repository = mock(ContractorRepository.class);
    private final static String PUBLIC_ID_OF_EXISTING_CONTRACTOR = "fb75951a-fe54-11ee-92c8-0242ac120002";

    @BeforeEach
    void setup(){
        service = new ContractorServiceImpl(repository);
    }

    @Test
    public void should_throw_an_exception_when_contractor_with_pesel_exists() {
        // given
        ContractorCreateRequest contractorCreateRequest = contractorCreateRequestBuild();
        Contractor contractor = contractorFromRequest(contractorCreateRequest);
        when(repository.findByPesel(PESEL)).thenReturn(Optional.of(contractor));
        when(repository.save(contractor)).thenReturn(contractor);

        // then
        assertThrows(IllegalStateException.class, () -> service.add(contractorCreateRequest));

    }

    //TODO: add when create contracts for Contractors will be implemented
    @Test
    public void should_throw_an_exception_when_contractor_with_pesel_has_contracts() {
        // given
        // when
        // then
    }

}