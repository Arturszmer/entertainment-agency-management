package com.agency.organizer.service;

import com.agency.organizer.model.QOrganizer;
import com.agency.search.AbstractSortableConfig;
import com.querydsl.core.types.Predicate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class OrganizerSearchFilter extends AbstractSortableConfig {

    private static final QOrganizer organizer = QOrganizer.organizer;

    @Override
    public Predicate getPredicate() {
        return null;
    }

    public static OrganizerSearchFilter forPageable(int page, int size, String sort, String order){
        OrganizerSearchFilter organizerSearchFilter = new OrganizerSearchFilter();
        organizerSearchFilter.setPage(page);
        organizerSearchFilter.setSize(size);
        organizerSearchFilter.setSort(sort);
        organizerSearchFilter.setOrder(order);
        return organizerSearchFilter;
    }
}
