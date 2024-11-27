package com.agency.user.model;

import com.agency.dict.userProfile.Permission;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

// TODO: think about this in the future

@Converter
public class PermissionSetConverter implements AttributeConverter<Set<Permission>, String> {

    private final static String SPLIT = ",";

    @Override
    public String convertToDatabaseColumn(Set<Permission> attribute) {
        if(attribute == null || attribute.isEmpty()) {
            return "";
        }
        return attribute.stream()
                .map(Permission::name)
                .collect(Collectors.joining(SPLIT));
    }

    @Override
    public Set<Permission> convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.split(SPLIT))
                .map(Permission::valueOf)
                .collect(Collectors.toSet());
    }
}
