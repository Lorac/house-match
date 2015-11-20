package ca.ulaval.glo4003.housematch.domain.property;

import java.util.function.Function;

@SuppressWarnings("rawtypes")
public enum PropertySortColumn {
    NONE(null),
    SELLING_PRICE(Property::getSellingPrice),
    CREATION_DATE(Property::getCreationDate),
    VIEW_COUNT(Property::getViewCount);

    private final Function<Property, Comparable> sortFunction;

    PropertySortColumn(final Function<Property, Comparable> sortFunction) {
        this.sortFunction = sortFunction;
    }

    public Function<Property, Comparable> getSortFunction() {
        return sortFunction;
    }
}
