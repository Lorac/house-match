package ca.ulaval.glo4003.housematch.web.assemblers;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyListViewModel;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyViewModel;

public class PropertyListViewModelAssemblerTest {

    private PropertyListViewModelAssembler propertyListViewModelAssembler;
    private PropertyViewModelAssembler propertyViewModelAssemblerMock;
    private List<Property> propertyList = new ArrayList<>();
    private PropertyViewModel propertyViewModelMock;
    private Property propertyMock;

    @Before
    public void init() {
        initMocks();
        propertyList.add(propertyMock);
        propertyListViewModelAssembler = new PropertyListViewModelAssembler(propertyViewModelAssemblerMock);
    }

    private void initMocks() {
        propertyMock = mock(Property.class);
        propertyViewModelMock = mock(PropertyViewModel.class);
        propertyViewModelAssemblerMock = mock(PropertyViewModelAssembler.class);
    }

    @Test
    public void assemblesTheViewModelFromTheSpecifiedPropertyList() {
        when(propertyViewModelAssemblerMock.assemble(propertyMock, null)).thenReturn(propertyViewModelMock);
        PropertyListViewModel viewModel = propertyListViewModelAssembler.assembleFromPropertyList(propertyList);
        assertThat(viewModel.getPropertyViewModels(), contains(propertyViewModelMock));
    }
}
