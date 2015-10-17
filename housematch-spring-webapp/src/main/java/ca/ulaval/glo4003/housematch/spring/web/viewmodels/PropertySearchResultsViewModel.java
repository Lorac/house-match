package ca.ulaval.glo4003.housematch.spring.web.viewmodels;

import java.util.List;

import ca.ulaval.glo4003.housematch.domain.property.Property;

public class PropertySearchResultsViewModel extends ViewModel {

    public static final String NAME = "propertySearchResults";

    private List<Property> properties;

    @Override
    public String getName() {
        return NAME;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
}
