package com.agency.contractmanagement.service;

import com.agency.contractmanagement.assembler.ContractorAssembler;
import com.agency.contractmanagement.repository.ContractorRepository;
import com.agency.dto.contractor.ContractorDto;
import com.agency.dto.contractor.ShortContractorDto;
import com.agency.exception.AgencyErrorResult;
import com.agency.exception.AgencyException;
import com.agency.service.ContractorSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContractorSearchServiceImpl implements ContractorSearchService {

    private final ContractorRepository repository;

    @Override
    public ContractorDto getContractorFullInfo(String publicId) {
        return ContractorAssembler.toDto(repository.findContractorByPublicId(UUID.fromString(publicId))
                .orElseThrow(() -> new AgencyException(AgencyErrorResult.CONTRACTOR_DOES_NOT_EXISTS, publicId)));
    }

    @Override
    public Page<ShortContractorDto> getContractorsShortInfo(int page, int size) {
        Pageable pagesRequest = PageRequest.of(page, size);
        List<ShortContractorDto> contractorDtos = repository.findAll().stream().map(ContractorAssembler::toShortContractorDto).toList();
        int start = (int) pagesRequest.getOffset();
        int end = Math.min(start + pagesRequest.getPageSize(), contractorDtos.size());

        List<ShortContractorDto> pageContent = contractorDtos.subList(start, end);
        return new PageImpl<>(pageContent, pagesRequest, contractorDtos.size());
    }
}
