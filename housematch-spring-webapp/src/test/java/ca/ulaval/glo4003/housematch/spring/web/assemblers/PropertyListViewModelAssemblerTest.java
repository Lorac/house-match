package ca.ulaval.glo4003.housematch.spring.web.assemblers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyListViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyViewModel;

public class PropertyListViewModelAssemblerTest {

    private static final List<Property> SAMPLE_PROPERTY_LIST = new ArrayList<>();
    private static final List<PropertyViewModel> SAMPLE_PROPERTY_VIEW_MODEL_LIST = new ArrayList<>();

    private PropertyListViewModelAssembler assembler;
    private PropertyViewModelAssembler propertyViewModelAssemblerMock;

    @Before
    public void init() {
        propertyViewModelAssemblerMock = mock(PropertyViewModelAssembler.class);
        when(propertyViewModelAssemblerMock.assembleFromPropertyList(SAMPLE_PROPERTY_LIST)).thenReturn(SAMPLE_PROPERTY_VIEW_MODEL_LIST);
        assembler = new PropertyListViewModelAssembler(propertyViewModelAssemblerMock);
    }

    @Test
    public void assemblesTheViewModelFromTheSpecifiedPropertyList() {
        PropertyListViewModel viewModel = assembler.assembleFromPropertyList(SAMPLE_PROPERTY_LIST);
        assertEquals(SAMPLE_PROPERTY_VIEW_MODEL_LIST, viewModel.getPropertyViewModels());
    }
}
