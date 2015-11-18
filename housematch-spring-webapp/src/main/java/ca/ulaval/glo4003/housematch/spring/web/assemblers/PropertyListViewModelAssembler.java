package ca.ulaval.glo4003.housematch.spring.web.assemblers;

import java.util.List;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyListViewModel;

public class PropertyListViewModelAssembler {

    public PropertyListViewModel assembleFromPropertyList(List<Property> properties) {
        PropertyListViewModel viewModel = new PropertyListViewModel();
        viewModel.setProperties(properties);
        return viewModel;
    }
}
