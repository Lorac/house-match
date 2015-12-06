package ca.ulaval.glo4003.housematch.web.assemblers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyPhotoListViewModel;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyPhotoViewModel;

public class PropertyPhotoListViewModelAssembler {

    PropertyPhotoViewModelAssembler propertyPhotoViewModelAssembler;

    public PropertyPhotoListViewModelAssembler(final PropertyPhotoViewModelAssembler propertyPhotoViewModelAssembler) {
        this.propertyPhotoViewModelAssembler = propertyPhotoViewModelAssembler;
    }

    public PropertyPhotoListViewModel assemble(Collection<PropertyPhoto> propertyPhotos) {
        PropertyPhotoListViewModel propertyPhotoListViewModel = new PropertyPhotoListViewModel();
        List<PropertyPhotoViewModel> propertyPhotoViewModels = assemblePropertyPhotoViewModels(propertyPhotos);
        propertyPhotoListViewModel.setPropertyPhotoViewModels(propertyPhotoViewModels);
        return propertyPhotoListViewModel;
    }

    private List<PropertyPhotoViewModel> assemblePropertyPhotoViewModels(
            Collection<PropertyPhoto> propertyPhotos) {
        List<PropertyPhotoViewModel> propertyPhotoViewModels = new ArrayList<>();
        for (PropertyPhoto propertyPhoto : propertyPhotos) {
            propertyPhotoViewModels.add(propertyPhotoViewModelAssembler.assemble(propertyPhoto));
        }
        return propertyPhotoViewModels;
    }

}
