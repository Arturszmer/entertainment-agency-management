package com.agency.contractmanagement.contractnumber.service;


import com.agency.contractmanagement.contractnumber.model.ContractNumberStatus;
import com.agency.dict.contract.ContractType;

import java.time.LocalDate;
import java.util.Optional;

public interface ContractNumberService {

    String createContractNumber(LocalDate signDate, ContractType contractType);
    Optional<String> updateContractNumberStatus(String completeNumber, ContractNumberStatus contractNumberStatus);
    void deleteContractNumber(String completeNumber);
}
