package ca.ulaval.glo4003.housematch.spring.web.viewmodels;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;

public class PropertyViewModel extends ViewModel {

    public static final String NAME = "property";

    @NotNull
    private PropertyType propertyType;
    @NotNull
    private Address address;
    @NotNull
    private BigDecimal sellingPrice;
    private PropertyDetails propertyDetails;

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

}
