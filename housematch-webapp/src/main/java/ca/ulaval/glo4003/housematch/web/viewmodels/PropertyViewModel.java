package ca.ulaval.glo4003.housematch.web.viewmodels;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

import javax.validation.constraints.NotNull;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.utils.DateFormatter;

public class PropertyViewModel extends ViewModel {

    public static final String NAME = "property";

    @NotNull
    private PropertyType propertyType;
    @NotNull
    private Address address;
    @NotNull
    private BigDecimal sellingPrice;
    private PropertyDetails propertyDetails;
    private int hashCode;
    private ZonedDateTime creationDate;
    private Integer viewCount;
    private Boolean propertyFavorited;
    private Set<PropertyPhoto> photos;
    private PropertyPhoto mainPhoto;

    public PropertyViewModel() {

    }

    @Override
    public String getName() {
        return NAME;
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

    public PropertyDetails getPropertyDetails() {
        return propertyDetails;
    }

    public void setPropertyDetails(PropertyDetails propertyDetails) {
        this.propertyDetails = propertyDetails;
    }

    public int getHashCode() {
        return hashCode;
    }

    public void setHashCode(int hashCode) {
        this.hashCode = hashCode;
    }

    public String getCreationDate() {
        return DateFormatter.toShortDate(creationDate);
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Boolean isPropertyFavorited() {
        return propertyFavorited;
    }

    public void setPropertyFavorited(Boolean propertyFavorited) {
        this.propertyFavorited = propertyFavorited;
    }

    public Set<PropertyPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<PropertyPhoto> photos) {
        this.photos = photos;
    }

    public PropertyPhoto getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(PropertyPhoto mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

}
