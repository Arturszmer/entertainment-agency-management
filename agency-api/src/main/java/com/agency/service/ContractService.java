package com.agency.service;

import com.agency.dto.contractwork.ContractWorkCreateDto;
import com.agency.dto.contractwork.ContractWorkDto;

public interface ContractService {

    ContractWorkDto createContractOfWork(ContractWorkCreateDto createDto);
    void deleteContractOfWork(String publicId);

    void confirmContract(String publicId);

    void cancelConfirmation(String publicId);

}
