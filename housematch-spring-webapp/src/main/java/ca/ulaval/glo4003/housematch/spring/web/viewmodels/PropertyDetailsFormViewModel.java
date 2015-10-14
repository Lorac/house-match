package ca.ulaval.glo4003.housematch.spring.web.viewmodels;

import javax.validation.constraints.NotNull;

import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;

public class PropertyDetailsFormViewModel extends ViewModel {
    public static final String VIEWMODEL_NAME = "propertyDetailsForm";

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
