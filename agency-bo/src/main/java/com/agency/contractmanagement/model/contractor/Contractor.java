package com.agency.contractmanagement.model.contractor;

import com.agency.common.BaseEntity;
import com.agency.contractmanagement.model.contract.Contract;
import com.agency.user.model.Address;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "contractor")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Contractor extends BaseEntity<Long> {

    @Column(name = "public_id", nullable = false)
    private UUID publicId;
    private String firstName;
    private String lastName;
    private String pesel;
    private LocalDate birthDate;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Setter
    private Address address;
    private String phone;
    @Column(name = "contractor_description")
    private String contractorDescription;
    @OneToMany
    private List<Contract> contractOfPiece = new ArrayList<>();

    public Contractor(String name, String lastName, String pesel, LocalDate birthDate, Address address, String phone, String contractorType) {
        this.publicId = UUID.randomUUID();
        this.firstName = name;
        this.lastName = lastName;
        this.pesel = pesel;
        this.birthDate = birthDate;
        this.address = address;
        this.phone = phone;
        this.contractorDescription = contractorType;
    }
}
