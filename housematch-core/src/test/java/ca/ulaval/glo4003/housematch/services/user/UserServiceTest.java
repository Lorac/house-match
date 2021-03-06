package ca.ulaval.glo4003.housematch.services.user;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationSettings;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.user.UserFactory;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.validators.address.AddressValidationException;
import ca.ulaval.glo4003.housematch.validators.address.AddressValidator;
import ca.ulaval.glo4003.housematch.validators.user.UserRegistrationValidationException;
import ca.ulaval.glo4003.housematch.validators.user.UserRegistrationValidator;

public class UserServiceTest {
    private static final String SAMPLE_USERNAME = "username1";
    private static final String SAMPLE_EMAIL = "test@test.com";
    private static final String SAMPLE_INVALID_EMAIL = "asd@asd<!";
    private static final String SAMPLE_PASSWORD = "password1234";
    private static final UserRole SAMPLE_ROLE = UserRole.BUYER;
    private static final int SAMPLE_HASHCODE = new Object().hashCode();

    private UserFactory userFactoryMock;
    private UserRepository userRepositoryMock;
    private UserRegistrationValidator userRegistrationValidatorMock;
    private UserActivationService userActivationServiceMock;
    private AddressValidator addressValidatorMock;
    private Address addressMock;
    private User userMock;
    private Property propertyMock;
    private NotificationSettings notificationSettingsMock;

    private UserService userService;
    private List<User> users = new ArrayList<>();
    private Set<Property> properties = new HashSet<Property>();

    @Before
    public void init() throws Exception {
        initMocks();
        initStubs();
        userService = new UserService(userFactoryMock, userRepositoryMock, userActivationServiceMock, userRegistrationValidatorMock,
                addressValidatorMock);
        properties.add(propertyMock);
    }

    private void initMocks() throws UserNotFoundException {
        userFactoryMock = mock(UserFactory.class);
        userRepositoryMock = mock(UserRepository.class);
        userMock = mock(User.class);
        propertyMock = mock(Property.class);
        notificationSettingsMock = mock(NotificationSettings.class);
        userActivationServiceMock = mock(UserActivationService.class);
        userRegistrationValidatorMock = mock(UserRegistrationValidator.class);
        addressValidatorMock = mock(AddressValidator.class);
        addressMock = mock(Address.class);
    }

    private void initStubs() throws UserNotFoundException {
        when(userFactoryMock.createUser(anyString(), anyString(), anyString(), any(UserRole.class))).thenReturn(userMock);
        when(userRepositoryMock.getByUsername(SAMPLE_USERNAME)).thenReturn(userMock);
        when(userMock.getFavoriteProperties()).thenReturn(properties);
    }

    @Test
    public void gettingUserByLoginCredentialsValidatesPasswordFromTheUserObject() throws Exception {
        userService.getUserByLoginCredentials(SAMPLE_USERNAME, SAMPLE_PASSWORD);
        verify(userMock).validatePassword(SAMPLE_PASSWORD);
    }

    @Test
    public void gettingUserByLoginCredentialsRetrievesUserByUsernameFromRepository() throws Exception {
        userService.getUserByLoginCredentials(SAMPLE_USERNAME, SAMPLE_PASSWORD);
        verify(userRepositoryMock).getByUsername(SAMPLE_USERNAME);
    }

    @Test
    public void gettingUserByLoginCredentialsReturnsTheUser() throws Exception {
        User user = userService.getUserByLoginCredentials(SAMPLE_USERNAME, SAMPLE_PASSWORD);
        assertSame(userMock, user);
    }

    @Test(expected = UserServiceException.class)
    public void gettingUserByLoginCredentialsThrowsUserServiceExceptionOnUserNotFoundException() throws Exception {
        doThrow(new UserNotFoundException()).when(userRepositoryMock).getByUsername(SAMPLE_USERNAME);
        userService.getUserByLoginCredentials(SAMPLE_USERNAME, SAMPLE_PASSWORD);
    }

