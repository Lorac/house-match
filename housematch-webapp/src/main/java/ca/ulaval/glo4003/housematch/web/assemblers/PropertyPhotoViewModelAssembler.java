package ca.ulaval.glo4003.housematch.web.assemblers;

import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyPhotoViewModel;

public class PropertyPhotoViewModelAssembler {

    public PropertyPhotoViewModel assemble(PropertyPhoto propertyPhoto) {
        PropertyPhotoViewModel viewModel = new PropertyPhotoViewModel();
        viewModel.setHashCode(propertyPhoto.hashCode());
        return viewModel;
    }

}
