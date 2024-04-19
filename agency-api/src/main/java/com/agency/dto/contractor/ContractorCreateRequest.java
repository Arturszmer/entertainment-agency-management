package com.agency.dto.contractor;

import com.agency.dto.AddressDto;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

public record ContractorCreateRequest(
        @NonNull String name,
        @NonNull String lastName,
        @NonNull String pesel,
        @NonNull LocalDate birthDate,
        @NonNull AddressDto addressDto,
        String phone,
        String contractorDescribe
) {
}
