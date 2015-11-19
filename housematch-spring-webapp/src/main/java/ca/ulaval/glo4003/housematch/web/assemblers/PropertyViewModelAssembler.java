package ca.ulaval.glo4003.housematch.web.assemblers;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyViewModel;

public class PropertyViewModelAssembler {

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
