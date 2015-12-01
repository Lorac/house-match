package ca.ulaval.glo4003.housematch.web.assemblers;

import static org.junit.Assert.assertEquals;
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
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyViewModel;

public class PropertyViewModelAssemblerTest {

    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.SINGLE_FAMILY_HOME;
    private static final BigDecimal SAMPLE_SELLING_PRICE = BigDecimal.valueOf(523.5);
    private static final Integer SAMPLE_VIEW_COUNT = 60;
    private static final Boolean SAMPLE_BOOLEAN = Boolean.TRUE;

    private Property propertyMock;
    private User userMock;
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
        userMock = mock(User.class);
        propertyDetailsMock = mock(PropertyDetails.class);
        addressMock = mock(Address.class);
    }

    private void initStubs() {
        when(propertyMock.getPropertyType()).thenReturn(SAMPLE_PROPERTY_TYPE);
        when(propertyMock.getAddress()).thenReturn(addressMock);
        when(propertyMock.getSellingPrice()).thenReturn(SAMPLE_SELLING_PRICE);
        when(propertyMock.getPropertyDetails()).thenReturn(propertyDetailsMock);
        when(propertyMock.getViewCount()).thenReturn(SAMPLE_VIEW_COUNT);
        when(userMock.isPropertyFavorited(propertyMock)).thenReturn(SAMPLE_BOOLEAN);
    }

    @Test
    public void assemblesTheViewModelFromTheSpecifiedProperty() {
        PropertyViewModel viewModel = assembler.assemble(propertyMock, userMock);

        assertEquals(SAMPLE_PROPERTY_TYPE, viewModel.getPropertyType());
        assertEquals(addressMock, viewModel.getAddress());
        assertEquals(SAMPLE_SELLING_PRICE, viewModel.getSellingPrice());
        assertSame(propertyDetailsMock, viewModel.getPropertyDetails());
        assertEquals(propertyMock.hashCode(), viewModel.getHashCode());
        assertEquals(SAMPLE_VIEW_COUNT, viewModel.getViewCount());
        assertEquals(SAMPLE_BOOLEAN, viewModel.isPropertyAddedToFavorites());
    }

}
