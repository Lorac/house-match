package ca.ulaval.glo4003.housematch.spring.web.assemblers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertySearchResultsViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyViewModel;

public class PropertySearchResultsViewModelAssemblerTest {

    private static final List<Property> SAMPLE_PROPERTY_LIST = new ArrayList<Property>();
    private static final List<PropertyViewModel> samplePropertyViewModelList = new ArrayList<PropertyViewModel>();

    private PropertySearchResultsViewModelAssembler assembler;

    private Property propertyMock;
    private PropertyViewModel propertyViewModelMock;
    private PropertyViewModelAssembler propertyViewModelAssemblerMock;

    @Before
    public void init() {
        initMocks();
        stubMethods();
        assembler = new PropertySearchResultsViewModelAssembler(propertyViewModelAssemblerMock);
        initPropertyList();

    }

    private void initMocks() {
        propertyMock = mock(Property.class);
        propertyViewModelMock = mock(PropertyViewModel.class);
        propertyViewModelAssemblerMock = mock(PropertyViewModelAssembler.class);

    }
    
    private void stubMethods() {
        when(propertyViewModelAssemblerMock.assembleFromProperty(propertyMock)).thenReturn(propertyViewModelMock);
    }
    
    private void initPropertyList() {
        SAMPLE_PROPERTY_LIST.add(propertyMock);
        samplePropertyViewModelList.add(propertyViewModelMock);
    }

    @Test
    public void assemblesTheViewModelFromTheSpecifiedPropertyList() {
        PropertySearchResultsViewModel viewModel = assembler.assembleFromPropertyList(SAMPLE_PROPERTY_LIST);
        assertEquals(samplePropertyViewModelList, viewModel.getPropertyViewModels());
    }
}
