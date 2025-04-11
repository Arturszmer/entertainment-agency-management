package com.agency.user.model;

import com.agency.common.BaseEntity;
import com.agency.common.PlaceholderOrder;
import com.agency.dto.address.AddressDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Objects;

@NoArgsConstructor
@Data
@Entity
@Table(name = "user_address")
public class Address extends BaseEntity<Long> {

    @PlaceholderOrder(order = 11)
    private String city;
    @PlaceholderOrder(order = 13)
    private String street;
    @PlaceholderOrder(order = 12)
    private String voivodeship;
    @PlaceholderOrder(order = 10)
    private String zipCode;
    @PlaceholderOrder(order = 14)
    private String houseNumber;
    @PlaceholderOrder(order = 15)
    private String apartmentNumber;


    public Address(String city, String street, String voivodeship, String zipCode, String houseNumber, String apartmentNumber) {
        this.city = city;
        this.street = street;
        this.voivodeship = voivodeship;
        this.zipCode = zipCode;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
    }

    @Override
    public boolean equals(Object changedAddress) {
        if (this == changedAddress) return true;
        if (changedAddress == null || getClass() != changedAddress.getClass()) return false;
        Address that = (Address) changedAddress;
        return Objects.equals(city, that.city)
                && Objects.equals(street, that.street)
                && Objects.equals(voivodeship, that.voivodeship)
                && Objects.equals(zipCode, that.zipCode)
                && Objects.equals(houseNumber, that.houseNumber)
                && Objects.equals(apartmentNumber, that.apartmentNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, voivodeship, zipCode, houseNumber, apartmentNumber);
    }

    public void update(AddressDto addressDto) {
        city = addressDto.city();
        street = addressDto.street();
        voivodeship = addressDto.voivodeship();
        zipCode = addressDto.zipCode();
        houseNumber = addressDto.houseNumber();
        apartmentNumber = addressDto.apartmentNumber();
    }

    public String getStreetWithNumber() {
        return StringUtils.hasText(apartmentNumber)
                ? String.format("%s %s/%s", street, houseNumber, apartmentNumber)
                : String.format("%s %s", street, houseNumber);
    }
}

