package ca.ulaval.glo4003.housematch.spring.web.assemblers;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyDetailsFormViewModel;

public class PropertyDetailsFormViewModelAssemblerTest {

    private Property propertyMock;
    private PropertyDetails propertyDetailsMock;

    private PropertyDetailsFormViewModelAssembler assembler;

    @Before
    public void init() {
        propertyMock = mock(Property.class);
        propertyDetailsMock = mock(PropertyDetails.class);
        assembler = new PropertyDetailsFormViewModelAssembler();
    }

    @Test
    public void assemblesTheViewModelFromTheSpecifiedProperty() {
        when(propertyMock.getPropertyDetails()).thenReturn(propertyDetailsMock);
        PropertyDetailsFormViewModel viewModel = assembler.assembleFromProperty(propertyMock);
        assertSame(propertyDetailsMock, viewModel.getDetails());
    }
}
