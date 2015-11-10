package ca.ulaval.glo4003.housematch.domain.property;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PropertySorter {

    public void sortByHighestViewCount(List<Property> all) {
        Collections.sort(all, Comparator.comparing(Property::getViewCount));
        Collections.reverse(all);
    }
}
