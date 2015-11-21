package ca.ulaval.glo4003.housematch.web.viewmodels;

import javax.validation.constraints.NotNull;

import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;

public class PropertyDetailsFormViewModel extends ViewModel {
    public static final String NAME = "propertyDetailsForm";

    @NotNull
    private PropertyDetails details;

    @Override
    public String getName() {
        return NAME;
    }

    public PropertyDetails getDetails() {
        return details;
    }

    public void setDetails(PropertyDetails details) {
        this.details = details;
    }
}
