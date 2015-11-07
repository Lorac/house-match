package ca.ulaval.glo4003.housematch.domain.property;

import ca.ulaval.glo4003.housematch.domain.address.Address;

import java.math.BigDecimal;

public class PropertyFactory {

    public Property createProperty(final PropertyType propertyType, final Address address, final BigDecimal sellingPrice) {
        return new Property(propertyType, address, sellingPrice, new PropertyDetails(), new ViewCount());
    }
}
