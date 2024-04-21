package com.agency.agencydetails.model;


import com.agency.common.BaseEntity;
import com.agency.dto.agencydetails.AgencyDetailsDto;
import com.agency.user.assembler.UserProfileAssembler;
import com.agency.user.model.Address;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "agency_details")
@Getter
@NoArgsConstructor
public class AgencyDetails extends BaseEntity<Long> {

    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "NIP", nullable = false)
    private String nip;
    @Column(name = "REGON")
    private String regon;
    @Column(name = "PESEL")
    private String pesel;
    @Column(name = "KRS")
    private String krsNumber;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Setter
    private Address address;

    protected AgencyDetails(String name, String nip, String regon, String pesel, String krsNumber, Address address) {
        this.name = name;
        this.nip = nip;
        this.regon = regon;
        this.pesel = pesel;
        this.krsNumber = krsNumber;
        this.address = address;
    }

    public static AgencyDetails initialize(AgencyDetailsDto agencyDetailsDto){
        return new AgencyDetails(
                agencyDetailsDto.name(), agencyDetailsDto.nip(), agencyDetailsDto.regon(), agencyDetailsDto.pesel(),
                agencyDetailsDto.krsNumber(), UserProfileAssembler.toEntity(agencyDetailsDto.addressDto())
        );
    }

    public void update(AgencyDetailsDto agencyDetailsDto) {
        name = agencyDetailsDto.name();
        nip = agencyDetailsDto.nip();
        regon = agencyDetailsDto.regon();
        pesel = agencyDetailsDto.pesel();
        krsNumber = agencyDetailsDto.krsNumber();
        address = UserProfileAssembler.toEntity(agencyDetailsDto.addressDto());
    }
}
