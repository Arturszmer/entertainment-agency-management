package com.agency.contractmanagement.assembler;

import com.agency.contractmanagement.model.contractor.Contractor;
import com.agency.dto.contractor.ContractorAssignDto;
import com.agency.dto.contractor.ContractorCreateRequest;
import com.agency.dto.contractor.ContractorDto;
import com.agency.dto.contractor.ShortContractorDto;
import com.agency.project.model.Project;
import com.agency.user.assembler.AddressAssembler;

import java.util.ArrayList;

public class ContractorAssembler {

    public static Contractor fromCreationRequest(ContractorCreateRequest request) {
        return new Contractor(request.firstName(), request.lastName(), request.pesel(),
                request.birthDate(), AddressAssembler.toEntity(request.addressDto()), request.phone(), request.email(), request.contractorDescription());
    }

    public static ContractorDto toDto(Contractor contractor) {
        return new ContractorDto(
                contractor.getPublicId(),
                contractor.getFirstName(),
                contractor.getLastName(),
                contractor.getPesel(),
                contractor.getBirthDate(),
                AddressAssembler.toDto(contractor.getAddress()),
                contractor.getPhone(),
                contractor.getEmail(),
                contractor.getContractorDescription(),
                contractor.getContract().isEmpty()
                        ? new ArrayList<>()
                        : contractor.getContract().stream().map(ContractAssembler::toContractShortDto).toList()
        );
    }

    public static ShortContractorDto toShortContractorDto(Contractor contractor) {
        return new ShortContractorDto(
                contractor.getPublicId().toString(),
                contractor.getFirstName(),
                contractor.getLastName(),
                AddressAssembler.toDto(contractor.getAddress()),
                contractor.getPhone(),
                contractor.getEmail()
        );
    }

    public static ContractorAssignDto toAssignContractors(Contractor contractor, Project project) {
        return new ContractorAssignDto(
                contractor.getPublicId().toString(),
                contractor.getFirstName() + " " + contractor.getLastName(),
                project.getContractors().contains(contractor)
        );
    }
}
