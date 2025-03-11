package com.agency.documents.service.placeholdergenerator;

import com.agency.agencydetails.model.AgencyDetails;
import com.agency.contractmanagement.bills.model.Bill;
import com.agency.contractmanagement.contractwork.model.ContractWork;
import com.agency.contractmanagement.project.model.Project;
import com.agency.contractor.model.Contractor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TemplateResource {

    CONTRACT_WORK(ContractWork.class),
    CONTRACTOR(Contractor.class),
    AGENCY_DETAILS(AgencyDetails.class),
    PROJECT(Project.class),
    BILL(Bill.class);

    private final Class<?> clazz;

    public Class<?> getReourceClass() {
        return clazz;
    }
}
