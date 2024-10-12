package com.agency.contractmanagement.model;

import com.agency.contractor.model.Contractor;
import com.agency.project.model.Project;
import com.agency.user.model.Address;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContractorBuilder {

    private UUID publicId;
    private String firstName;
    private String lastName;
    private String pesel;
    private LocalDate birthDate;
    private Address address;
    private String phone;
    private String email;
    private String contractorDescription;
    private List<ContractWork> contract = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();

    public static ContractorBuilder aContractorBuilder() {
        return new ContractorBuilder();
    }

    public ContractorBuilder withPublicId(UUID publicId) {
        this.publicId = publicId;
        return this;
    }

    public ContractorBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ContractorBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ContractorBuilder withPesel(String pesel) {
        this.pesel = pesel;
        return this;
    }

    public ContractorBuilder withBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public ContractorBuilder withAddress(Address address) {
        this.address = address;
        return this;
    }

    public ContractorBuilder withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public ContractorBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public ContractorBuilder withContractorDescription(String contractorDescription) {
        this.contractorDescription = contractorDescription;
        return this;
    }

    public ContractorBuilder withContracts(List<ContractWork> contract) {
        this.contract = contract;
        return this;
    }

    public ContractorBuilder withProjects(List<Project> projects) {
        this.projects = projects;
        return this;
    }

    public Contractor build() {
        return new Contractor(
                publicId,
                firstName,
                lastName,
                pesel,
                birthDate,address,
                phone,
                email,
                contractorDescription,
                contract,
                projects
        );
    }
}
