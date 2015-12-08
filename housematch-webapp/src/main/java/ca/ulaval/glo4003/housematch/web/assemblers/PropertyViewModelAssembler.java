package ca.ulaval.glo4003.housematch.web.assemblers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyViewModel;

public class PropertyViewModelAssembler {

    private PropertyPhotoViewModelAssembler propertyPhotoViewModelAssembler;

    public PropertyViewModelAssembler(final PropertyPhotoViewModelAssembler propertyPhotoViewModelAssembler) {
        this.propertyPhotoViewModelAssembler = propertyPhotoViewModelAssembler;
    }

    public List<PropertyViewModel> assemble(Collection<Property> properties, Optional<User> user) {
        List<PropertyViewModel> propertyViewModels = new ArrayList<>();
        for (Property property : properties) {
            propertyViewModels.add(assemble(property, user));
        }
        return propertyViewModels;
    }

    public PropertyViewModel assemble(Property property, Optional<User> user) {
        PropertyViewModel viewModel = new PropertyViewModel();
        viewModel.setPropertyType(property.getPropertyType());
        viewModel.setAddress(property.getAddress());
        viewModel.setSellingPrice(property.getSellingPrice());
        viewModel.setPropertyDetails(property.getPropertyDetails());
        viewModel.setPropertyHashCode(property.hashCode());
        viewModel.setCreationDate(property.getCreationDate());
        viewModel.setViewCount(property.getViewCount());
        setViewModelMainPhoto(viewModel, property);
        setViewModelPhotos(viewModel, property, user);
        setViewModelPropertyFavoritedFlag(viewModel, property, user);
        return viewModel;
    }

    private void setViewModelMainPhoto(PropertyViewModel viewModel, Property property) {
        if (property.getMainPhoto().isPresent()) {
            viewModel.setMainPhotoViewModel(propertyPhotoViewModelAssembler.assemble(property.getMainPhoto().get()));
        }
    }

    private void setViewModelPhotos(PropertyViewModel viewModel, Property property, Optional<User> user) {
        if (user.isPresent() && user.get().hasRole(UserRole.SELLER)) {
            viewModel.setPhotoViewModels(propertyPhotoViewModelAssembler.assemble(property.getPhotos()));
        } else {
            viewModel.setPhotoViewModels(propertyPhotoViewModelAssembler.assemble(property.getApprovedPhotos()));
        }
    }

    private void setViewModelPropertyFavoritedFlag(PropertyViewModel viewModel, Property property, Optional<User> user) {
        if (user.isPresent()) {
            viewModel.setPropertyFavorited(user.get().isPropertyFavorited(property));
        }
    }

}
