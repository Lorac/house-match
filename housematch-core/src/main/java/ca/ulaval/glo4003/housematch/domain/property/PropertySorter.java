package ca.ulaval.glo4003.housematch.domain.property;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PropertySorter {

    public void sortByDateInAscendingOrder(List<Property> properties) {
        Collections.sort(properties, Comparator.comparing(Property::getCreationDate));
    }

    public void sortByDateInDescendingOrder(List<Property> properties) {
        Collections.sort(properties, Comparator.comparing(Property::getCreationDate).reversed());
    }

    public void sortByPriceInAscendingOrder(List<Property> properties) {
        Collections.sort(properties, Comparator.comparing(Property::getSellingPrice));
    }

    public void sortByPriceInDescendingOrder(List<Property> properties) {
        Collections.sort(properties, Comparator.comparing(Property::getSellingPrice).reversed());
    }
}
