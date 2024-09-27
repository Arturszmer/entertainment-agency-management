package com.agency.dto.contractor;

import com.agency.dto.address.AddressDto;
import com.agency.dto.contractwork.ContractShortDto;

import java.util.List;

public record ContractorShortInfoDto(String publicId,
                                     String firstName,
                                     String lastName,
                                     AddressDto addressDto,
                                     String phone,
                                     String email,
                                     List<ContractShortDto> contracts) {
}
