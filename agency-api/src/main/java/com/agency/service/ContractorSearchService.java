package com.agency.service;

import com.agency.dto.contractor.ContractorDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContractorSearchService {

    ContractorDto getContractorFullInfo(String publicId);
    Page<ContractorDto> getContractorsShortInfo(int page, int size);
    List<ContractorDto> getContractorsByEvent(String eventId); // TODO implement when event logic will be created
}
