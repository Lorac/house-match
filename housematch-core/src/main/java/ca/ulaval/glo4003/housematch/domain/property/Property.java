package ca.ulaval.glo4003.housematch.domain.property;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;

import ca.ulaval.glo4003.housematch.domain.address.Address;

public class Property {

    private PropertyType propertyType;
    private Address address;
    private BigDecimal sellingPrice;

    Property() {
        // Required for instanciation by reflection
    }

    public Property(final PropertyType propertyType, final Address address, final BigDecimal sellingPrice) {
        this.propertyType = propertyType;
        this.address = address;
        this.sellingPrice = sellingPrice;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    @Override
    public int hashCode() {
        return address.hashCode();
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
