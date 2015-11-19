package ca.ulaval.glo4003.housematch.web.assemblers;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.web.assemblers.PropertyViewModelAssembler;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyViewModel;

public class PropertyViewModelAssemblerTest {

    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.SINGLE_FAMILY_HOME;
    private static final BigDecimal SAMPLE_SELLING_PRICE = BigDecimal.valueOf(523.5);

    private Property propertyMock;
    private PropertyDetails propertyDetailsMock;
    private Address addressMock;

    private PropertyViewModelAssembler assembler;

    @Before
    public void init() {
        initMocks();
        initStubs();
        assembler = new PropertyViewModelAssembler();
    }

    private void initMocks() {
        propertyMock = mock(Property.class);
        propertyDetailsMock = mock(PropertyDetails.class);
        addressMock = mock(Address.class);
    }

    private void initStubs() {
        when(propertyMock.getPropertyType()).thenReturn(SAMPLE_PROPERTY_TYPE);
        when(propertyMock.getSellingPrice()).thenReturn(SAMPLE_SELLING_PRICE);
        when(propertyMock.getAddress()).thenReturn(addressMock);
        when(propertyMock.getPropertyDetails()).thenReturn(propertyDetailsMock);
    }

    @Test
    public void assemblesTheViewModelFromTheSpecifiedProperty() {
        PropertyViewModel viewModel = assembler.assembleFromProperty(propertyMock);

        assertSame(SAMPLE_PROPERTY_TYPE, viewModel.getPropertyType());
        assertSame(SAMPLE_SELLING_PRICE, viewModel.getSellingPrice());
        assertSame(addressMock, viewModel.getAddress());
        assertSame(propertyDetailsMock, viewModel.getPropertyDetails());
    }

}
