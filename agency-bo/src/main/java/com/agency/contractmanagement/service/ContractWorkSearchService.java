package com.agency.contractmanagement.service;

import com.agency.contractmanagement.assembler.ContractAssembler;
import com.agency.contractmanagement.repository.ContractWorkRepository;
import com.agency.dto.contractwork.ContractWorkSearchRequest;
import com.agency.dto.contractwork.ContractWorkSearchResultDto;
import com.agency.service.ContractSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContractWorkSearchService implements ContractSearchService {

    private final ContractWorkRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Page<ContractWorkSearchResultDto> findAllContracts(ContractWorkSearchRequest contractWorkSearchRequest) {
        ContractWorkSearchFilter contractWorkSearchFilter = ContractWorkSearchFilter.of(contractWorkSearchRequest);
        return repository.findAll(contractWorkSearchFilter.getPredicate(), contractWorkSearchFilter.getPageable())
                .map(ContractAssembler::toContractWorkSearchResultDto);
    }
}
