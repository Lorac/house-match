package ca.ulaval.glo4003.housematch.domain.property;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PropertySorter {

    public void sortByHighestViewCount(List<Property> properties) {
        Collections.sort(properties, Comparator.comparing(Property::getViewCount).reversed());
    }
}
