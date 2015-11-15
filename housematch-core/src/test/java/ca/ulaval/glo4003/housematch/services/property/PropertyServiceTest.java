package ca.ulaval.glo4003.housematch.services.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertySorter;
import ca.ulaval.glo4003.housematch.domain.property.PropertyAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyFactory;
import ca.ulaval.glo4003.housematch.domain.property.PropertyRepository;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.validators.property.PropertyCreationValidationException;
import ca.ulaval.glo4003.housematch.validators.property.PropertyCreationValidator;
import ca.ulaval.glo4003.housematch.validators.property.PropertyDetailsValidationException;
import ca.ulaval.glo4003.housematch.validators.property.PropertyDetailsValidator;

public class PropertyServiceTest {
    private static final BigDecimal SAMPLE_SELLING_PRICE = BigDecimal.valueOf(5541);
    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.FARM;
    private static final List<Property> SAMPLE_PROPERTY_LIST = new ArrayList<Property>();

    private PropertyFactory propertyFactoryMock;
    private PropertyRepository propertyRepositoryMock;
    private UserRepository userRepositoryMock;
    private PropertyCreationValidator propertyCreationValidatorMock;
    private PropertyDetailsValidator propertyDetailsValidatorMock;
    private User userMock;
    private Address addressMock;
    private Property propertyMock;
    private PropertyDetails propertyDetailsMock;
    private PropertySorter propertySorter;

    private PropertyService propertyService;

    @Before
    public void init() throws Exception {
        initMocks();
        stubMethods();
        propertyService = new PropertyService(propertyFactoryMock, propertyRepositoryMock, userRepositoryMock,
                propertyCreationValidatorMock, propertyDetailsValidatorMock, propertySorter);
    }

    private void initMocks() {
        propertyFactoryMock = mock(PropertyFactory.class);
        userRepositoryMock = mock(UserRepository.class);
        propertyRepositoryMock = mock(PropertyRepository.class);
        userMock = mock(User.class);
        addressMock = mock(Address.class);
        propertyCreationValidatorMock = mock(PropertyCreationValidator.class);
        propertyDetailsValidatorMock = mock(PropertyDetailsValidator.class);
        propertyMock = mock(Property.class);
        propertyDetailsMock = mock(PropertyDetails.class);
        propertySorter = mock(PropertySorter.class);
    }

    private void stubMethods() {
        when(propertyFactoryMock.createProperty(any(PropertyType.class), any(Address.class), any(BigDecimal.class)))
                .thenReturn(propertyMock);
    }

    @Test
    public void propertyCreationPersistsNewPropertyToRepository() throws Exception {
        createProperty();
        verify(propertyRepositoryMock).persist(any(Property.class));
    }

    @Test
    public void propertyCreationUpdatesTheUserInTheRepository() throws Exception {
        createProperty();
        verify(userRepositoryMock).update(userMock);
    }

    @Test
    public void propertyCreationCallsThePropertyCreationValidator() throws Exception {
        createProperty();
        verify(propertyCreationValidatorMock).validatePropertyCreation(SAMPLE_PROPERTY_TYPE, addressMock, SAMPLE_SELLING_PRICE);
    }

    @Test(expected = PropertyServiceException.class)
    public void propertyCreationThrowsPropertyServiceExceptionOnPropertyCreationValidationException() throws Exception {
        doThrow(new PropertyCreationValidationException()).when(propertyCreationValidatorMock)
                .validatePropertyCreation(SAMPLE_PROPERTY_TYPE, addressMock, SAMPLE_SELLING_PRICE);
        createProperty();
    }

    @Test(expected = PropertyServiceException.class)
    public void propertyCreationThrowsUserServiceExceptionOnUserAlreadyExistsException() throws Exception {
        doThrow(new PropertyAlreadyExistsException()).when(propertyRepositoryMock).persist(any(Property.class));
        createProperty();
    }

    @Test
    public void updatingPropertyDetailsCallsThePropertyDetailsValidator() throws Exception {
        propertyService.updatePropertyDetails(propertyMock, propertyDetailsMock);
        verify(propertyDetailsValidatorMock).validatePropertyDetails(propertyDetailsMock);
    }

    @Test
    public void updatingPropertyDetailsSetsThePropertyDetailsOnThePropertyObject() throws Exception {
        propertyService.updatePropertyDetails(propertyMock, propertyDetailsMock);
        verify(propertyMock).setPropertyDetails(propertyDetailsMock);
    }

    @Test
    public void updatingPropertyDetailsUpdatesThePropertyInTheRepository() throws Exception {
        propertyService.updatePropertyDetails(propertyMock, propertyDetailsMock);
        verify(propertyRepositoryMock).update(propertyMock);
    }

    @Test(expected = PropertyServiceException.class)
    public void updatingPropertyDetailsThrowsPropertyServiceExceptionOnPropertyDetailsValidationException() throws Exception {
        doThrow(new PropertyDetailsValidationException()).when(propertyDetailsValidatorMock).validatePropertyDetails(propertyDetailsMock);
        propertyService.updatePropertyDetails(propertyMock, propertyDetailsMock);
    }

    @Test
    public void gettingPropertiesGetsAllPropertiesFromThePropertyRepository() {
        propertyService.getProperties();
        verify(propertyRepositoryMock).getAll();
    }

    @Test
    public void gettingPropertiesReturnsListContainingAllTheProperties() {
        when(propertyRepositoryMock.getAll()).thenReturn(SAMPLE_PROPERTY_LIST);
        List<Property> returnedPropertyList = propertyService.getProperties();
        assertSame(SAMPLE_PROPERTY_LIST, returnedPropertyList);
    }

    @Test
    public void gettingPropertiesInChronologicalOrderReturnsAListOfSortedProperties() {
        List<Property> propertyList = propertyService.getPropertiesInChronologicalOrder();
        
        verify(propertyRepositoryMock).getAll();
        verify(propertySorter).sortByDateInAscendingOrder(SAMPLE_PROPERTY_LIST);
        assertEquals(SAMPLE_PROPERTY_LIST, propertyList);
    }

    @Test
    public void gettingPropertiesInReverseChronologicalOrderFilterAllPropertiesInRepositories() {
        List<Property> propertyList = propertyService.getPropertiesInReverseChronologicalOrder();
        
        verify(propertyRepositoryMock).getAll();
        verify(propertySorter).sortByDateInDescendingOrder(SAMPLE_PROPERTY_LIST);
        assertEquals(SAMPLE_PROPERTY_LIST, propertyList);
    }

    @Test
    public void gettingPropertyByHashCodeGetsThePropertyFromTheSpecifiedHashCode() throws Exception {
        propertyService.getPropertyByHashCode(propertyMock.hashCode());
        verify(propertyRepositoryMock).getByHashCode(propertyMock.hashCode());
    }

    private void createProperty() throws PropertyServiceException {
        propertyService.createProperty(SAMPLE_PROPERTY_TYPE, addressMock, SAMPLE_SELLING_PRICE, userMock);
    }
}
