package com.agency.service;

import com.agency.dto.project.ProjectSearchDto;
import org.springframework.data.domain.Page;

public interface ProjectSearchService {

    Page<ProjectSearchDto> findAll(int page, int size, String sort, String order);
}
