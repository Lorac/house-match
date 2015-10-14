package ca.ulaval.glo4003.housematch.spring.web.assemblers;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyListingUpdateFormViewModel;

public class PropertyListingUpdateFormViewModelAssembler {

    public PropertyListingUpdateFormViewModel assembleFromProperty(Property property) {
        PropertyListingUpdateFormViewModel viewModel = new PropertyListingUpdateFormViewModel();
        viewModel.setDetails(property.getPropertyDetails());
        return viewModel;
    }
}
