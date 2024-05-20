package com.agency.dto.contractor;

import com.agency.dto.AddressDto;

public record ShortContractorDto(String publicId,
                                 String firstName,
                                 String lastName,
                                 AddressDto addressDto,
                                 String phone,
                                 String email) {
}
