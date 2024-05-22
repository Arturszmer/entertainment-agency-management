package com.agency.model;

import com.agency.dto.address.AddressDto;

public class AddressModel {

    public static AddressDto createDefaultAddressDto() {
        return new AddressDto(
                "Warszawa", "Zwycięstwa", "mazowieckie", "00-001", "1", "1"
        );
    }
}