    @Test
    public void gettingUsersGetsAlltheUsersFromTheRepository() {
        when(userRepositoryMock.getAll()).thenReturn(users);
        List<User> returnedUsers = userService.getUsers();
        assertSame(users, returnedUsers);
    }

    @Test
    public void userRegistrationPersistsNewUserToRepository() throws Exception {
        registerUser();
        verify(userRepositoryMock).persist(any(User.class));
    }

    @Test
    public void userRegistrationCallsTheUserCreationValidator() throws Exception {
        registerUser();
        verify(userRegistrationValidatorMock).validateUserRegistration(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
    }

    @Test
    public void userRegistrationBeginsTheActivationProcess() throws Exception {
        registerUser();
        verify(userActivationServiceMock).beginActivation(any(User.class));
    }

    @Test(expected = UserServiceException.class)
    public void userRegistrationThrowsUserServiceExceptionOnUserRegistrationValidationException() throws Exception {
        doThrow(new UserRegistrationValidationException()).when(userRegistrationValidatorMock).validateUserRegistration(SAMPLE_USERNAME,
                SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        registerUser();
    }

    @Test(expected = UserServiceException.class)
    public void userRegistrationThrowsUserServiceExceptionOnUserAlreadyExistsException() throws Exception {
        doThrow(new UserAlreadyExistsException()).when(userRepositoryMock).persist(any(User.class));
        registerUser();
    }

    @Test(expected = UserServiceException.class)
    public void userRegistrationThrowsUserServiceExceptionOnUserActivationServiceException() throws Exception {
        doThrow(new UserActivationServiceException()).when(userActivationServiceMock).beginActivation(any(User.class));
        registerUser();
    }

    @Test
    public void updatingUserContactInformationUpdatesTheEmailFromTheUserObject() throws Exception {
        userService.updateUserContactInformation(userMock, addressMock, SAMPLE_EMAIL);
        verify(userMock).updateEmail(SAMPLE_EMAIL);
    }

    @Test
    public void updatingUserContactInformationWithNewEmailBeginsTheUserActivationProcess() throws Exception {
        userService.updateUserContactInformation(userMock, addressMock, SAMPLE_EMAIL);
        verify(userActivationServiceMock).beginActivation(userMock);
    }

    @Test(expected = UserServiceException.class)
    public void updatingUserContactInformationUsingInvalidEmailThrowsUserServiceException() throws Exception {
        userService.updateUserContactInformation(userMock, addressMock, SAMPLE_INVALID_EMAIL);
    }

    @Test
    public void updatingUserContactInformationWithTheSameEmailDoesNotBeginTheUserActivationProcess() throws Exception {
        when(userMock.getEmail()).thenReturn(SAMPLE_EMAIL);
        userService.updateUserContactInformation(userMock, addressMock, SAMPLE_EMAIL);
        verify(userActivationServiceMock, never()).beginActivation(userMock);
    }

    @Test
    public void updatingUserContactInformationsPushesUserUpdateToRepository() throws Exception {
        userService.updateUserContactInformation(userMock, addressMock, SAMPLE_EMAIL);
        verify(userRepositoryMock).update(userMock);
    }

    @Test
    public void updatingUserContactInformationsValidatesTheAddress() throws Exception {
        userService.updateUserContactInformation(userMock, addressMock, SAMPLE_EMAIL);
        verify(addressValidatorMock).validateAddress(addressMock);
    }

    @Test
    public void updatingUserContactInformationsSetsTheNewAddressInTheUserObject() throws Exception {
        userService.updateUserContactInformation(userMock, addressMock, SAMPLE_EMAIL);
        verify(userMock).setAddress(addressMock);
    }

    @Test(expected = UserServiceException.class)
    public void updatingUserContactInformationsThrowsUserServiceExceptionOnUserActivationServiceException() throws Exception {
        doThrow(new UserActivationServiceException()).when(userActivationServiceMock).beginActivation(userMock);
        userService.updateUserContactInformation(userMock, addressMock, SAMPLE_EMAIL);
    }

    @Test(expected = UserServiceException.class)
    public void updatingUserContactInformationsThrowsUserServiceExceptionOnAddressValidationException() throws Exception {
        doThrow(new AddressValidationException()).when(addressValidatorMock).validateAddress(addressMock);
        userService.updateUserContactInformation(userMock, addressMock, SAMPLE_EMAIL);
    }

    @Test
    public void gettingPropertiesorSaleReturnsThePropertiesForSaleOfTheUser() throws Exception {
        when(userMock.getPropertiesForSale()).thenReturn(properties);
        Set<Property> returnedProperties = userService.getPropertiesForSale(userMock);
        assertSame(properties, returnedProperties);
    }

    @Test
    public void gettingPropertyForSaleByHashCodeReturnsThePropertyFromTheSpecifiedHashCode() throws Exception {
        when(userMock.getPropertyForSaleByHashCode(SAMPLE_HASHCODE)).thenReturn(propertyMock);
        Property property = userService.getPropertyForSaleByHashCode(userMock, SAMPLE_HASHCODE);
        assertSame(propertyMock, property);
    }

    @Test
    public void gettingPubliclyRegistrableUserRolesReturnsAListOfPubliclyRegistrableUserRoles() {
        List<UserRole> userRoles = userService.getPubliclyRegistrableUserRoles();
        assertFalse(userRoles.isEmpty());
        userRoles.stream().forEach(u -> assertTrue(u.isPubliclyRegistrable()));
    }

    @Test
    public void applyingTheUserStatusPolicyAppliesTheUserStatusPolicyToUsers() {
        when(userRepositoryMock.getAll()).thenReturn(Arrays.asList(userMock));
        userService.applyUserStatusPolicy();
        verify(userMock).applyUserStatusPolicy();
    }

    @Test
    public void addingFavoritePropertyToUserAddsThePropertyToTheSpecifiedUser() {
        userService.addFavoritePropertyToUser(userMock, propertyMock);
        verify(userMock).addPropertyToFavorites(propertyMock);
    }

    @Test
    public void addingFavoritePropertyToUserUpdatesTheUserInRepository() {
        userService.addFavoritePropertyToUser(userMock, propertyMock);
        verify(userRepositoryMock).update(userMock);
    }

    @Test
    public void gettingFavoritePropertiesForSaleRetrievesFavoritePropertiesFromUser() {
        userService.getFavoritePropertiesForSale(userMock);
        verify(userMock).getFavoriteProperties();
    }

    @Test
    public void gettingFavoritePropertiesForSaleReturnsAPropertyWhenItIsForSale() {
        when(propertyMock.isForSale()).thenReturn(true);
        Set<Property> returnedProperties = userService.getFavoritePropertiesForSale(userMock);
        assertThat(returnedProperties, hasItem(propertyMock));
    }

    @Test
    public void gettingFavoritePropertiesForSaleDoesReturnsAPropertyWhenItIsNotForSale() {
        when(propertyMock.isForSale()).thenReturn(false);
        Set<Property> returnedProperties = userService.getFavoritePropertiesForSale(userMock);
        assertThat(returnedProperties, not(hasItem(propertyMock)));
    }

    @Test
    public void gettingUserNotificationSettingsReturnsTheUserNotificationSettingsFromTheUser() {
        when(userMock.getNotificationSettings()).thenReturn(notificationSettingsMock);
        NotificationSettings returnedNotificationSettings = userService.getUserNotificationSettings(userMock);
        assertEquals(notificationSettingsMock, returnedNotificationSettings);
    }

    @Test
    public void updatingTheUserNotificationSettingsUpdatesTheUserNotificationSettingsOfTheUser() {
        userService.updateUserNotificationSettings(userMock, notificationSettingsMock);
        verify(userMock).setNotificationSettings(notificationSettingsMock);
    }

    @Test
    public void updatingTheUserNotificationSettingsUpdatesTheUserInRepository() {
        userService.updateUserNotificationSettings(userMock, notificationSettingsMock);
        verify(userRepositoryMock).update(userMock);
    }

    private void registerUser() throws UserServiceException {
        userService.registerUser(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
    }
}
