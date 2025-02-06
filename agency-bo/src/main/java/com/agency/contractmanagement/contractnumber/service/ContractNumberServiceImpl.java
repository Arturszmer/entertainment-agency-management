package com.agency.contractmanagement.contractnumber.service;

import com.agency.contractmanagement.contractnumber.model.ContractNumber;
import com.agency.contractmanagement.contractnumber.model.ContractNumberStatus;
import com.agency.contractmanagement.contractnumber.repository.ContractNumberRepository;
import com.agency.dict.contract.ContractType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContractNumberServiceImpl implements ContractNumberService {

    private final ContractNumberRepository contractNumberRepository;
    private final ContractGenerator contractWorkGenerator;

    @Override
    public String createContractNumber(LocalDate signDate, ContractType contractType) {
        ContractNumber contractNumber = contractWorkGenerator.generateContractNumber(signDate, contractType);
        return contractNumberRepository.save(contractNumber).getCompleteNumber();
    }

    @Override
    public Optional<String> updateContractNumberStatus(String completeNumber, ContractNumberStatus contractNumberStatus) {
        Optional<ContractNumber> contractNumberOpt = contractNumberRepository.findContractNumberByCompleteNumber(completeNumber);
        if (contractNumberOpt.isPresent()) {
            ContractNumber contractNumber = contractNumberOpt.get();
            contractNumber.updateByContractNumberStatus(contractNumberStatus);
            return Optional.ofNullable(contractNumberRepository.save(contractNumber).getCompleteNumber());
        }
        return Optional.empty();
    }

    @Override
    public void deleteContractNumber(String completeNumber) {
        Optional<ContractNumber> contractNumberOpt = contractNumberRepository.findContractNumberByCompleteNumber(completeNumber);
        if (contractNumberOpt.isPresent()) {
            ContractNumber contractNumber = contractNumberOpt.get();
            if (ContractNumberStatus.DRAFT == contractNumber.getContractNumberStatus()) {
                contractNumberRepository.delete(contractNumber);
            }
        }
    }


}
