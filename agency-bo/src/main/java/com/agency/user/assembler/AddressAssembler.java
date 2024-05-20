package com.agency.user.assembler;

import com.agency.dto.AddressDto;
import com.agency.user.model.Address;

public class AddressAssembler {

    public static AddressDto toDto(Address userAddress){
        return userAddress == null
                ? null
                : new AddressDto(
                userAddress.getCity(),
                userAddress.getStreet(),
                userAddress.getZipCode(),
                userAddress.getHouseNumber(),
                userAddress.getApartmentNumber()
        );
    }

    public static Address toEntity(AddressDto userAddressDto){
        return userAddressDto == null
                ? null
                : new Address(
                userAddressDto.city(),
                userAddressDto.street(),
                userAddressDto.zipCode(),
                userAddressDto.houseNumber(),
                userAddressDto.apartmentNumber()
        );
    }
}
