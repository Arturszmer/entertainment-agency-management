package com.agency.contractmanagement.model.contractor;

import com.agency.common.BaseEntity;
import com.agency.contractmanagement.model.contract.ContractWork;
import com.agency.dto.contractor.ContractorCreateRequest;
import com.agency.user.model.Address;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "contractor")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Contractor extends BaseEntity<Long> {

    @Column(name = "public_id", nullable = false, unique = true)
    private UUID publicId;
    private String firstName;
    private String lastName;
    @Column(name = "pesel", nullable = false, unique = true)
    private String pesel;
    private LocalDate birthDate;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Setter
    private Address address;
    private String phone;
    private String email;
    @Column(name = "contractor_description")
    private String contractorDescription;
    @OneToMany(mappedBy = "contractor", cascade = CascadeType.PERSIST)
    private List<ContractWork> contract = new ArrayList<>();

    public Contractor(String name, String lastName, String pesel, LocalDate birthDate, Address address, String phone, String email, String contractorType) {
        this.publicId = UUID.randomUUID();
        this.firstName = name;
        this.lastName = lastName;
        this.pesel = pesel;
        this.birthDate = birthDate;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.contractorDescription = contractorType;
    }

    public void updatePersonalData(ContractorCreateRequest request) {
        firstName = request.firstName();
        lastName = request.lastName();
        pesel = request.pesel();
        birthDate = request.birthDate();
        address.update(request.addressDto());
        phone = request.phone();
        email = request.email();
        contractorDescription = request.contractorDescription();
    }
}
