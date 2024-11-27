package com.agency.contractmanagement.utils;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class AgencyPlaceholderGenerator extends PlaceholderGenerator{

    @Override
    protected void add(Map<String, Object> placeholders, Object instance, Field field, String prefix) throws IllegalAccessException {
        Object value = field.get(instance);
        if(isNotNestedField(value)){
            placeholders.put(buildPrefix(field.getName(), prefix), Objects.requireNonNullElse(getValue(value), ""));
        } else {
            fillIn(value.getClass().getDeclaredFields(), placeholders, value, field.getName());
        }
    }

    @NotNull
    private static String buildPrefix(String field, String nestedField) {
        if(StringUtils.hasText(nestedField)){
            return "agency_" + nestedField + "_" + field;
        }
        return "agency_" + field;
    }
}
