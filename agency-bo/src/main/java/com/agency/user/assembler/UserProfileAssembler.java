package com.agency.user.assembler;

import com.agency.dto.AddressDto;
import com.agency.dto.userprofile.UserProfileDetailsDto;
import com.agency.user.model.Address;
import com.agency.user.model.UserProfile;

public class UserProfileAssembler {

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

    public static UserProfileDetailsDto toDtoDetails(UserProfile savedUser) {
        return new UserProfileDetailsDto(
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getFirstName(),
                savedUser.getLastName()
        );
    }
}
