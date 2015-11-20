package ca.ulaval.glo4003.housematch.domain.property;

import ca.ulaval.glo4003.housematch.domain.address.Address;

import java.math.BigDecimal;

public class PropertyFactory {

    PropertyObserver sharedPropertyObserver;

    public PropertyFactory(final PropertyObserver sharedPropertyObserver) {
        this.sharedPropertyObserver = sharedPropertyObserver;
    }

    public Property createProperty(final PropertyType propertyType, final Address address, final BigDecimal sellingPrice) {
        Property property = new Property(propertyType, address, sellingPrice, new PropertyDetails());
        property.registerObserver(sharedPropertyObserver);
        return property;
    }
}
