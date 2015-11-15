package ca.ulaval.glo4003.housematch.domain.property;

import java.util.Collections;
import java.util.List;

public class PropertySorter {

    public void sortByDateInAscendingOrder(List<Property> properties) {
        Collections.sort(properties, (Property p1, Property p2) -> p1.getCreationDate().compareTo(p2.getCreationDate()));
    }

    public void sortByDateInDescendingOrder(List<Property> properties) {
        Collections.sort(properties, (Property p1, Property p2) -> p1.getCreationDate().compareTo(p2.getCreationDate()));
        Collections.reverse(properties);
    }
}
