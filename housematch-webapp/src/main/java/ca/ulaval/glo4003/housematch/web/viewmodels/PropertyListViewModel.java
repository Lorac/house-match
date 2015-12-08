package ca.ulaval.glo4003.housematch.web.viewmodels;

import java.util.List;

public class PropertyListViewModel extends ViewModel {

    public static final String NAME = "propertyListViewModel";

    private List<PropertyViewModel> propertyViewModels;

    @Override
    public String getName() {
        return NAME;
    }

    public List<PropertyViewModel> getPropertyViewModels() {
        return propertyViewModels;
    }

    public void setPropertyViewModels(List<PropertyViewModel> properties) {
        this.propertyViewModels = properties;
    }
}
