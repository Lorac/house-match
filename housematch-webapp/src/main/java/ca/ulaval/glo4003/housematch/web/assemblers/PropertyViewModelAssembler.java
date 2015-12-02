package ca.ulaval.glo4003.housematch.web.assemblers;

import java.util.Optional;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyViewModel;

public class PropertyViewModelAssembler {

    public PropertyViewModel assemble(Property property, Optional<User> user) {
        PropertyViewModel viewModel = new PropertyViewModel();
        viewModel.setPropertyType(property.getPropertyType());
        viewModel.setAddress(property.getAddress());
        viewModel.setSellingPrice(property.getSellingPrice());
        viewModel.setPropertyDetails(property.getPropertyDetails());
        viewModel.setHashCode(property.hashCode());
        viewModel.setCreationDate(property.getCreationDate());
        viewModel.setViewCount(property.getViewCount());
        if (user.isPresent()) {
            viewModel.setPropertyAddedToFavorites(user.get().hasPropertyInFavorites(property));
        }
        return viewModel;
    }

}
