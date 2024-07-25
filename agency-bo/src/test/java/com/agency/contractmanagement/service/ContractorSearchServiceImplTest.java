package com.agency.contractmanagement.service;

import com.agency.contractmanagement.assembler.ContractorAssembler;
import com.agency.contractmanagement.model.contractor.Contractor;
import com.agency.contractmanagement.repository.ContractorRepository;
import com.agency.dto.contractor.ShortContractorDto;
import com.agency.project.repository.ProjectRepository;
import com.agency.search.SortableConfig;
import com.agency.user.model.Address;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ContractorSearchServiceImplTest {

    private ContractorSearchServiceImpl service;
    private final ContractorRepository contractorRepository = mock(ContractorRepository.class);
    private final ProjectRepository projectRepository = mock(ProjectRepository.class);

    @BeforeEach
    void setUp() {
        service = new ContractorSearchServiceImpl(contractorRepository, projectRepository, new SortableConfig());
    }

    @Test
    void should_return_default_sorted_by_lastName() {
        // given
        int page = 0;
        int size = 10;
        String sort = null;
        String order = null;

        List<Contractor> contractors = new ArrayList<>();
        contractors.add(new Contractor("Jane", "Smith", "10987654321", LocalDate.of(1985, 5, 15), new Address(), "0987654321", "jane.smith@example.com", "TypeB"));
        contractors.add(new Contractor("John", "Doe", "12345678901", LocalDate.of(1990, 1, 1), new Address(), "1234567890", "john.doe@example.com", "TypeA"));

        Page<Contractor> contractorsPage = new PageImpl<>(contractors);
        when(contractorRepository.findAll(any(Pageable.class))).thenReturn(contractorsPage);

        // when

        Page<ShortContractorDto> result = service.getContractorsShortInfo(page, size, sort, order);
        // then

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(contractorRepository).findAll(pageableCaptor.capture());
        Pageable pageable = pageableCaptor.getValue();

        assertEquals(Sort.by(Sort.Direction.ASC, "id"), pageable.getSort());
        assertEquals(page, pageable.getPageNumber());
        assertEquals(size, pageable.getPageSize());

        List<ShortContractorDto> expectedDtos = contractors.stream()
                .map(ContractorAssembler::toShortContractorDto)
                .toList();
        assertEquals(expectedDtos, result.getContent());

    }
}
