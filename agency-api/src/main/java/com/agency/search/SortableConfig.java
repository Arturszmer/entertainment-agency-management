package com.agency.search;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortableConfig {

    public Pageable getPageable(int page, int size, String sort, String order) {
        Sort.Direction direction = Sort.Direction.fromString((order != null) ? order : "asc");
        String defaultSortColumn = "id";
        String sortField = (sort != null) ? sort : defaultSortColumn;

        return PageRequest.of(page, size, Sort.by(direction, sortField));
    }
}
