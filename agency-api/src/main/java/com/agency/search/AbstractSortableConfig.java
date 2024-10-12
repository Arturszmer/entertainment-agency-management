package com.agency.search;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractSortableConfig extends AbstractFilter {

    private int page;
    private int size;
    private String sort;
    private String order;

    public Pageable getPageable() {
        Sort.Direction direction = Sort.Direction.fromString((StringUtils.hasText(order)) ? order : "asc");
        String defaultSortColumn = "id";
        String sortField = (sort != null) ? sort : defaultSortColumn;

        return PageRequest.of(page, size, Sort.by(direction, sortField));
    }
}
