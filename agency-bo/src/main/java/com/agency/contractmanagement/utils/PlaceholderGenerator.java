package com.agency.contractmanagement.utils;

import com.agency.common.ExcludeFromPlaceholders;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class PlaceholderGenerator {

    public final Map<String, Object> getPlaceholders(Class clazz, Object instance) {
        Map<String, Object> placeholders = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        fillIn(fields, placeholders, instance, "");

        if(clazz.getSuperclass() != null){
            placeholders.putAll(getPlaceholders(clazz.getSuperclass(), instance));
        }
        return placeholders;
    }

    protected void fillIn(Field[] fields, Map<String, Object> placeholders, Object instance, String nestedFieldName){
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (!field.isAnnotationPresent(ExcludeFromPlaceholders.class)) {
                    add(placeholders, instance, field, nestedFieldName);
                }
            } catch (IllegalAccessException e) {
                log.error("Field: {} instantiated error, message: {}", field.getName(), e.getMessage());
            }
        }
    }

    protected abstract void add(Map<String, Object> placeholders, Object instance, Field field, String prefix) throws IllegalAccessException;

    protected boolean isNotNestedField(Object value) {
        if (value == null) return true;
        return value instanceof Enum<?> || value instanceof String || value instanceof Integer ||
                value instanceof Double || value instanceof Boolean || value instanceof Long ||
                value instanceof BigDecimal || value instanceof LocalDate || value instanceof LocalDateTime;
    }

    protected Object getValue(Object value) {
        if(value instanceof LocalDate date){
            return formatDate(date);
        } else if (value instanceof BigDecimal decimal) {
            DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
            decimalFormatSymbols.setDecimalSeparator(',');
            decimalFormatSymbols.setGroupingSeparator(' ');
            DecimalFormat df = new DecimalFormat("#,###.00", decimalFormatSymbols);
            return df.format(decimal);
        } else {
            return value;
        }
    }

    protected String formatDate(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return localDate.format(formatter);
    }
}
