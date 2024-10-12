package com.agency.search;

import com.querydsl.core.types.Predicate;

public abstract class AbstractFilter {

    public abstract Predicate getPredicate();
}
