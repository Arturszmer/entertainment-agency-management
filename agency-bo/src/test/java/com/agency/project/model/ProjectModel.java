package com.agency.project.model;

import com.agency.dto.project.ProjectCreateDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProjectModel {

    public static ProjectCreateDto getProjectCreateDto(){
        return new ProjectCreateDto(
                LocalDate.of(2024, 1, 2),
                LocalDate.of(2024, 1, 2),
                LocalDate.of(2024, 1, 31),
                "Big city concert",
                BigDecimal.valueOf(2000L),
                "Concert will be in theater"
        );
    }

}
