package ca.ulaval.glo4003.housematch.web.assemblers;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyListViewModel;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyViewModel;

public class PropertyListViewModelAssembler {
    PropertyViewModelAssembler propertyViewModelAssembler;

    public PropertyListViewModelAssembler(final PropertyViewModelAssembler propertyViewModelAssembler) {
        this.propertyViewModelAssembler = propertyViewModelAssembler;
    }

    public PropertyListViewModel assembleFromPropertyList(List<Property> propertyList) {
        PropertyListViewModel propertyListViewModel = new PropertyListViewModel();
        List<PropertyViewModel> propertyViewModelList = assemblePropertyViewModelListFromPropertyList(propertyList);
        propertyListViewModel.setPropertyViewModels(propertyViewModelList);
        return propertyListViewModel;
    }

    private List<PropertyViewModel> assemblePropertyViewModelListFromPropertyList(List<Property> propertyList) {
        List<PropertyViewModel> propertyViewModelList = new ArrayList<>();
        for (Property property : propertyList) {
            propertyViewModelList.add(propertyViewModelAssembler.assemble(property, null));
        }
        return propertyViewModelList;
    }
}
