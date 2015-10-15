package ca.ulaval.glo4003.housematch.services.property;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyRepository;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.validators.property.PropertyCreationValidationException;
import ca.ulaval.glo4003.housematch.validators.property.PropertyCreationValidator;
import ca.ulaval.glo4003.housematch.validators.property.PropertyDetailsValidator;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class PropertyServiceTest {
    private static final BigDecimal SAMPLE_SELLING_PRICE = BigDecimal.valueOf(5541);
    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.FARM;

    private PropertyRepository propertyRepositoryMock;
    private UserRepository userRepositoryMock;
    private PropertyCreationValidator propertyCreationValidatorMock;
    private PropertyDetailsValidator propertyDetailsValidatorMock;
    private User userMock;
    private Address addressMock;
    private Property propertyMock;
    private PropertyDetails propertyDetailsMock;

    private PropertyService propertyService;

    @Before
    public void init() throws Exception {
        initMocks();
        propertyService = new PropertyService(propertyRepositoryMock, userRepositoryMock, propertyCreationValidatorMock,
                propertyDetailsValidatorMock);
    }

    private void initMocks() {
        userRepositoryMock = mock(UserRepository.class);
        propertyRepositoryMock = mock(PropertyRepository.class);
        userMock = mock(User.class);
        addressMock = mock(Address.class);
        propertyCreationValidatorMock = mock(PropertyCreationValidator.class);
        propertyDetailsValidatorMock = mock(PropertyDetailsValidator.class);
        propertyMock = mock(Property.class);
        propertyDetailsMock = mock(PropertyDetails.class);
    }

    @Test
    public void propertyCreationPersistsNewPropertyToRepository() throws Exception {
        createProperty();
        verify(propertyRepositoryMock).persist(any(Property.class));
    }

    @Test
    public void propertyCreationPushesUserUpdateToRepository() throws Exception {
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
    public void propertyDetailsUpdateCallsThePropertyDetailsValidator() throws Exception {
        propertyService.updateProperty(propertyMock, propertyDetailsMock);
        verify(propertyDetailsValidatorMock).validatePropertyDetails(propertyDetailsMock);
    }

    @Test
    public void propertyDetailsUpdateSetsThePropertyDetailsOnThePropertyObject() throws Exception {
        propertyService.updateProperty(propertyMock, propertyDetailsMock);
        verify(propertyMock).setPropertyDetails(propertyDetailsMock);
    }

    @Test
    public void propertyDetailsUpdatePushesThePropertyInTheRepository() throws Exception {
        propertyService.updateProperty(propertyMock, propertyDetailsMock);
        verify(propertyRepositoryMock).update(propertyMock);
    }

    private void createProperty() throws PropertyServiceException {
        propertyService.createProperty(SAMPLE_PROPERTY_TYPE, addressMock, SAMPLE_SELLING_PRICE, userMock);
    }
}