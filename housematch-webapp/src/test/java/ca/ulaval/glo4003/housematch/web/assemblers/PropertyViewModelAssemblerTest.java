package ca.ulaval.glo4003.housematch.web.assemblers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyPhotoViewModel;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyViewModel;

public class PropertyViewModelAssemblerTest {

    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.SINGLE_FAMILY_HOME;
    private static final BigDecimal SAMPLE_SELLING_PRICE = BigDecimal.valueOf(523.5);
    private static final Integer SAMPLE_VIEW_COUNT = 60;
    private static final Boolean SAMPLE_BOOLEAN = Boolean.TRUE;

    private PropertyPhotoViewModelAssembler propertyPhotoViewModelAssemblerMock;
    private PropertyPhotoViewModel propertyPhotoViewModelMock;
    private Property propertyMock;
    private PropertyPhoto propertyPhotoMock;
    private PropertyDetails propertyDetailsMock;
    private User userMock;
    private Address addressMock;

    private List<PropertyPhotoViewModel> propertyPhotoViewModels = new ArrayList<>();
    private List<PropertyPhoto> propertyPhotos = new ArrayList<>();
    private List<Property> properties = new ArrayList<>();
    private PropertyViewModelAssembler assembler;

    @Before
    public void init() {
        initMocks();
        initStubs();
        properties.add(propertyMock);
        assembler = new PropertyViewModelAssembler(propertyPhotoViewModelAssemblerMock);
    }

    private void initMocks() {
        propertyPhotoViewModelAssemblerMock = mock(PropertyPhotoViewModelAssembler.class);
        propertyPhotoViewModelMock = mock(PropertyPhotoViewModel.class);
        propertyMock = mock(Property.class);
        propertyPhotoMock = mock(PropertyPhoto.class);
        propertyDetailsMock = mock(PropertyDetails.class);
        userMock = mock(User.class);
        addressMock = mock(Address.class);
    }

    private void initStubs() {
        when(propertyPhotoViewModelAssemblerMock.assemble(propertyPhotoMock)).thenReturn(propertyPhotoViewModelMock);
        when(propertyPhotoViewModelAssemblerMock.assemble(propertyPhotos)).thenReturn(propertyPhotoViewModels);
        when(propertyMock.getPropertyType()).thenReturn(SAMPLE_PROPERTY_TYPE);
        when(propertyMock.getAddress()).thenReturn(addressMock);
        when(propertyMock.getSellingPrice()).thenReturn(SAMPLE_SELLING_PRICE);
        when(propertyMock.getPropertyDetails()).thenReturn(propertyDetailsMock);
        when(propertyMock.getViewCount()).thenReturn(SAMPLE_VIEW_COUNT);
        when(propertyMock.getMainPhoto()).thenReturn(Optional.of(propertyPhotoMock));
        when(userMock.isPropertyFavorited(propertyMock)).thenReturn(SAMPLE_BOOLEAN);
    }

    @Test
    public void assemblesTheViewModelFromTheSpecifiedProperty() {
        PropertyViewModel viewModel = assembler.assemble(propertyMock, Optional.of(userMock));
        compareViewModelToExpectedValues(viewModel);
    }

    @Test
    public void assemblesTheViewModelFromTheSpecifiedProperties() {
        List<PropertyViewModel> propertyViewModels = assembler.assemble(properties, Optional.of(userMock));
        compareViewModelToExpectedValues(propertyViewModels.get(0));
    }

    @Test
    public void setsThePropertyFavoritedFlagToNullWhenTheUserIsNotPresent() {
        PropertyViewModel viewModel = assembler.assemble(propertyMock, Optional.empty());
        assertNull(viewModel.isPropertyFavorited());
    }

    @Test
    public void setsTheMainPhotoToNullWhenTheMainPhotoIsNotPresent() {
        when(propertyMock.getMainPhoto()).thenReturn(Optional.empty());
        PropertyViewModel viewModel = assembler.assemble(propertyMock, Optional.of(userMock));
        assertNull(viewModel.getMainPhotoViewModel());
    }

    @Test
    public void retrievesOnlyTheApprovedPhotosWhenUserIsNotPresent() {
        assembler.assemble(propertyMock, Optional.empty());
        verify(propertyMock).getApprovedPhotos();
    }

    @Test
    public void retrievesOnlyTheApprovedPhotosWhenUserIsNotASeller() {
        when(userMock.hasRole(UserRole.SELLER)).thenReturn(false);
        assembler.assemble(propertyMock, Optional.of(userMock));
        verify(propertyMock).getApprovedPhotos();
    }

    @Test
    public void retrievesAllThePhotosWhenUserIsASeller() {
        when(userMock.hasRole(UserRole.SELLER)).thenReturn(true);
        assembler.assemble(propertyMock, Optional.of(userMock));
        verify(propertyMock).getPhotos();
    }

    private void compareViewModelToExpectedValues(PropertyViewModel viewModel) {
        assertEquals(SAMPLE_PROPERTY_TYPE, viewModel.getPropertyType());
        assertEquals(addressMock, viewModel.getAddress());
        assertEquals(SAMPLE_SELLING_PRICE, viewModel.getSellingPrice());
        assertSame(propertyDetailsMock, viewModel.getPropertyDetails());
        assertEquals(propertyMock.hashCode(), viewModel.getPropertyHashCode());
        assertEquals(SAMPLE_VIEW_COUNT, viewModel.getViewCount());
        assertEquals(SAMPLE_BOOLEAN, viewModel.isPropertyFavorited());
        assertEquals(propertyPhotoViewModels, viewModel.getPhotoViewModels());
        assertEquals(propertyPhotoViewModelMock, viewModel.getMainPhotoViewModel());
    }
}
