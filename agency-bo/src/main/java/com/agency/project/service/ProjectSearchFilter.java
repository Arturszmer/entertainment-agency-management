package com.agency.project.service;

import com.agency.project.model.QProject;
import com.agency.search.AbstractSortableConfig;
import com.querydsl.core.types.Predicate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.agency.project.model.QProject.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class ProjectSearchFilter extends AbstractSortableConfig {

    private static final QProject PROJECT = project;

    @Override
    public Predicate getPredicate() {
        return null;
    }

    public static ProjectSearchFilter forPageable(int page, int size, String sort, String order){
        ProjectSearchFilter filter = new ProjectSearchFilter();
        filter.setPage(page);
        filter.setSize(size);
        filter.setSort(sort);
        filter.setOrder(order);
        return filter;
    }
}
