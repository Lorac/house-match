package ca.ulaval.glo4003.housematch.domain.property;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import org.apache.commons.lang3.builder.EqualsBuilder;

import ca.ulaval.glo4003.housematch.domain.address.Address;

public class Property extends PropertyObservable {

    private PropertyType propertyType;
    private Address address;
    private BigDecimal sellingPrice;
    private PropertyDetails propertyDetails;
    private PropertyStatus status = PropertyStatus.CREATED;
    private ZonedDateTime creationDate = ZonedDateTime.now();
    private Integer viewCount = 0;

    public Property(final PropertyType propertyType, final Address address, final BigDecimal sellingPrice,
            final PropertyDetails propertyDetails) {
        this.propertyType = propertyType;
        this.address = address;
        this.sellingPrice = sellingPrice;
        this.propertyDetails = propertyDetails;
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

    public PropertyStatus getStatus() {
        return status;
    }

    public void setStatus(PropertyStatus propertyStatus) {
        this.status = propertyStatus;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer incrementViewCount() {
        return ++viewCount;
    }

    public void markForSale() {
        status = PropertyStatus.FOR_SALE;
        propertyStatusChanged(this, status);
    }

    public void markAsSold() {
        status = PropertyStatus.SOLD;
        propertyStatusChanged(this, status);
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
