package com.agency.contractmanagement.assembler;

import com.agency.contractmanagement.model.contractor.Contractor;
import com.agency.dto.contractor.ContractorCreateRequest;
import com.agency.dto.contractor.ContractorDto;
import com.agency.user.assembler.UserProfileAssembler;

import java.util.ArrayList;

public class ContractorAssembler {

    public static Contractor fromCreationRequest(ContractorCreateRequest request){
        return new Contractor(request.firstName(), request.lastName(), request.pesel(),
                request.birthDate(), UserProfileAssembler.toEntity(request.addressDto()), request.phone(), request.contractorDescription());
    }

    public static ContractorDto toDto(Contractor contractor) {
        return new ContractorDto(
                contractor.getPublicId(),
                contractor.getFirstName(),
                contractor.getLastName(),
                contractor.getPesel(),
                contractor.getBirthDate(),
                UserProfileAssembler.toDto(contractor.getAddress()),
                contractor.getPhone(),
                contractor.getContractorDescription(),
                contractor.getContract().isEmpty()
                        ? new ArrayList<>()
                        : contractor.getContract().stream().map(ContractAssembler::toContractShortDto).toList()
        );
    }
}
