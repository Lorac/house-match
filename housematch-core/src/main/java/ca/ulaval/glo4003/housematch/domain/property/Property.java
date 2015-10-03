package ca.ulaval.glo4003.housematch.domain.property;

import java.util.Currency;

import ca.ulaval.glo4003.housematch.domain.address.StreetAddress;

public class Property {

    PropertyType propertyType;
    StreetAddress streetAddress;
    Currency sellPrice;

    Property() {
        // Required for instanciation by reflection
    }

    public Property(final PropertyType propertyType, final StreetAddress streetAddress, final Currency sellPrice) {
        this.propertyType = propertyType;
        this.streetAddress = streetAddress;
        this.sellPrice = sellPrice;
    }
}
