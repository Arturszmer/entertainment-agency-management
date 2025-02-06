package com.agency.dict.contract;

public enum ContractWorkStatus {
    DRAFT, CONFIRMED, TERMINATED;

    public static boolean canCreateABill(ContractWorkStatus status){
        return status.equals(CONFIRMED);
    }
}
