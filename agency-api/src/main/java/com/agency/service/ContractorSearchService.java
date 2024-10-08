package com.agency.service;

import com.agency.dto.contractor.ContractorAssignDto;
import com.agency.dto.contractor.ContractorDto;
import com.agency.dto.contractor.ContractorShortInfoDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContractorSearchService {

    ContractorDto getContractorFullInfo(String publicId);
    Page<ContractorShortInfoDto> getContractorsShortInfo(int page, int size, String sort, String order);

    List<ContractorAssignDto> getContractorsForAssign(String projectNumber);
}
