package ca.ulaval.glo4003.housematch.domain.property;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;

import ca.ulaval.glo4003.housematch.domain.streetaddress.StreetAddress;

public class Property {

    private PropertyType propertyType;
    private StreetAddress streetAddress;
    private BigDecimal sellingPrice;

    Property() {
        // Required for instanciation by reflection
    }

    public Property(final PropertyType propertyType, final StreetAddress streetAddress, final BigDecimal sellingPrice) {
        this.propertyType = propertyType;
        this.streetAddress = streetAddress;
        this.sellingPrice = sellingPrice;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public StreetAddress getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(StreetAddress streetAddress) {
        this.streetAddress = streetAddress;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    @Override
    public int hashCode() {
        return streetAddress.hashCode();
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
        return new EqualsBuilder().append(streetAddress, property.streetAddress).isEquals();
    }
}
