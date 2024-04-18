package com.agency.dto.contractor;

import com.agency.dto.AddressDto;

import java.time.LocalDate;

public record ContractorCreateRequest(
        String name,
        String lastName,
        String pesel,
        LocalDate birthDate,
        AddressDto addressDto,
        String phone,
        String contractorDescribe
) {
}
