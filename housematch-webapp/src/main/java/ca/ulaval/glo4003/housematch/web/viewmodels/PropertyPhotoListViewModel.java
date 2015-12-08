package ca.ulaval.glo4003.housematch.web.viewmodels;

import java.util.List;

public class PropertyPhotoListViewModel extends ViewModel {

    public static final String NAME = "propertyPhotoListViewModel";

    private List<PropertyPhotoViewModel> propertyPhotoViewModels;

    @Override
    public String getName() {
        return NAME;
    }

    public List<PropertyPhotoViewModel> getPropertyPhotoViewModels() {
        return propertyPhotoViewModels;
    }

    public void setPropertyPhotoViewModels(List<PropertyPhotoViewModel> propertyPhotoViewModels) {
        this.propertyPhotoViewModels = propertyPhotoViewModels;
    }

}
