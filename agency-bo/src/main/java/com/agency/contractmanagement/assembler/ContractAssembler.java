package com.agency.contractmanagement.assembler;

import com.agency.contractmanagement.model.contract.Contract;
import com.agency.dto.contract.ContractShortDto;

public class ContractAssembler {

    public static ContractShortDto toContractShortDto(Contract contract){
        return new ContractShortDto(contract.getContractNumber(), contract.getStartDate(), contract.getEndDate());
    }
}
