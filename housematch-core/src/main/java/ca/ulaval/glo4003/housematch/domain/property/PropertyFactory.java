package ca.ulaval.glo4003.housematch.domain.property;

import java.math.BigDecimal;

import ca.ulaval.glo4003.housematch.domain.address.Address;

public class PropertyFactory {

    public Property getProperty(final PropertyType propertyType, final Address address, final BigDecimal sellingPrice) {
        return new Property(propertyType, address, sellingPrice);
    }
}
