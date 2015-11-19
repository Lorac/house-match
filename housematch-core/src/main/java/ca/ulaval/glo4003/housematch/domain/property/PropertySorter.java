package ca.ulaval.glo4003.housematch.domain.property;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PropertySorter {

    public void sortByCreationDateInAscendingOrder(List<Property> properties) {
        Collections.sort(properties, Comparator.comparing(Property::getCreationDate));
    }

    public void sortByCreationDateInDescendingOrder(List<Property> properties) {
        Collections.sort(properties, Comparator.comparing(Property::getCreationDate).reversed());
    }

    public void sortBySellingPriceInAscendingOrder(List<Property> properties) {
        Collections.sort(properties, Comparator.comparing(Property::getSellingPrice));
    }

    public void sortBySellingPriceInDescendingOrder(List<Property> properties) {
        Collections.sort(properties, Comparator.comparing(Property::getSellingPrice).reversed());
    }

    public void sortByViewCountInDescendingOrder(List<Property> properties) {
        Collections.sort(properties, Comparator.comparing(Property::getViewCount).reversed());
    }
}
