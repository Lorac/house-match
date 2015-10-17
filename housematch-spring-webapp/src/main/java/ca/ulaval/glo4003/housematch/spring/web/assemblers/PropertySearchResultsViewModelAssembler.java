package ca.ulaval.glo4003.housematch.spring.web.assemblers;

import java.util.List;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertySearchResultsViewModel;

public class PropertySearchResultsViewModelAssembler {

    public PropertySearchResultsViewModel assembleFromPropertyList(List<Property> properties) {
        PropertySearchResultsViewModel viewModel = new PropertySearchResultsViewModel();
        viewModel.setProperties(properties);
        return viewModel;
    }
}
