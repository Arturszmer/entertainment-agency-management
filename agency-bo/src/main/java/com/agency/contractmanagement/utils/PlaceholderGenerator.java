package com.agency.contractmanagement.utils;

import com.agency.common.ExcludeFromPlaceholders;
import com.agency.common.PlaceholderOrder;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PlaceholderGenerator {

    public static Map<String, Integer> generatePlaceholders(Class<?> clazz) {
        String prefix = clazz.getSimpleName();
        return generate(clazz, prefix);
    }

    private static Map<String, Integer> generatePlaceholders(Class<?> clazz, String prefix) {
        return generate(clazz, prefix);
    }

    private static Map<String, Integer> generate(Class<?> clazz, String prefix) {
        Map<String, Integer> placeholders = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        addPlaceholders(placeholders, fields, "", prefix);
        if (clazz.getSuperclass() != null) {
            placeholders.putAll(generatePlaceholders(clazz.getSuperclass(), prefix));
        }
        return placeholders;
    }

    private static void addPlaceholders(Map<String, Integer> placeholders,
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

    private static void build(Map<String, Integer> placeholders,
                              Field field,
                              String nestedField,
                              String prefix) {
        if (isNotNestedField(field.getType())) {
            int order = 999;
            if(field.getAnnotation(PlaceholderOrder.class) != null) {
                order = field.getAnnotation(PlaceholderOrder.class).order();
            }
            placeholders.put(buildPrefix(field.getName(), nestedField, prefix), order);
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
