package ca.ulaval.glo4003.housematch.spring.web.assemblers;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertySearchResultsViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyViewModel;

public class PropertySearchResultsViewModelAssembler {

    public PropertySearchResultsViewModel assembleFromPropertyList(List<Property> properties) {

        PropertyViewModelAssembler propertyViewModelAssembler = new PropertyViewModelAssembler();
        List<PropertyViewModel> propertiesViewModel = new ArrayList<>();

        for (Property property : properties) {
            propertiesViewModel.add(propertyViewModelAssembler.assembleFromProperty(property));
        }

        PropertySearchResultsViewModel viewModel = new PropertySearchResultsViewModel();
        viewModel.setPropertyViewModels(propertiesViewModel);

        return viewModel;
    }
}
