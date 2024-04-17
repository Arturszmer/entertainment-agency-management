package com.agency.dto;

public record AddressDto(
        String city,
        String street,
        String zipCode,
        String houseNumber,
        String apartmentNumber
) {
}
