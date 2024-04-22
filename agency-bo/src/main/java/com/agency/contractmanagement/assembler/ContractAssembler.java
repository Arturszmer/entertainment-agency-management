package com.agency.contractmanagement.assembler;

import com.agency.contractmanagement.model.contract.ContractWork;
import com.agency.dto.contractwork.ContractShortDto;

public class ContractAssembler {

    public static ContractShortDto toContractShortDto(ContractWork contract){
        return new ContractShortDto(contract.getContractNumber(), contract.getStartDate(), contract.getEndDate());
    }
}
