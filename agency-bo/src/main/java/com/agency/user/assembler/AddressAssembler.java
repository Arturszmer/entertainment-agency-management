package com.agency.user.assembler;

import com.agency.dto.address.AddressDto;
import com.agency.user.model.Address;

public class AddressAssembler {

    public static AddressDto toDto(Address userAddress){
        return userAddress == null
                ? null
                : new AddressDto(
                userAddress.getCity(),
                userAddress.getStreet(),
                userAddress.getVoivodeship(),
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
                userAddressDto.voivodeship(),
                userAddressDto.zipCode(),
                userAddressDto.houseNumber(),
                userAddressDto.apartmentNumber()
        );
    }
}
