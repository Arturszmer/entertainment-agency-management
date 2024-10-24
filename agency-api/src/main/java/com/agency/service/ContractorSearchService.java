package com.agency.service;

import com.agency.dto.contractor.ContractorAssignDto;
import com.agency.dto.contractor.ContractorDto;
import com.agency.dto.contractor.ContractorSearchRequest;
import com.agency.dto.contractor.ContractorShortInfoDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContractorSearchService {

    ContractorDto getContractorFullInfo(String publicId);
    Page<ContractorShortInfoDto> getContractorsShortInfo(ContractorSearchRequest searchRequest);

    List<ContractorAssignDto> getContractorsForAssign(String projectNumber);
}
