package com.agency.contractor.model;

import com.agency.common.BaseEntity;
import com.agency.common.ExcludeFromPlaceholders;
import com.agency.common.PlaceholderOrder;
import com.agency.contractmanagement.contractwork.model.ContractWork;
import com.agency.dto.contractor.ContractorCreateRequest;
import com.agency.exception.AgencyException;
import com.agency.exception.ContractErrorResult;
import com.agency.contractmanagement.project.model.Project;
import com.agency.user.model.Address;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "contractor")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Contractor extends BaseEntity<Long> {

    @Column(name = "public_id", nullable = false, unique = true)
    @ExcludeFromPlaceholders
    private UUID publicId;
    @PlaceholderOrder
    private String firstName;
    @PlaceholderOrder(order = 1)
    private String lastName;
    @Column(name = "pesel", nullable = false, unique = true)
    @PlaceholderOrder(order = 2)
    private String pesel;
    @PlaceholderOrder(order = 3)
    private LocalDate birthDate;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Setter
    @PlaceholderOrder(order = 7)
    private Address address;
    @PlaceholderOrder(order = 4)
    private String phone;
    @PlaceholderOrder(order = 5)
    private String email;
    @Column(name = "contractor_description")
    @PlaceholderOrder(order = 6)
    private String contractorDescription;
    @OneToMany(mappedBy = "contractor", cascade = CascadeType.PERSIST)
    @ExcludeFromPlaceholders
    private List<ContractWork> contracts = new ArrayList<>();
    @ManyToMany(mappedBy = "contractors")
    @ExcludeFromPlaceholders
    private List<Project> projects = new ArrayList<>();

    public Contractor(String name, String lastName, String pesel, LocalDate birthDate, Address address, String phone, String email, String contractorType) {
        this.publicId = UUID.randomUUID();
        this.firstName = name;
        this.lastName = lastName;
        this.pesel = pesel;
        this.birthDate = birthDate;
        this.address = address;
        this.phone = StringUtils.trimAllWhitespace(phone);
        this.email = email;
        this.contractorDescription = contractorType;
    }

    public void updatePersonalData(ContractorCreateRequest request) {
        firstName = request.firstName();
        lastName = request.lastName();
        pesel = request.pesel();
        birthDate = request.birthDate();
        address.update(request.addressDto());
        phone = StringUtils.trimAllWhitespace(request.phone());
        email = request.email();
        contractorDescription = request.contractorDescription();
    }

    public void addNewContract(ContractWork contract) {
        if(projects.stream()
                .anyMatch(project -> project.getContractNumber().equals(contract.getProjectNumber()))){
            contracts.add(contract);
        } else {
            throw new AgencyException(ContractErrorResult.CONTRACTOR_IS_NOT_PART_OF_THE_PROJECT, firstName, lastName, contract.getProjectNumber());
        }
    }

    public Contractor getContractorWithProjectContracts(String projectNumber) {
        this.contracts = getContractsForProject(projectNumber);
        return this;
    }

    public List<ContractWork> getContractsForProject(String projectNumber) {
        return contracts.stream()
                .filter(contractWork -> contractWork.getProjectNumber().equals(projectNumber))
                .toList();
    }
}
