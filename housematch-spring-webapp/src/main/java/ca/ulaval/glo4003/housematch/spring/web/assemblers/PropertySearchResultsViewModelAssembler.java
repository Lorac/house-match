package ca.ulaval.glo4003.housematch.spring.web.assemblers;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertySearchResultsViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyViewModel;

public class PropertySearchResultsViewModelAssembler {
    PropertyViewModelAssembler propertyViewModelAssembler;

    public PropertySearchResultsViewModelAssembler(final PropertyViewModelAssembler propertyViewModelAssembler) {
        this.propertyViewModelAssembler = propertyViewModelAssembler;
    }

    public PropertySearchResultsViewModelAssembler() {
        this.propertyViewModelAssembler = new PropertyViewModelAssembler();
    }

    public PropertySearchResultsViewModel assembleFromPropertyList(List<Property> properties) {
        PropertySearchResultsViewModel viewModel = new PropertySearchResultsViewModel();
        viewModel.setPropertyViewModels(convertPropertyListToPropertyViewModelList(properties));
        return viewModel;
    }

    private List<PropertyViewModel> convertPropertyListToPropertyViewModelList(List<Property> properties) {
        List<PropertyViewModel> propertiesViewModel = new ArrayList<>();

        for (Property property : properties) {
            propertiesViewModel.add(propertyViewModelAssembler.assembleFromProperty(property));
        }

        return propertiesViewModel;
    }
}
