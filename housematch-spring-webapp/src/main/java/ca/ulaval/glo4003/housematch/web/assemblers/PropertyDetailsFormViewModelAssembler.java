package ca.ulaval.glo4003.housematch.web.assemblers;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyDetailsFormViewModel;

public class PropertyDetailsFormViewModelAssembler {

    public PropertyDetailsFormViewModel assembleFromProperty(Property property) {
        PropertyDetailsFormViewModel viewModel = new PropertyDetailsFormViewModel();
        viewModel.setDetails(property.getPropertyDetails());
        return viewModel;
    }
}
