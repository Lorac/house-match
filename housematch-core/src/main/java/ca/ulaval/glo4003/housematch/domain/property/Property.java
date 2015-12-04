package ca.ulaval.glo4003.housematch.domain.property;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;

public class Property extends PropertyObservable {

    private PropertyType propertyType;
    private Address address;
    private BigDecimal sellingPrice;
    private List<BigDecimal> sellingPriceHistory = new ArrayList<>();
    private PropertyDetails propertyDetails;
    private Set<PropertyPhoto> photos = new HashSet<>();
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

    public List<BigDecimal> getSellingPriceHistory() {
        return sellingPriceHistory;
    }

    public void setSellingPriceHistory(List<BigDecimal> sellingPriceHistory) {
        this.sellingPriceHistory = sellingPriceHistory;
    }

    public Integer incrementViewCount() {
        return ++viewCount;
    }

    public boolean isForSale() {
        return status == PropertyStatus.FOR_SALE;
    }

    public void markForSale() {
        status = PropertyStatus.FOR_SALE;
        propertyStatusChanged(this, status);
    }

    public void markAsSold() {
        status = PropertyStatus.SOLD;
        propertyStatusChanged(this, status);
    }

    public void updateSellingPrice(BigDecimal sellingPrice) {
        if (!sellingPrice.equals(this.sellingPrice)) {
            sellingPriceHistory.add(this.sellingPrice);
            this.sellingPrice = sellingPrice;
        }
    }

    public void updatePropertyDetails(PropertyDetails propertyDetails) {
        this.propertyDetails = propertyDetails;
        propertyDetailsChanged(this, propertyDetails);
    }

    public void addPhoto(PropertyPhoto propertyPhoto) {
        photos.add(propertyPhoto);
    }

    public void removePhoto(PropertyPhoto propertyPhoto) {
        photos.remove(propertyPhoto);
    }

    public Set<PropertyPhoto> getPhotos() {
        return photos;
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

    @Override
    public String toString() {
        return address.toString();
    }
}
