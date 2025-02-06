package com.agency.contractmanagement.contractnumber.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ContractNumberStatus {
    DRAFT("D"), FINAL("F");

    private final String status;
}
