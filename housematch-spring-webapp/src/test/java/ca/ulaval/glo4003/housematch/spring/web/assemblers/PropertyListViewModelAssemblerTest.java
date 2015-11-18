package ca.ulaval.glo4003.housematch.spring.web.assemblers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyListViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyViewModel;

public class PropertyListViewModelAssemblerTest {

    private static List<Property> SAMPLE_PROPERTY_LIST;
    private static List<PropertyViewModel> SAMPLE_PROPERTY_VIEW_MODEL_LIST;

    private PropertyListViewModelAssembler assembler;

    private static Property propertyMock;
    private PropertyViewModel propertyViewModelMock;
    private PropertyViewModelAssembler propertyViewModelAssemblerMock;

    @Before
    public void init() {
        initMocks();
        stubMethods();
        assembler = new PropertySearchResultsViewModelAssembler(propertyViewModelAssemblerMock);
        SAMPLE_PROPERTY_LIST = Collections.unmodifiableList(Arrays.asList(propertyMock));
        SAMPLE_PROPERTY_VIEW_MODEL_LIST = Collections.unmodifiableList(Arrays.asList(propertyViewModelMock));
    }

    private void initMocks() {
        propertyMock = mock(Property.class);
        propertyViewModelMock = mock(PropertyViewModel.class);
        propertyViewModelAssemblerMock = mock(PropertyViewModelAssembler.class);

    }
    
    private void stubMethods() {
        when(propertyViewModelAssemblerMock.assembleFromProperty(propertyMock)).thenReturn(propertyViewModelMock);
    }

    @Test
    public void assemblesTheViewModelFromTheSpecifiedPropertyList() {
        PropertyListViewModel viewModel = assembler.assembleFromPropertyList(SAMPLE_PROPERTY_LIST);
        assertEquals(SAMPLE_PROPERTY_VIEW_MODEL_LIST, viewModel.getPropertyViewModels());
    }
}
