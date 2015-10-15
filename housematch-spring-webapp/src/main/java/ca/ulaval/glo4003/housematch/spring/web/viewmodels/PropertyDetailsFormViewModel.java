package ca.ulaval.glo4003.housematch.spring.web.viewmodels;

import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;

import javax.validation.constraints.NotNull;

public class PropertyDetailsFormViewModel extends ViewModel {
    public static final String NAME = "propertyDetailsForm";

    @NotNull
    private PropertyDetails details;

    public PropertyDetails getDetails() {
        return details;
    }

    public void setDetails(PropertyDetails details) {
        this.details = details;
    }

    public String getName() {
        return NAME;
    }
}
