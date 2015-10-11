package ca.ulaval.glo4003.housematch.services.property;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyListingDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyRepository;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserPropertyNotListedException;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.validators.property.PropertyListingCreationValidationException;
import ca.ulaval.glo4003.housematch.validators.property.PropertyListingCreationValidator;

public class PropertyServiceTest {
    private static final BigDecimal SAMPLE_SELLING_PRICE = BigDecimal.valueOf(5541);
    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.FARM;
    private static final Integer SAMPLE_PROPERTY_HASHCODE = Integer.valueOf(2222);
    private static final String SAMPLE_PROPERTY_INFO = "This is a big house";

    private PropertyRepository propertyRepositoryMock;
    private UserRepository userRepositoryMock;
    private PropertyListingCreationValidator propertyListingCreationValidatorMock;
    private User userMock;
    private Address addressMock;
    private Property propertyMock;
    
    private PropertyService propertyService;

    @Before
    public void init() throws Exception {
        initMocks();
        propertyService = new PropertyService(propertyRepositoryMock, userRepositoryMock,
                propertyListingCreationValidatorMock);
    }

    private void initMocks() {
        userRepositoryMock = mock(UserRepository.class);
        propertyRepositoryMock = mock(PropertyRepository.class);
        userMock = mock(User.class);
        addressMock = mock(Address.class);
        propertyListingCreationValidatorMock = mock(PropertyListingCreationValidator.class);
        propertyMock = mock(Property.class);
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
        verify(propertyListingCreationValidatorMock).validatePropertyListingCreation(SAMPLE_PROPERTY_TYPE,
                addressMock, SAMPLE_SELLING_PRICE);
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
    
    //TODO : update tests to reflect actual use of methods
    @Test
    public void propertyEditingChangesUpdatePropertyInRepository() throws Exception {
        when(propertyRepositoryMock.getByHashCode(SAMPLE_PROPERTY_HASHCODE)).thenReturn(propertyMock);

        propertyService.updateProperty(SAMPLE_PROPERTY_HASHCODE, new PropertyListingDetails(), userMock);
        verify(propertyRepositoryMock).update(propertyMock);
    }
    
    @Test
    public void propertyEditingChangesUpdatePropertyInUser() throws Exception   {
        when(propertyRepositoryMock.getByHashCode(SAMPLE_PROPERTY_HASHCODE)).thenReturn(propertyMock);
        
        propertyService.updateProperty(SAMPLE_PROPERTY_HASHCODE, new PropertyListingDetails(), userMock);
        verify(userMock).updateProperty(propertyMock);
    }

    private void createPropertyListing() throws PropertyServiceException {
        propertyService.createPropertyListing(SAMPLE_PROPERTY_TYPE, addressMock, SAMPLE_SELLING_PRICE, userMock);
    }
}
