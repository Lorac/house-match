package ca.ulaval.glo4003.housematch.domain.property;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.math.BigDecimal;

public class Property {

    private PropertyType propertyType;
    private Address address;
    private BigDecimal sellingPrice;
    private PropertyDetails propertyDetails;
    private Integer viewCount;
    private boolean isMostPopular;

    public Property(final PropertyType propertyType, final Address address, final BigDecimal sellingPrice,
            final PropertyDetails propertyDetails) {
        this.propertyType = propertyType;
        this.address = address;
        this.sellingPrice = sellingPrice;
        this.propertyDetails = propertyDetails;
        this.viewCount = new Integer(0);
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
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

    public int increaseViewCount() {
        return ++viewCount;
    }

    public boolean isMostPopular() {
        return isMostPopular;
    }

    public void setMostPopular(boolean mostPopular) {
        isMostPopular = mostPopular;
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
