package com.agency.agencydetails.model;


import com.agency.common.BaseEntity;
import com.agency.common.ExcludeFromPlaceholders;
import com.agency.dto.agencydetails.AgencyDetailsDto;
import com.agency.user.assembler.AddressAssembler;
import com.agency.user.model.Address;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "agency_details")
@Getter
@NoArgsConstructor
public class AgencyDetails extends BaseEntity<Long> implements Serializable {

    @Serial
    @ExcludeFromPlaceholders
    private static final long serialVersionUID = 5569323110732509331L;

    @Column(name = "AGENCY_NAME", nullable = false)
    private String agencyName;
    @Column(name = "NIP", nullable = false)
    private String nip;
    @Column(name = "REGON")
    private String regon;
    @Column(name = "PESEL")
    private String pesel;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "KRS")
    private String krsNumber;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Setter
    private Address address;

    protected AgencyDetails(String agencyName,
                            String nip,
                            String regon,
                            String pesel,
                            String firstName,
                            String lastName,
                            String krsNumber,
                            Address address) {
        this.agencyName = agencyName;
        this.nip = nip;
        this.regon = regon;
        this.pesel = pesel;
        this.firstName = firstName;
        this.lastName = lastName;
        this.krsNumber = krsNumber;
        this.address = address;
    }

    public static AgencyDetails initialize(AgencyDetailsDto agencyDetailsDto){
        return new AgencyDetails(
                agencyDetailsDto.agencyName(), agencyDetailsDto.nip(), agencyDetailsDto.regon(), agencyDetailsDto.pesel(),
                agencyDetailsDto.firstName(), agencyDetailsDto.lastName(), agencyDetailsDto.krsNumber(), AddressAssembler.toEntity(agencyDetailsDto.addressDto())
        );
    }

    public void update(AgencyDetailsDto agencyDetailsDto) {
        agencyName = agencyDetailsDto.agencyName();
        nip = agencyDetailsDto.nip();
        regon = agencyDetailsDto.regon();
        pesel = agencyDetailsDto.pesel();
        firstName = agencyDetailsDto.firstName();
        lastName = agencyDetailsDto.lastName();
        krsNumber = agencyDetailsDto.krsNumber();
        address = AddressAssembler.toEntity(agencyDetailsDto.addressDto());
    }

//    @Override
//    public String getPrefix() {
//        return "agency";
//    }
}
