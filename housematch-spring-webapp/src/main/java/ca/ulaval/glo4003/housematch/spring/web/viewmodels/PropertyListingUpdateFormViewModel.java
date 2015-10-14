package ca.ulaval.glo4003.housematch.spring.web.viewmodels;

import javax.validation.constraints.NotNull;

import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;

public class PropertyListingUpdateFormViewModel extends ViewModel {
    public static final String VIEWMODEL_NAME = "propertyListingUpdateForm";

    @NotNull
    private PropertyDetails details;

    public PropertyDetails getDetails() {
        return details;
    }

    public void setDetails(PropertyDetails details) {
        this.details = details;
    }

    public String getViewModelName() {
        return VIEWMODEL_NAME;
    }
}
