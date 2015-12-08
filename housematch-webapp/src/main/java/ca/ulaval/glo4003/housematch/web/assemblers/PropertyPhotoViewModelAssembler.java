package ca.ulaval.glo4003.housematch.web.assemblers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyPhotoViewModel;

public class PropertyPhotoViewModelAssembler {

    public List<PropertyPhotoViewModel> assemble(Collection<PropertyPhoto> propertyPhotos) {
        List<PropertyPhotoViewModel> propertyPhotoViewModels = new ArrayList<>();
        for (PropertyPhoto propertyPhoto : propertyPhotos) {
            propertyPhotoViewModels.add(assemble(propertyPhoto));
        }
        return propertyPhotoViewModels;
    }

    public PropertyPhotoViewModel assemble(PropertyPhoto propertyPhoto) {
        PropertyPhotoViewModel viewModel = new PropertyPhotoViewModel();
        viewModel.setHashCode(propertyPhoto.hashCode());
        return viewModel;
    }
}
