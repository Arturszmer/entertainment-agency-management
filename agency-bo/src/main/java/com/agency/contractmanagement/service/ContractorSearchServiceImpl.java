package com.agency.contractmanagement.service;

import com.agency.contractmanagement.assembler.ContractorAssembler;
import com.agency.contractmanagement.model.contractor.Contractor;
import com.agency.contractmanagement.repository.ContractorRepository;
import com.agency.dto.contractor.ContractorDto;
import com.agency.dto.contractor.ShortContractorDto;
import com.agency.exception.AgencyException;
import com.agency.exception.ContractorErrorResult;
import com.agency.search.SortableConfig;
import com.agency.service.ContractorSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContractorSearchServiceImpl implements ContractorSearchService {

    private final ContractorRepository repository;
    private final SortableConfig sortableConfig;

    @Override
    public ContractorDto getContractorFullInfo(String publicId) {
        return ContractorAssembler.toDto(repository.findContractorByPublicId(UUID.fromString(publicId))
                .orElseThrow(() -> new AgencyException(ContractorErrorResult.CONTRACTOR_DOES_NOT_EXISTS, publicId)));
    }

    @Override
    public Page<ShortContractorDto> getContractorsShortInfo(int page, int size, String sort, String order) {
        Pageable pagesRequest = sortableConfig.getPageable(page, size, sort, order);
        Page<Contractor> contractorsPage = repository.findAll(pagesRequest);
        List<ShortContractorDto> contractorDtos = contractorsPage.getContent().stream()
                .map(ContractorAssembler::toShortContractorDto)
                .toList();
        return new PageImpl<>(contractorDtos, pagesRequest, contractorsPage.getTotalElements());
    }
}
