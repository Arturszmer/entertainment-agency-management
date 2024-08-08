package com.agency.dto.contractor;

import com.agency.dto.address.AddressDto;

public record ContractorShortInfoDto(String publicId,
                                     String firstName,
                                     String lastName,
                                     AddressDto addressDto,
                                     String phone,
                                     String email) {
}
