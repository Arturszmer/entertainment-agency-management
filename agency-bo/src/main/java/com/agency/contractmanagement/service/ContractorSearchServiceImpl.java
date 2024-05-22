package com.agency.contractmanagement.service;

import com.agency.contractmanagement.assembler.ContractorAssembler;
import com.agency.contractmanagement.model.contractor.Contractor;
import com.agency.contractmanagement.repository.ContractorRepository;
import com.agency.dto.contractor.ContractorDto;
import com.agency.dto.contractor.ShortContractorDto;
import com.agency.exception.AgencyErrorResult;
import com.agency.exception.AgencyException;
import com.agency.service.ContractorSearchService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.*;
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
    public Page<ShortContractorDto> getContractorsShortInfo(int page, int size, String sort, String order) {
        Pageable pagesRequest = getPageable(page, size, sort, order);
        Page<Contractor> contractorsPage = repository.findAll(pagesRequest);
        List<ShortContractorDto> contractorDtos = contractorsPage.getContent()
                .stream().map(ContractorAssembler::toShortContractorDto).toList();
        return new PageImpl<>(contractorDtos, pagesRequest, contractorsPage.getTotalElements());
    }

    private static @NotNull Pageable getPageable(int page, int size, String sort, String order) {
        Sort.Direction direction = Sort.Direction.fromString((order != null) ? order : "asc");
        String sortField = (sort != null) ? sort : "lastName";

        return PageRequest.of(page, size, Sort.by(direction, sortField));
    }
}
