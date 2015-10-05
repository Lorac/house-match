package ca.ulaval.glo4003.housematch.spring.web.viewmodels;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.domain.streetaddress.StreetAddress;

public class PropertyListingCreationFormViewModel extends ViewModel {

    public static final String VIEWMODEL_NAME = "propertyListingCreationForm";

    @NotNull
    private PropertyType propertyType;
    @NotNull
    private StreetAddress streetAddress;
    @NotNull
    private BigDecimal sellingPrice;

    public String getViewModelName() {
        return VIEWMODEL_NAME;
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
}
