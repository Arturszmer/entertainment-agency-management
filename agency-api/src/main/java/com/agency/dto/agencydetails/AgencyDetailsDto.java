package com.agency.dto.agencydetails;

import com.agency.dto.address.AddressDto;
import org.springframework.lang.NonNull;

public record AgencyDetailsDto(
        @NonNull String name,
        @NonNull String nip,
        String regon,
        String pesel,
        String krsNumber,
        @NonNull AddressDto addressDto
) {
}
