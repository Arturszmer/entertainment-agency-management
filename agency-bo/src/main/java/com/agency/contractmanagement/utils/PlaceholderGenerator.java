package com.agency.contractmanagement.utils;

import com.agency.common.ExcludeFromPlaceholders;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class PlaceholderGenerator {

    public static Set<String> generatePlaceholders(Class<?> clazz) {
        String prefix = clazz.getSimpleName();
        return generate(clazz, prefix);
    }

    private static Set<String> generatePlaceholders(Class<?> clazz, String prefix) {
        return generate(clazz, prefix);
    }

    private static Set<String> generate(Class<?> clazz, String prefix) {
        Set<String> placeholders = new HashSet<>();
        Field[] fields = clazz.getDeclaredFields();
        addPlaceholders(placeholders, fields, "", prefix);
        if (clazz.getSuperclass() != null) {
            placeholders.addAll(generatePlaceholders(clazz.getSuperclass(), prefix));
        }
        return placeholders;
    }

    private static void addPlaceholders(Set<String> placeholders,
                                        Field[] fields,
                                        String nestedFieldName,
                                        String prefix) {
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (!field.isAnnotationPresent(ExcludeFromPlaceholders.class) && !field.getType().getName().equals("org.slf4j.Logger")) {
                    build(placeholders, field, nestedFieldName, prefix);
                }
            } catch (Exception e) {
                log.error("Field: {} instantiated error, message: {}", field.getName(), e.getMessage());
            }
        }
    }

    private static void build(Set<String> placeholders,
                              Field field,
                              String nestedField,
                              String prefix) {
        if (isNotNestedField(field.getType())) {
            placeholders.add(buildPrefix(field.getName(), nestedField, prefix));
        } else {
            addPlaceholders(placeholders, field.getType().getDeclaredFields(), field.getName(), prefix);
        }
    }

    @NotNull
    private static String buildPrefix(String field, String nestedField, String prefix) {
        if (StringUtils.hasText(nestedField)) {
            return prefix + "_" + nestedField + "_" + field;
        }
        return prefix + "_" + field;
    }

    private static boolean isNotNestedField(Class<?> type) {
        if (type.isPrimitive()) return true;
        return type.isEnum()
                || Number.class.isAssignableFrom(type)
                || type.equals(String.class)
                || type.equals(Integer.class)
                || type.equals(Double.class)
                || type.equals(Boolean.class)
                || type.equals(Long.class)
                || type.equals(BigDecimal.class)
                || type.equals(LocalDate.class)
                || type.equals(LocalDateTime.class);
    }
}
