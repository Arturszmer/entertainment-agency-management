package com.agency.contractmanagement.assembler;

import com.agency.contractmanagement.model.contract.ContractWork;
import com.agency.dto.contractwork.ContractShortDto;
import com.agency.dto.contractwork.ContractWorkDto;

public class ContractAssembler {

    public static ContractShortDto toContractShortDto(ContractWork contract){
        return new ContractShortDto(contract.getContractNumber(), contract.getStartDate(), contract.getEndDate());
    }

    public static ContractWorkDto toContractWorkDto(ContractWork contract) {
        return new ContractWorkDto(
                contract.getContractNumber(),
                contract.getSignDate(),
                contract.getStartDate(),
                contract.getEndDate(),
                contract.getContractSubject(),
                contract.getSalary(),
                contract.getAdditionalInformation(),
                contract.getContractType(),
                contract.isWithCopyrights()
        );
    }
}
