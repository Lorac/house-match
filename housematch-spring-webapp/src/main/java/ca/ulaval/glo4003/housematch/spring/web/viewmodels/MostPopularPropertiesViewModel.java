package ca.ulaval.glo4003.housematch.spring.web.viewmodels;

import ca.ulaval.glo4003.housematch.domain.property.PropertyType;

public class MostPopularPropertiesViewModel extends ViewModel {

    public static final String NAME = "mostPopularProperties";

    private PropertyType propertyType;

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
}
