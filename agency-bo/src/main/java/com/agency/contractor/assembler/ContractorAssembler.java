package com.agency.contractor.assembler;

import com.agency.contractmanagement.contractwork.assembler.ContractAssembler;
import com.agency.contractor.model.Contractor;
import com.agency.dto.contractor.ContractorAssignDto;
import com.agency.dto.contractor.ContractorCreateRequest;
import com.agency.dto.contractor.ContractorDto;
import com.agency.dto.contractor.ContractorShortInfoDto;
import com.agency.contractmanagement.project.model.Project;
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
                contractor.getContracts().isEmpty()
                        ? new ArrayList<>()
                        : contractor.getContracts().stream().map(ContractAssembler::toContractWorkDto).toList()
        );
    }

    public static ContractorShortInfoDto toShortContractorDto(Contractor contractor) {
        return new ContractorShortInfoDto(
                contractor.getPublicId().toString(),
                contractor.getFirstName(),
                contractor.getLastName(),
                AddressAssembler.toDto(contractor.getAddress()),
                contractor.getPhone(),
                contractor.getEmail(),
                contractor.getContracts().stream()
                        .map(ContractAssembler::toContractShortDto)
                        .toList()
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
