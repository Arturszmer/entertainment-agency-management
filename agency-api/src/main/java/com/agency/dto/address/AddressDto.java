package com.agency.dto.address;

public record AddressDto(
        String city,
        String street,
        String voivodeship,
        String zipCode,
        String houseNumber,
        String apartmentNumber
) {
}
