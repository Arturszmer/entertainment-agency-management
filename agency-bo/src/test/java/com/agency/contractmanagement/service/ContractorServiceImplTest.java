package com.agency.contractmanagement.service;

import com.agency.contractmanagement.model.ContractWorkBuilder;
import com.agency.contractmanagement.model.ContractorBuilder;
import com.agency.contractmanagement.model.ContractWork;
import com.agency.contractor.model.Contractor;
import com.agency.contractor.repository.ContractorRepository;
import com.agency.contractor.service.ContractorServiceImpl;
import com.agency.dto.contractor.ContractorCreateRequest;
import com.agency.exception.AgencyException;
import com.agency.service.ContractorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.agency.contractmanagement.model.ContractorModel.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ContractorServiceImplTest {

    private ContractorService service;

    private final ContractorRepository repository = mock(ContractorRepository.class);

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
        assertThrows(AgencyException.class, () -> service.add(contractorCreateRequest));

    }

    //TODO: add when create contracts for Contractors will be implemented
    @Test
    public void should_throw_an_exception_when_contractor_with_pesel_has_contracts_will_be_removed() {
        // given
        UUID publicId = UUID.randomUUID();
        ContractWork contractWork = ContractWorkBuilder.aContractWorkBuilder()
                .build();
        Contractor contractor = ContractorBuilder.aContractorBuilder().withPublicId(publicId)
                .withContracts(List.of(contractWork)).build();

        when(repository.findContractorByPublicId(publicId)).thenReturn(Optional.of(contractor));

        // then
        assertThrows(AgencyException.class, () -> service.delete(publicId.toString()));
    }

}
