package com.agency.model;

import com.agency.dto.project.ProjectCreateDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ProjectModel {

    public static ProjectCreateDto getProjectCreateDto(){
        return new ProjectCreateDto(
                LocalDate.of(2024, 1, 2),
                LocalDate.of(2024, 1, 2),
                LocalDate.of(2024, 1, 31),
                "Big city concert",
                BigDecimal.valueOf(2000L),
                "Concert will be in theater",
                "d4bfca9f-2056-4e49-9c15-1abc10015c2e",
                List.of(),
                false
        );
    }
}
