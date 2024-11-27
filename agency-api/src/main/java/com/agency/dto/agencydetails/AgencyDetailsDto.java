package com.agency.dto.agencydetails;

import com.agency.dto.address.AddressDto;
import org.springframework.lang.NonNull;

public record AgencyDetailsDto(
        @NonNull String agencyName,
        @NonNull String nip,
        String regon,
        String pesel,
        String firstName,
        String lastName,
        String krsNumber,
        @NonNull AddressDto addressDto
) {
}
