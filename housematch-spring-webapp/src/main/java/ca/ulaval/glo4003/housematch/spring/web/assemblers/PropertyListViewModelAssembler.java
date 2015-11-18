package ca.ulaval.glo4003.housematch.spring.web.assemblers;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyListViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyViewModel;

public class PropertyListViewModelAssembler {
    PropertyViewModelAssembler propertyViewModelAssembler;

    public PropertySearchResultsViewModelAssembler(final PropertyViewModelAssembler propertyViewModelAssembler) {
        this.propertyViewModelAssembler = propertyViewModelAssembler;
    }

    public PropertySearchResultsViewModelAssembler() {
        this.propertyViewModelAssembler = new PropertyViewModelAssembler();
    }

    public PropertyListViewModel assembleFromPropertyList(List<Property> properties) {
        PropertyListViewModel viewModel = new PropertyListViewModel();
        viewModel.setPropertyViewModels(convertPropertyListToPropertyViewModelList(properties));
        return viewModel;
    }

    private List<PropertyViewModel> convertPropertyListToPropertyViewModelList(List<Property> properties) {
        List<PropertyViewModel> propertyViewModelList = new ArrayList<>();

        for (Property property : properties) {
            propertyViewModelList.add(propertyViewModelAssembler.assembleFromProperty(property));
        }

        return propertyViewModelList;
    }
}
