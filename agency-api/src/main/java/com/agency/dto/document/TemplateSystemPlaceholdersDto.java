package com.agency.dto.document;

import java.util.Set;

public record TemplateSystemPlaceholdersDto(String groupName,
                                            Set<String> variables) {
}
