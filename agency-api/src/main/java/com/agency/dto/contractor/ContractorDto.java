package com.agency.dto.contractor;

import com.agency.dto.address.AddressDto;
import com.agency.dto.contractwork.ContractWorkDto;

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
        List<ContractWorkDto> contracts
) {
}
