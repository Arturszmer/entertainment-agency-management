package com.agency.contractor.service;

import com.agency.contractor.model.QContractor;
import com.agency.search.AbstractSortableConfig;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.agency.contractor.model.QContractor.contractor;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class ContractorSearchFilter extends AbstractSortableConfig {

    private static final QContractor CONTRACTOR = contractor;

    @Override
    public Predicate getPredicate() {
        BooleanBuilder bb = new BooleanBuilder();
        return bb;
    }

    public static ContractorSearchFilter forPageable(int page, int size, String sort, String order){
        ContractorSearchFilter contractorSearchFilter = new ContractorSearchFilter();
        contractorSearchFilter.setPage(page);
        contractorSearchFilter.setSize(size);
        contractorSearchFilter.setSort(sort);
        contractorSearchFilter.setOrder(order);
        return contractorSearchFilter;
    }
}
