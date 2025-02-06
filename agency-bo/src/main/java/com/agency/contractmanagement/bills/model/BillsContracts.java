package com.agency.contractmanagement.bills.model;

public interface BillsContracts<T extends Bill> {

    void addBill(T bill);
}
