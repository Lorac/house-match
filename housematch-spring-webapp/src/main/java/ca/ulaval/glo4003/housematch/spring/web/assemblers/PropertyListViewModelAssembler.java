package ca.ulaval.glo4003.housematch.spring.web.assemblers;

import java.util.List;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyListViewModel;

public class PropertyListViewModelAssembler {
    PropertyViewModelAssembler propertyViewModelAssembler;

    public PropertyListViewModelAssembler(final PropertyViewModelAssembler propertyViewModelAssembler) {
        this.propertyViewModelAssembler = propertyViewModelAssembler;
    }

    public PropertyListViewModel assembleFromPropertyList(List<Property> properties) {
        PropertyListViewModel viewModel = new PropertyListViewModel();
        viewModel.setPropertyViewModels(propertyViewModelAssembler.assembleFromPropertyList(properties));
        return viewModel;
    }
}
