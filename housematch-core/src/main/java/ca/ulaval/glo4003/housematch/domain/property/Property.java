package ca.ulaval.glo4003.housematch.domain.property;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.utils.VersionedValue;

public class Property {

    private PropertyType propertyType;
    private Address address;
    private BigDecimal sellingPrice;
    private PropertyDetails propertyDetails;
    private Integer viewCount;
    private VersionedValue<Boolean> isMostViewed = new VersionedValue<>(false);
    private static Integer isMostViewedFlagValueVersion = 0;

    public Property(final PropertyType propertyType, final Address address, final BigDecimal sellingPrice,
            final PropertyDetails propertyDetails) {
        this.propertyType = propertyType;
        this.address = address;
        this.sellingPrice = sellingPrice;
        this.propertyDetails = propertyDetails;
        this.viewCount = new Integer(0);
    }

    public Integer getViewCount() {
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

    public Integer incrementViewCount() {
        return ++viewCount;
    }

    public Boolean isMostViewed() {
        return isMostViewed.getValue() && isMostViewed.getVersion().equals(isMostViewedFlagValueVersion);
    }

    public void markAsMostViewed() {
        isMostViewed.setValue(true, isMostViewedFlagValueVersion);
    }

    public static void incrementMostViewedFlagValueVersion() {
        isMostViewedFlagValueVersion++;
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
