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

    public PropertyPhotoListViewModel assembleFromCollection(Collection<PropertyPhoto> propertyPhotoCollection) {
        PropertyPhotoListViewModel propertyPhotoListViewModel = new PropertyPhotoListViewModel();
        List<PropertyPhotoViewModel> propertyPhotoViewModelList = assemblePropertyPhotoViewModelListFromCollection(propertyPhotoCollection);
        propertyPhotoListViewModel.setPropertyPhotoViewModels(propertyPhotoViewModelList);
        return propertyPhotoListViewModel;
    }

    private List<PropertyPhotoViewModel> assemblePropertyPhotoViewModelListFromCollection(
            Collection<PropertyPhoto> propertyPhotoCollection) {
        List<PropertyPhotoViewModel> propertyPhotoViewModelList = new ArrayList<>();
        for (PropertyPhoto propertyPhoto : propertyPhotoCollection) {
            propertyPhotoViewModelList.add(propertyPhotoViewModelAssembler.assemble(propertyPhoto));
        }
        return propertyPhotoViewModelList;
    }

}
