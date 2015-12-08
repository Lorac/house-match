package ca.ulaval.glo4003.housematch.web.viewmodels;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.utils.DateFormatter;

public class PropertyViewModel extends ViewModel {

    public static final String NAME = "propertyViewModel";

    @NotNull
    private PropertyType propertyType;
    @NotNull
    private Address address;
    @NotNull
    private BigDecimal sellingPrice;
    private PropertyDetails propertyDetails;
    private int propertyHashCode;
    private ZonedDateTime creationDate;
    private Integer viewCount;
    private Boolean propertyFavorited;
    private List<PropertyPhotoViewModel> photoViewModels;
    private PropertyPhotoViewModel mainPhotoViewModel;

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

    public int getPropertyHashCode() {
        return propertyHashCode;
    }

    public void setPropertyHashCode(int propertyHashCode) {
        this.propertyHashCode = propertyHashCode;
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

    public List<PropertyPhotoViewModel> getPhotoViewModels() {
        return photoViewModels;
    }

    public void setPhotoViewModels(List<PropertyPhotoViewModel> photos) {
        this.photoViewModels = photos;
    }

    public PropertyPhotoViewModel getMainPhotoViewModel() {
        return mainPhotoViewModel;
    }

    public void setMainPhotoViewModel(PropertyPhotoViewModel mainPhoto) {
        this.mainPhotoViewModel = mainPhoto;
    }

}
