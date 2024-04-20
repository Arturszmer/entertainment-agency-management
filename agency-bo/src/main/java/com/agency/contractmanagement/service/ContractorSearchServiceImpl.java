package com.agency.contractmanagement.service;

import com.agency.contractmanagement.assembler.ContractorAssembler;
import com.agency.contractmanagement.repository.ContractorRepository;
import com.agency.dto.contractor.ContractorDto;
import com.agency.exception.AgencyErrorResult;
import com.agency.exception.AgencyException;
import com.agency.service.ContractorSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public Page<ContractorDto> getContractorsShortInfo(int page, int size) {
        return null;
    }

    @Override
    public List<ContractorDto> getContractorsByEvent(String eventId) {
        return null;
    }
}
