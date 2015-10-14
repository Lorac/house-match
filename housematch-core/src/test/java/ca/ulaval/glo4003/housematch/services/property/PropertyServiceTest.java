package ca.ulaval.glo4003.housematch.services.property;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyRepository;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.validators.property.PropertyListingCreationValidationException;
import ca.ulaval.glo4003.housematch.validators.property.PropertyListingCreationValidator;
import ca.ulaval.glo4003.housematch.validators.property.PropertyListingUpdateValidator;

public class PropertyServiceTest {
    private static final BigDecimal SAMPLE_SELLING_PRICE = BigDecimal.valueOf(5541);
    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.FARM;

    private PropertyRepository propertyRepositoryMock;
    private UserRepository userRepositoryMock;
    private PropertyListingCreationValidator propertyListingCreationValidatorMock;
    private PropertyListingUpdateValidator propertyListingUpdateValidatorMock;
    private User userMock;
    private Address addressMock;
    private Property propertyMock;
    private PropertyDetails propertyDetailsMock;

    private PropertyService propertyService;

    @Before
    public void init() throws Exception {
        initMocks();
        propertyService = new PropertyService(propertyRepositoryMock, userRepositoryMock,
                propertyListingCreationValidatorMock, propertyListingUpdateValidatorMock);
    }

    private void initMocks() {
        userRepositoryMock = mock(UserRepository.class);
        propertyRepositoryMock = mock(PropertyRepository.class);
        userMock = mock(User.class);
        addressMock = mock(Address.class);
        propertyListingCreationValidatorMock = mock(PropertyListingCreationValidator.class);
        propertyListingUpdateValidatorMock = mock(PropertyListingUpdateValidator.class);
        propertyMock = mock(Property.class);
        propertyDetailsMock = mock(PropertyDetails.class);
    }

    @Test
    public void propertyListingCreationPersistsNewPropertyToRepository() throws Exception {
        createPropertyListing();
        verify(propertyRepositoryMock).persist(any(Property.class));
    }

    @Test
    public void propertyListingCreationPushesUserUpdateToRepository() throws Exception {
        createPropertyListing();
        verify(userRepositoryMock).update(userMock);
    }

    @Test
    public void propertyListingCreationCallsThePropertyListingCreationValidator() throws Exception {
        createPropertyListing();
        verify(propertyListingCreationValidatorMock).validatePropertyListingCreation(SAMPLE_PROPERTY_TYPE, addressMock,
                SAMPLE_SELLING_PRICE);
    }

    @Test(expected = PropertyServiceException.class)
    public void propertyListingCreationThrowsPropertyServiceExceptionOnPropertyListingCreationValidationException()
            throws Exception {
        doThrow(new PropertyListingCreationValidationException()).when(propertyListingCreationValidatorMock)
                .validatePropertyListingCreation(SAMPLE_PROPERTY_TYPE, addressMock, SAMPLE_SELLING_PRICE);
        createPropertyListing();
    }

    @Test(expected = PropertyServiceException.class)
    public void propertyListingCreationThrowsUserServiceExceptionOnUserAlreadyExistsException() throws Exception {
        doThrow(new PropertyAlreadyExistsException()).when(propertyRepositoryMock).persist(any(Property.class));
        createPropertyListing();
    }

    @Test
    public void propertyUpdateCallsThePropertyListingUpdateValidator() throws Exception {
        propertyService.updateProperty(propertyMock, propertyDetailsMock);
        verify(propertyListingUpdateValidatorMock).validatePropertyListingUpdate(propertyDetailsMock);
    }

    @Test
    public void propertyUpdateSetsThePropertyDetailsOnThePropertyObject() throws Exception {
        propertyService.updateProperty(propertyMock, propertyDetailsMock);
        verify(propertyMock).setPropertyDetails(propertyDetailsMock);
    }

    @Test
    public void propertyUpdatePushesThePropertyUpdateInTheRepository() throws Exception {
        propertyService.updateProperty(propertyMock, propertyDetailsMock);
        verify(propertyRepositoryMock).update(propertyMock);
    }

    private void createPropertyListing() throws PropertyServiceException {
        propertyService.createPropertyListing(SAMPLE_PROPERTY_TYPE, addressMock, SAMPLE_SELLING_PRICE, userMock);
    }
}
