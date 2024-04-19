package com.agency.service;

import com.agency.dto.contractor.ContractorCreateRequest;
import com.agency.dto.contractor.ContractorDto;

public interface ContractorService {

    ContractorDto add(ContractorCreateRequest request);
    ContractorDto edit(String publicId, ContractorCreateRequest request);
    void delete(String pesel);

}
