package ca.ulaval.glo4003.housematch.domain.property;

import java.math.BigDecimal;
import java.util.List;

import ca.ulaval.glo4003.housematch.domain.address.Address;

public class PropertyFactory {

    List<PropertyObserver> sharedPropertyObservers;

    public PropertyFactory(final List<PropertyObserver> sharedPropertyObservers) {
        this.sharedPropertyObservers = sharedPropertyObservers;
    }

    public Property createProperty(final PropertyType propertyType, final Address address, final BigDecimal sellingPrice) {
        Property property = new Property(propertyType, address, sellingPrice, new PropertyDetails());
        property.registerObservers(sharedPropertyObservers);
        return property;
    }
}
