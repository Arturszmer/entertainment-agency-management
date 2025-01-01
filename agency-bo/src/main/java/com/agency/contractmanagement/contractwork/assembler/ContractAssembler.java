package com.agency.contractmanagement.contractwork.assembler;

import com.agency.contractmanagement.contractwork.model.ContractWork;
import com.agency.contractor.model.Contractor;
import com.agency.dto.contractwork.ContractShortDto;
import com.agency.dto.contractwork.ContractWorkDto;
import com.agency.dto.contractwork.ContractWorkSearchResultDto;

public class ContractAssembler {

    public static ContractShortDto toContractShortDto(ContractWork contract){
        return new ContractShortDto(
                contract.getPublicId().toString(),
                contract.getContractNumber(),
                contract.getStartDate(),
                contract.getEndDate(),
                contract.getFilename());
    }

    public static ContractWorkDto toContractWorkDto(ContractWork contract) {
        return new ContractWorkDto(
                contract.getPublicId().toString(),
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

    public static ContractWorkSearchResultDto toContractWorkSearchResultDto(ContractWork contractWork) {
        Contractor contractor = contractWork.getContractor();
        return new ContractWorkSearchResultDto(
                toContractWorkDto(contractWork), contractor.getFirstName(),contractor.getLastName(), contractWork.getProjectNumber()
        );
    }
}
