package com.agency.dto.document;

import java.util.List;
import java.util.Map;

public record TemplateSystemPlaceholdersDto(String groupName,
                                            Map<String, Integer> variables,
                                            List<String> orderedVariables) {
}
