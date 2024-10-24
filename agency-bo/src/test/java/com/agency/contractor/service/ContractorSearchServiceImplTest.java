package com.agency.contractor.service;

import com.agency.contractor.assembler.ContractorAssembler;
import com.agency.contractor.model.Contractor;
import com.agency.contractor.repository.ContractorRepository;
import com.agency.dto.contractor.ContractorSearchRequest;
import com.agency.dto.contractor.ContractorShortInfoDto;
import com.agency.project.repository.ProjectRepository;
import com.agency.user.model.Address;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ContractorSearchServiceImplTest {

    private ContractorSearchServiceImpl service;
    private final ContractorRepository contractorRepository = mock(ContractorRepository.class);
    private final ProjectRepository projectRepository = mock(ProjectRepository.class);

    @BeforeEach
    void setUp() {
        service = new ContractorSearchServiceImpl(contractorRepository, projectRepository);
    }

    @Test
    void should_return_default_sorted_by_lastName() {
        // given
        ContractorSearchRequest contractorSearchRequest = getContractorSearchRequest();

        List<Contractor> contractors = new ArrayList<>();
        contractors.add(new Contractor("Jane", "Smith", "10987654321", LocalDate.of(1985, 5, 15), new Address(), "0987654321", "jane.smith@example.com", "TypeB"));
        contractors.add(new Contractor("John", "Doe", "12345678901", LocalDate.of(1990, 1, 1), new Address(), "1234567890", "john.doe@example.com", "TypeA"));

        Page<Contractor> contractorsPage = new PageImpl<>(contractors);
        ContractorSearchFilter filter = ContractorSearchFilter.of(contractorSearchRequest);

        when(contractorRepository.findAll(filter.getPredicate(), filter.getPageable())).thenReturn(contractorsPage);

        // when

        Page<ContractorShortInfoDto> result = service.getContractorsShortInfo(contractorSearchRequest);
        // then

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        ArgumentCaptor<Predicate> predicateCaptor = ArgumentCaptor.forClass(Predicate.class);
        verify(contractorRepository).findAll(predicateCaptor.capture(), pageableCaptor.capture());
        Pageable pageable = pageableCaptor.getValue();

        assertEquals(Sort.by(Sort.Direction.ASC, "lastName"), pageable.getSort());
        assertEquals(0, pageable.getPageNumber());
        assertEquals(10, pageable.getPageSize());

        List<ContractorShortInfoDto> expectedDtos = contractors.stream()
                .map(ContractorAssembler::toShortContractorDto)
                .toList();
        assertEquals(expectedDtos, result.getContent());

    }

    private ContractorSearchRequest getContractorSearchRequest() {
        return new ContractorSearchRequest(
                0, 10, null, null, null, null, null, null
        );
    }
}
