package ca.ulaval.glo4003.housematch.domain.property;

import java.math.BigDecimal;
import java.time.ZonedDateTime;


import org.apache.commons.lang3.builder.EqualsBuilder;

import ca.ulaval.glo4003.housematch.domain.address.Address;

public class Property {

    private PropertyType propertyType;
    private Address address;
    private BigDecimal sellingPrice;
    private PropertyDetails propertyDetails;
    private ZonedDateTime date;

    public Property(final PropertyType propertyType, final Address address, final BigDecimal sellingPrice,
                    final PropertyDetails propertyDetails) {
        this.propertyType = propertyType;
        this.address = address;
        this.sellingPrice = sellingPrice;
        this.propertyDetails = propertyDetails;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public Address getAddress() {
        return address;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public PropertyDetails getPropertyDetails() {
        return propertyDetails;
    }

    public void setPropertyDetails(PropertyDetails propertyDetails) {
        this.propertyDetails = propertyDetails;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        return Math.abs(address.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Property)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Property property = (Property) obj;
        return new EqualsBuilder().append(address, property.address).isEquals();
    }
}
