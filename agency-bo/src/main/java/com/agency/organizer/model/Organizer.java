package com.agency.organizer.model;

import com.agency.common.BaseEntity;
import com.agency.dto.organizer.OrganizerCreate;
import com.agency.user.assembler.AddressAssembler;
import com.agency.user.helper.SecurityContextUsers;
import com.agency.user.model.Address;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "organizer")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Organizer extends BaseEntity<Long> {

    @Column(name = "public_id", nullable = false, unique = true, length = 50)
    private UUID publicId;
    @Column(name = "organizer_name", nullable = false, unique = true, length = 100)
    private String organizerName;
    @Column(name = "email", length = 30)
    private String email;
    @Column(name = "telephone", length = 15)
    private String telephone;
    @Column(name = "contact_manager", length = 50)
    private String username;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Setter
    private Address address;
    @Column(name = "notes", length = 1000)
    private String notes;

    public Organizer(String notes, Address address, String telephone, String email, String organizerName) {
        this.publicId = UUID.randomUUID();
        this.notes = notes;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.organizerName = organizerName;
        this.username = SecurityContextUsers.getUsernameFromAuthenticatedUser();
    }

    public static Organizer createOrganizer(OrganizerCreate create){
        return new Organizer(create.notes(),
                AddressAssembler.toEntity(create.addressDto()), create.telephone(), create.email(), create.organizerName());
    }
}
