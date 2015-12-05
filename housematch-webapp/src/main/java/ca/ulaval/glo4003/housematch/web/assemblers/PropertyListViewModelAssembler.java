package ca.ulaval.glo4003.housematch.web.assemblers;

import java.util.ArrayList;
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

    public PropertyListViewModel assembleFromPropertyCollection(Collection<Property> propertyCollection) {
        PropertyListViewModel propertyListViewModel = new PropertyListViewModel();
        List<PropertyViewModel> propertyViewModelList = assemblePropertyViewModelListFromPropertyCollection(propertyCollection);
        propertyListViewModel.setPropertyViewModels(propertyViewModelList);
        return propertyListViewModel;
    }

    private List<PropertyViewModel> assemblePropertyViewModelListFromPropertyCollection(Collection<Property> propertyCollection) {
        List<PropertyViewModel> propertyViewModelList = new ArrayList<>();
        for (Property property : propertyCollection) {
            propertyViewModelList.add(propertyViewModelAssembler.assemble(property, Optional.empty()));
        }
        return propertyViewModelList;
    }
}
