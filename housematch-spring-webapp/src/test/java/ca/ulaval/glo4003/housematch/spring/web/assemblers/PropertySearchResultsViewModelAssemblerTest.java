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
    private static final List<PropertyViewModel> SAMPLE_PROPERTY_VIEW_MODEL_LIST = new ArrayList<PropertyViewModel>();

    private PropertySearchResultsViewModelAssembler assembler;
    
    private Property propertyMock;
    private PropertyViewModel propertyViewModelMock;
    private PropertyViewModelAssembler propertyViewModelAssemblerMock;

    @Before
    public void init() {
        propertyMock = mock(Property.class);
        propertyViewModelMock = mock(PropertyViewModel.class);
        propertyViewModelAssemblerMock = mock(PropertyViewModelAssembler.class);
        
        when(propertyViewModelAssemblerMock.assembleFromProperty(propertyMock)).thenReturn(propertyViewModelMock);
        assembler = new PropertySearchResultsViewModelAssembler(propertyViewModelAssemblerMock);
        
        SAMPLE_PROPERTY_LIST.add(propertyMock);
        SAMPLE_PROPERTY_VIEW_MODEL_LIST.add(propertyViewModelMock);
    }

    @Test
    public void assemblesTheViewModelFromTheSpecifiedPropertyList() {
    	PropertySearchResultsViewModel viewModel = assembler.assembleFromPropertyList(SAMPLE_PROPERTY_LIST);
    	assertEquals(SAMPLE_PROPERTY_VIEW_MODEL_LIST, viewModel.getPropertyViewModels());
    }
}
