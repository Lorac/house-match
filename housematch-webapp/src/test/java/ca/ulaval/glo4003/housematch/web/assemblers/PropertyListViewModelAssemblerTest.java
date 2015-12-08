package ca.ulaval.glo4003.housematch.web.assemblers;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyListViewModel;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyViewModel;

public class PropertyListViewModelAssemblerTest {

    private PropertyViewModelAssembler propertyViewModelAssemblerMock;
    private Property propertyMock;

    private PropertyListViewModelAssembler propertyListViewModelAssembler;
    private Collection<Property> properties = new ArrayList<>();
    private List<PropertyViewModel> propertyViewModels = new ArrayList<>();

    @Before
    public void init() {
        initMocks();
        properties.add(propertyMock);
        propertyListViewModelAssembler = new PropertyListViewModelAssembler(propertyViewModelAssemblerMock);
    }

    private void initMocks() {
        propertyMock = mock(Property.class);
        propertyViewModelAssemblerMock = mock(PropertyViewModelAssembler.class);
    }

    @Test
    public void assemblesTheViewModelFromTheSpecifiedProperties() {
        when(propertyViewModelAssemblerMock.assemble(properties, Optional.empty())).thenReturn(propertyViewModels);
        PropertyListViewModel viewModel = propertyListViewModelAssembler.assemble(properties);
        assertSame(viewModel.getPropertyViewModels(), propertyViewModels);
    }
}
