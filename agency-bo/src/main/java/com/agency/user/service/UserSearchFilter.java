package com.agency.user.service;

import com.agency.search.AbstractSortableConfig;
import com.agency.user.model.QUserProfile;
import com.querydsl.core.types.Predicate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.agency.user.model.QUserProfile.*;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class UserSearchFilter extends AbstractSortableConfig {

    private static final QUserProfile USER_PROFILE = userProfile;

    @Override
    public Predicate getPredicate() {
        return null;
    }

    public static UserSearchFilter forPageable(int page, int size, String sort, String order){
        UserSearchFilter filter = new UserSearchFilter();
        filter.setPage(page);
        filter.setSize(size);
        filter.setSort(sort);
        filter.setOrder(order);
        return filter;
    }
}
