package ca.ulaval.glo4003.housematch.spring.web.assemblers;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PropertyViewModelAssembler {

    public PropertyViewModel assembleFromProperty(Property property) {
        PropertyViewModel viewModel = new PropertyViewModel();
        viewModel.setPropertyType(property.getPropertyType());
        viewModel.setAddress(property.getAddress());
        viewModel.setSellingPrice(property.getSellingPrice());
        viewModel.setPropertyDetails(property.getPropertyDetails());
        return viewModel;
    }

    public List<PropertyViewModel> assembleFromPropertyList(List<Property> properties) {
        List<PropertyViewModel> viewModels = new ArrayList<>(properties.size());
        viewModels.addAll(properties.stream().map(this::assembleFromProperty).collect(Collectors.toList()));
        return viewModels;
    }
}
