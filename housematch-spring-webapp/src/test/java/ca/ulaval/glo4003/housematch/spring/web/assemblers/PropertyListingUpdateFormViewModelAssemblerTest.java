package ca.ulaval.glo4003.housematch.spring.web.assemblers;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyListingUpdateFormViewModel;

public class PropertyListingUpdateFormViewModelAssemblerTest {

    private Property propertyMock;
    private PropertyDetails propertyDetailsMock;

    private PropertyListingUpdateFormViewModelAssembler assembler;

    @Before
    public void init() {
        propertyMock = mock(Property.class);
        propertyDetailsMock = mock(PropertyDetails.class);
        assembler = new PropertyListingUpdateFormViewModelAssembler();
    }

    @Test
    public void assemblesTheViewModelFromTheSpecifiedProperty() {
        when(propertyMock.getPropertyDetails()).thenReturn(propertyDetailsMock);
        PropertyListingUpdateFormViewModel viewModel = assembler.assembleFromProperty(propertyMock);
        assertSame(propertyDetailsMock, viewModel.getDetails());
    }
}
