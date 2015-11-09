package ca.ulaval.glo4003.housematch.domain.property;

import java.util.Collections;
import java.util.List;

public class PropertiesFilter {

    public void orderByAscendingDates(List<Property> properties) {
        Collections.sort(properties, (Property p1, Property p2) -> p1.getDate().compareTo(p2.getDate()));
    }

    public void orderByDescendingDates(List<Property> properties) {
        Collections.sort(properties, (Property p1, Property p2) -> p1.getDate().compareTo(p2.getDate()));
        Collections.reverse(properties);
    }
}
