package com.agency.dto.contractor;

import com.agency.dto.AddressDto;
import com.agency.dto.contract.ContractShortDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ContractorDto(
        UUID publicId,
        String name,
        String lastName,
        String pesel,
        LocalDate birthDate,
        AddressDto addressDto,
        String phone,
        String email,
        String contractorDescription,
        List<ContractShortDto> contracts
) {
}
