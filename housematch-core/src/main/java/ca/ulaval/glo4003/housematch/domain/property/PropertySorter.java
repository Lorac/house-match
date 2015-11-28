package ca.ulaval.glo4003.housematch.domain.property;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ca.ulaval.glo4003.housematch.domain.SortOrder;

public class PropertySorter {

    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<Property> sort(List<Property> properties, PropertySortColumn sortColumn, SortOrder sortOrder) {
        if (sortColumn != PropertySortColumn.NONE) {
            if (sortOrder == SortOrder.ASCENDING) {
                Collections.sort(properties, Comparator.<Property, Comparable>comparing(sortColumn.getSortFunction()));
            } else if (sortOrder == SortOrder.DESCENDING) {
                Collections.sort(properties, Comparator.<Property, Comparable>comparing(sortColumn.getSortFunction()).reversed());
            }
        }
        return properties;
    }

}
