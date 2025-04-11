package com.agency.contractmanagement.utils;

import com.agency.common.ExcludeFromPlaceholders;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class PlaceholderResolver {

    public static Map<String, Object> fillInPlaceholders(Object instance) {
        Map<String, Integer> placeholdersWithOrder = PlaceholderGenerator.generatePlaceholders(instance.getClass());
        Map<String, Object> filledPlaceholders = new LinkedHashMap<>();
        for (String placeholder : placeholdersWithOrder.keySet()) {
            Object value = resolvePlaceholders(placeholder, instance);
            filledPlaceholders.put(placeholder, value != null ? value : "");
        }
        return filledPlaceholders;
    }

    private static Object resolvePlaceholders(String placeholder, Object instance) {
        String prefix = instance.getClass().getSimpleName();
        String placeholderWithoutPrefix = placeholder.substring(prefix.length() + 1);
        String[] fields = placeholderWithoutPrefix.split("_");

        Object value = getFieldValue(instance, fields);
        return getValue(value);
    }

    private static Object getFieldValue(Object instance, String[] fieldNames) {
        Object currentObject = instance;
        for (String fieldName : fieldNames) {
            if (currentObject == null) {
                return null;
            }
            Class<?> clazz = currentObject.getClass();
            try {
                Field field = getFieldFromClassHierarchy(clazz, fieldName);
                if (field == null) {
                    log.warn("Field '{}' not found in class '{}'", fieldName, clazz.getName());
                    return null;
                }
                field.setAccessible(true);

                if (field.isAnnotationPresent(ExcludeFromPlaceholders.class)) {
                    return null;
                }

                currentObject = field.get(currentObject);
            } catch (Exception e) {
                log.error("Cannot access field '{}' in class '{}'", fieldName, clazz.getName(), e);
                return null;
            }
        }
        return currentObject;
    }

    private static Field getFieldFromClassHierarchy(Class<?> clazz, String fieldName) {
        Class<?> currentClass = clazz;
        while (currentClass != null) {
            try {
                return currentClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                currentClass = currentClass.getSuperclass();
            }
        }
        return null;
    }

    private static Object getValue(Object value) {
        if (value instanceof LocalDate date) {
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

    private static String formatDate(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return localDate.format(formatter);
    }

}

