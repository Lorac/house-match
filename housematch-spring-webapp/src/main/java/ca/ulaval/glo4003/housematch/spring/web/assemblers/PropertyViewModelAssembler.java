package ca.ulaval.glo4003.housematch.spring.web.assemblers;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyViewModel;

public class PropertyViewModelAssembler {

    public List<PropertyViewModel> assembleFromPropertyList(List<Property> propertyList) {
        List<PropertyViewModel> propertyViewModelList = new ArrayList<>();

        for (Property property : propertyList) {
            propertyViewModelList.add(assembleFromProperty(property));
        }

        return propertyViewModelList;
    }

    public PropertyViewModel assembleFromProperty(Property property) {
        PropertyViewModel viewModel = new PropertyViewModel();
        viewModel.setPropertyType(property.getPropertyType());
        viewModel.setAddress(property.getAddress());
        viewModel.setSellingPrice(property.getSellingPrice());
        viewModel.setPropertyDetails(property.getPropertyDetails());
        viewModel.setHashCode(property.hashCode());
        viewModel.setCreationDate(property.getCreationDate());
        return viewModel;
    }

}
