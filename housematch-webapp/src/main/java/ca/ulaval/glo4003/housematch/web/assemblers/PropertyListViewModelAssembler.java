package ca.ulaval.glo4003.housematch.web.assemblers;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyListViewModel;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyViewModel;

public class PropertyListViewModelAssembler {

    PropertyViewModelAssembler propertyViewModelAssembler;

    public PropertyListViewModelAssembler(final PropertyViewModelAssembler propertyViewModelAssembler) {
        this.propertyViewModelAssembler = propertyViewModelAssembler;
    }

    public PropertyListViewModel assemble(Collection<Property> properties) {
        PropertyListViewModel propertyListViewModel = new PropertyListViewModel();
        List<PropertyViewModel> propertyViewModels = propertyViewModelAssembler.assemble(properties, Optional.empty());
        propertyListViewModel.setPropertyViewModels(propertyViewModels);
        return propertyListViewModel;
    }

}
