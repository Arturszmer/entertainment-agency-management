package com.agency.model;

import com.agency.dto.AddressDto;

public class AddressModel {

    public static AddressDto createDefaultAddressDto() {
        return new AddressDto(
                "Warszawa", "Zwycięstwa", "00-001", "1", "1"
        );
    }
}
