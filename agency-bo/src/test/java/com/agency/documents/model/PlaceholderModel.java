package com.agency.documents.model;

import java.util.Set;

public class PlaceholderModel {

    public static final Set<String> contractWorkPlaceholders = Set.of(
            "ContractWork_withCopyrights",
            "ContractWork_projectNumber",
            "ContractWork_contractNumber",
            "ContractWork_signDate",
            "ContractWork_startDate",
            "ContractWork_endDate",
            "ContractWork_contractSubject",
            "ContractWork_salary",
            "ContractWork_additionalInformation"
    );

    public static final Set<String> contractorPlaceholders = Set.of(
            "Contractor_firstName",
            "Contractor_lastName",
            "Contractor_pesel",
            "Contractor_birthDate",
            "Contractor_phone",
            "Contractor_email",
            "Contractor_contractorDescription",
            "Contractor_address_city",
            "Contractor_address_street",
            "Contractor_address_voivodeship",
            "Contractor_address_zipCode",
            "Contractor_address_houseNumber",
            "Contractor_address_apartmentNumber");

    public static final Set<String> agencyDetailsPlaceholders = Set.of(
            "AgencyDetails_agencyName",
            "AgencyDetails_nip",
            "AgencyDetails_regon",
            "AgencyDetails_pesel",
            "AgencyDetails_firstName",
            "AgencyDetails_lastName",
            "AgencyDetails_krsNumber",
            "AgencyDetails_address_city",
            "AgencyDetails_address_street",
            "AgencyDetails_address_voivodeship",
            "AgencyDetails_address_zipCode",
            "AgencyDetails_address_houseNumber",
            "AgencyDetails_address_apartmentNumber"
    );

    public static final Set<String> projectPlaceholders = Set.of(
            "Project_status",
            "Project_contractNumber",
            "Project_signDate",
            "Project_startDate",
            "Project_endDate",
            "Project_contractSubject",
            "Project_salary",
            "Project_additionalInformation"
    );
}
