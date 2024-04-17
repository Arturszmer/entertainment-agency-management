package com.agency.contractmanagement.model.contractor;

import com.agency.common.BaseEntity;
import com.agency.contractmanagement.model.contract.ContractOfPiece;
import com.agency.user.model.Address;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "contractor")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Contractor extends BaseEntity<Long> {

    private String name;
    private String lastName;
    private String pesel;
    private LocalDate birthDate;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Setter
    private Address address;
    private String phone;
    private String contractorType;
    @OneToMany
    private List<ContractOfPiece> contractOfPiece;

}
