package com.agency.dto.contractor;

public record ContractorSearchRequest(
        int page,
        int size,
        String sort,
        String order,
        String firstName,
        String lastName,
        String pesel,
        String email
) {
}
