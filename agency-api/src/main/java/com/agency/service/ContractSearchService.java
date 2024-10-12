package com.agency.service;

import com.agency.dto.contractwork.ContractWorkSearchRequest;
import com.agency.dto.contractwork.ContractWorkSearchResultDto;
import org.springframework.data.domain.Page;

public interface ContractSearchService {

    Page<ContractWorkSearchResultDto> findAllContracts(ContractWorkSearchRequest contractWorkSearchRequest);
}
