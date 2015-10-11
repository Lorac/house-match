package ca.ulaval.glo4003.housematch.spring.web.viewmodels;

import javax.validation.constraints.NotNull;

import ca.ulaval.glo4003.housematch.domain.property.PropertyListingDetails;

public class PropertyListingUpdateFormViewModel extends ViewModel {
    public static final String VIEWMODEL_NAME = "propertyListingUpdateForm";

    @NotNull
    private PropertyListingDetails details;

    public PropertyListingDetails getDetails() {
        return details;
    }

    public void setDetails(PropertyListingDetails details) {
        this.details = details;
    }

    public String getViewModelName() {
        return VIEWMODEL_NAME;
    }

}
