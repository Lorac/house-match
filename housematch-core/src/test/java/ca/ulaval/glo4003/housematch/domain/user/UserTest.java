package ca.ulaval.glo4003.housematch.domain.user;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.notification.Notification;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationSettings;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationType;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.utils.StringHasher;

public class UserTest {

    private static final UserRole SAMPLE_ROLE = UserRole.SELLER;
    private static final UserRole ANOTHER_SAMPLE_ROLE = UserRole.BUYER;
    private static final String SAMPLE_PASSWORD = "PASSWORD1234";
    private static final String SAMPLE_PASSWORD_HASH = "asd098fsdfgw4";
    private static final String ANOTHER_SAMPLE_PASSWORD_HASH = "53w5sdfd";
    private static final String SAMPLE_EMAIL = "email@hotmail.com";
    private static final String ANOTHER_SAMPLE_EMAIL = "email2@hotmail.com";
    private static final String SAMPLE_USERNAME = "Alice";
    private static final String SAMPLE_CAPITALIZED_USERNAME = "ALICE";
    private static final String ANOTHER_SAMPLE_USERNAME = "Bob";
    private static final UserStatus SAMPLE_STATUS = UserStatus.ACTIVE;
    private static final ZonedDateTime SAMPLE_DATE_TIME = ZonedDateTime.now();
    private static final Object SAMPLE_OBJECT = new Object();
    private static final UUID SAMPLE_ACTIVATION_CODE = UUID.randomUUID();
    private static final NotificationType SAMPLE_NOTIFICATION_TYPE = NotificationType.PROPERTY_PUT_UP_FOR_SALE;

    private Address addressMock;
    private StringHasher stringHasherMock;
    private Property propertyMock;
    private UserObserver userObserverMock;
    private NotificationSettings notificationSettingsMock;
    private Notification notificationMock;

    private Set<Property> properties = new HashSet<>();
    private Map<NotificationType, Queue<Notification>> notificationQueues = new HashMap<>();
    private Queue<Notification> notificationQueue = new ConcurrentLinkedQueue<>();
    private User user;
    private User buyer;
    private User seller;

    @Before
    public void init() throws Exception {
        initMocks();
        initStubs();
        createUsers();
        notificationQueues.put(SAMPLE_NOTIFICATION_TYPE, notificationQueue);
    }

    private void initMocks() {
        stringHasherMock = mock(StringHasher.class);
        propertyMock = mock(Property.class);
        userObserverMock = mock(UserObserver.class);
        addressMock = mock(Address.class);
        notificationSettingsMock = mock(NotificationSettings.class);
        notificationMock = mock(Notification.class);
    }

    private void initStubs() {
        when(stringHasherMock.hash(SAMPLE_PASSWORD)).thenReturn(SAMPLE_PASSWORD_HASH);
        when(notificationMock.getType()).thenReturn(SAMPLE_NOTIFICATION_TYPE);
    }

    private void createUsers() {
        buyer = new User(stringHasherMock, SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, UserRole.BUYER);
        buyer.setLastLoginDate(ZonedDateTime.now().minusMonths(User.INACTIVITY_TIMEOUT_PERIOD_IN_MONTHS - 1));
        seller = new User(stringHasherMock, SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, UserRole.SELLER);
        user = new User(stringHasherMock, SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        user.registerObserver(userObserverMock);
        user.setNotificationSettings(notificationSettingsMock);
    }

    @Test
    public void usersWithTheSameUsernameShouldBeConsideredAsEqual() throws Exception {
        User anotherUser = new User(stringHasherMock, SAMPLE_USERNAME, ANOTHER_SAMPLE_EMAIL, ANOTHER_SAMPLE_PASSWORD_HASH,
                ANOTHER_SAMPLE_ROLE);
        assertTrue(user.equals(anotherUser));
    }

    @Test
    public void usersWithDifferentUsernamesShouldBeConsideredAsDifferent() throws Exception {
        User anotherUser = new User(stringHasherMock, ANOTHER_SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        assertFalse(user.equals(anotherUser));
    }

    @Test
    public void usersWithTheSameUsernameShouldHaveTheSameHashCode() throws Exception {
        User anotherUser = new User(stringHasherMock, SAMPLE_USERNAME, ANOTHER_SAMPLE_EMAIL, ANOTHER_SAMPLE_PASSWORD_HASH,
                ANOTHER_SAMPLE_ROLE);
        assertEquals(user.hashCode(), anotherUser.hashCode());
    }

    @Test
    public void usersWithDifferentUsernamesShouldNotHaveTheSameHashCode() throws Exception {
        User anotherUser = new User(stringHasherMock, ANOTHER_SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        assertNotEquals(user.hashCode(), anotherUser.hashCode());
    }

    @Test
    public void usersHavingDifferentUsernameCapitalizationShouldBeConsideredAsEqual() throws Exception {
        User anotherUser = new User(stringHasherMock, SAMPLE_CAPITALIZED_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        assertTrue(user.equals(anotherUser));
    }

    @Test
    public void userComparedWithItselfShouldBeConsideredAsEqual() {
        assertTrue(user.equals(user));
    }

    @Test
    public void userComparedWithAnotherObjectShouldNotBeConsideredAsEqual() {
        assertFalse(user.equals(SAMPLE_OBJECT));
    }

    @Test
    public void usernameComparisonShouldConsiderUsersWithTheSameUsernameAsEqual() throws Exception {
        User anotherUser = new User(stringHasherMock, SAMPLE_USERNAME, ANOTHER_SAMPLE_EMAIL, ANOTHER_SAMPLE_PASSWORD_HASH,
                ANOTHER_SAMPLE_ROLE);
        assertTrue(user.usernameEquals(anotherUser.getUsername()));
    }

    @Test
    public void usernameComparisonShouldConsiderUsersWithDifferentUsernameAsDifferent() throws Exception {
        User anotherUser = new User(stringHasherMock, ANOTHER_SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        assertFalse(user.usernameEquals(anotherUser.getUsername()));
    }

    @Test
    public void usernameComparisonShouldConsiderUsersHavingDifferentUsernameCapitalizationAsEqual() throws Exception {
        User anotherUser = new User(stringHasherMock, SAMPLE_CAPITALIZED_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        assertTrue(user.usernameEquals(anotherUser.getUsername()));
    }

    @Test
    public void gettingTheUsernameGetsTheUsername() throws Exception {
        assertEquals(SAMPLE_USERNAME, user.getUsername());
    }

    @Test
    public void gettingTheEmailGetsTheEmail() throws Exception {
        assertEquals(SAMPLE_EMAIL, user.getEmail());
    }

    @Test
    public void settingThePasswordHashSetsTheSpecifiedPasswordHash() throws Exception {
        user.setPasswordHash(ANOTHER_SAMPLE_PASSWORD_HASH);
        assertEquals(ANOTHER_SAMPLE_PASSWORD_HASH, user.getPasswordHash());
    }

    @Test
    public void validationOfTheRightPasswordDoesNotThrowAnException() throws Exception {
        try {
            user.validatePassword(SAMPLE_PASSWORD);
        } catch (Exception e) {
            fail();
        }
    }

    @Test(expected = InvalidPasswordException.class)
    public void validationOfTheWrongPasswordThrowsInvalidPasswordException() throws Exception {
        user.validatePassword(ANOTHER_SAMPLE_PASSWORD_HASH);
    }

    @Test
    public void gettingTheRoleGetsTheRole() {
        assertEquals(SAMPLE_ROLE, user.getRole());
    }

    @Test
    public void userShouldNotHaveTheSpecifiedRoleWhenUserDoesNotHaveThatRole() {
        assertFalse(user.hasRole(ANOTHER_SAMPLE_ROLE));
    }

    @Test
    public void userShouldHaveTheSpecifiedRoleWhenUserHasThatRole() {
        assertTrue(user.hasRole(SAMPLE_ROLE));
    }

    @Test
    public void updatingTheEmailUpdatesTheSpecifiedEmail() {
        user.updateEmail(ANOTHER_SAMPLE_EMAIL);
        assertEquals(ANOTHER_SAMPLE_EMAIL, user.getEmail());
    }

    @Test
    public void updatingTheEmailSetsTheActivationFlagToFalse() {
        user.updateEmail(ANOTHER_SAMPLE_EMAIL);
        assertFalse(user.isActivated());
    }

    @Test
    public void settingTheActivationCodeSetsTheSpecifiedActivationCode() {
        user.setActivationCode(SAMPLE_ACTIVATION_CODE);
        assertEquals(SAMPLE_ACTIVATION_CODE, user.getActivationCode());
    }

    @Test
    public void newUserIsNotActivatedByDefault() {
        assertFalse(user.isActivated());
    }

    @Test
    public void settingTheActivationFlagSetsTheActivationFlag() {
        user.setActivated(Boolean.TRUE);
        assertEquals(Boolean.TRUE, user.isActivated());
    }

    @Test
    public void activatingTheUserClearsTheActivationCode() throws Exception {
        user.setActivationCode(SAMPLE_ACTIVATION_CODE);
        user.activate();
        assertNull(user.getActivationCode());
    }

    @Test
    public void settingPropertiesForSaleSetsTheSpecifiedPropertiesForSale() {
        user.setPropertiesForSale(properties);
        assertEquals(properties, user.getPropertiesForSale());
    }

    @Test
    public void addingPropertyForSaleAddsTheSpecifiedPropertyForSale() {
        user.addPropertyForSale(propertyMock);
        assertThat(user.getPropertiesForSale(), contains(propertyMock));
    }

    @Test
    public void gettingPropertyForSaleByHashCodeReturnsThePropertyFromTheSpecifiedHashCode() throws Exception {
        user.addPropertyForSale(propertyMock);
        assertSame(propertyMock, user.getPropertyForSaleByHashCode(propertyMock.hashCode()));
    }

    @Test(expected = PropertyNotFoundException.class)
    public void gettingPropertyForSaleByHashCodeThrowsPropertyNotFoundExceptionWhenTheSpecifiedPropertyDoesNotExist() throws Exception {
        user.getPropertyForSaleByHashCode(propertyMock.hashCode());
    }

    @Test
    public void settingPurchasedPropertiesSetsThePurchasedProperties() {
        user.setPurchasedProperties(properties);
        assertEquals(properties, user.getPurchasedProperties());
    }

    @Test
    public void settingTheAddressSetsTheSpecifiedAddress() throws Exception {
        user.setAddress(addressMock);
        assertEquals(addressMock, user.getAddress());
    }

    @Test
    public void settingTheStatusSetsTheSpecifiedStatus() {
        user.setStatus(SAMPLE_STATUS);
        assertEquals(SAMPLE_STATUS, user.getStatus());
    }

    @Test
    public void settingTheLastLoginDateSetsTheLastLoginDate() {
        user.setLastLoginDate(SAMPLE_DATE_TIME);
        assertEquals(SAMPLE_DATE_TIME, user.getLastLoginDate());
    }

    @Test
    public void applyingUserStatusPolicyWhenBuyerHasNeverLoggedInSetsTheUserStatusToInactive() {
        buyer.setLastLoginDate(null);
        buyer.applyUserStatusPolicy();
        assertFalse(buyer.isActive());
    }

    @Test
    public void applyingUserStatusPolicyWhenBuyerHasLoggedMoreThanSixMonthsAgoSetsTheUserStatusToInactive() {
        buyer.setLastLoginDate(ZonedDateTime.now().minusMonths(User.INACTIVITY_TIMEOUT_PERIOD_IN_MONTHS + 1));
        buyer.applyUserStatusPolicy();
        assertFalse(buyer.isActive());
    }

    @Test
    public void applyingUserStatusPolicyWhenBuyerCompliesToTheStatusPolicyRequirementsSetsTheUserStatusToActive() {
        buyer.applyUserStatusPolicy();

        assertTrue(buyer.isActive());
    }

    @Test
    public void applyingUserStatusPolicyWhenSellerHasNoPropertiesForSaleSetsTheUserStatusToInactive() {
        seller.applyUserStatusPolicy();
        assertFalse(seller.isActive());
    }

    @Test
    public void applyingUserStatusPolicyWhenSellerHasSomePropertiesForSaleSetsTheUserStatusToActive() {
        seller.addPropertyForSale(propertyMock);
        seller.applyUserStatusPolicy();
        assertTrue(seller.isActive());
    }

    @Test
    public void updatingTheUserStatusNotifiesTheObservers() {
        user.setStatus(UserStatus.ACTIVE);
        user.applyUserStatusPolicy();
        verify(userObserverMock).userStatusChanged(user, UserStatus.INACTIVE);
    }

    @Test
    public void purchasingAPropertyMarksThePropertyAsSold() {
        buyer.purchaseProperty(propertyMock);
        verify(propertyMock).markAsSold();
    }

    @Test
    public void settingFavoritePropertiesSetsTheFavoriteProperties() {
        user.setFavoriteProperties(properties);
        assertEquals(properties, user.getFavoriteProperties());
    }

    @Test
    public void addingPropertyToFavoriteAddsThePropertyToFavorites() {
        user.addPropertyToFavorites(propertyMock);
        assertTrue(user.isPropertyFavorited(propertyMock));
    }

    @Test
    public void addingPropertyToFavoriteRegistersANewUserFavoritePropertyObserverToTheProperty() {
        user.addPropertyToFavorites(propertyMock);
        verify(propertyMock).registerObserver(Matchers.any(UserFavoritePropertyObserver.class));
    }

    @Test
    public void settingTheNotificationSettingsSetsTheNotificationSettings() {
        user.setNotificationSettings(notificationSettingsMock);
        assertEquals(notificationSettingsMock, user.getNotificationSettings());
    }

    @Test
    public void settingTheNotificationQueuesSetsTheNotificationQueues() {
        user.setNotificationQueues(notificationQueues);
        assertEquals(notificationQueues, user.getNotificationQueues());
    }

    @Test
    public void gettingTheNotificationQueueGetsTheSpecifiedNotificationQueue() {
        user.setNotificationQueues(notificationQueues);
        Queue<Notification> returnedNotificationQueue = user.getNotificationQueue(SAMPLE_NOTIFICATION_TYPE);
        assertEquals(notificationQueue, returnedNotificationQueue);
    }

    @Test
    public void notifyingTheUserWithNotificationAddsTheSpecifiedNotificationToTheQueue() {
        when(notificationSettingsMock.isNotificationEnabled(SAMPLE_NOTIFICATION_TYPE)).thenReturn(true);
        user.setNotificationQueues(notificationQueues);

        user.notify(notificationMock);

        assertThat(notificationQueues.get(SAMPLE_NOTIFICATION_TYPE), hasItem(notificationMock));
    }

    @Test
    public void notifyingTheUserWhenTheseTypeOfNotificationsAreNotEnabledDoesNotAddTheNotificationToTheQueue() {
        when(notificationSettingsMock.isNotificationEnabled(SAMPLE_NOTIFICATION_TYPE)).thenReturn(false);
        user.setNotificationQueues(notificationQueues);

        user.notify(notificationMock);

        assertThat(notificationQueues.get(SAMPLE_NOTIFICATION_TYPE), not(hasItem(notificationMock)));
    }

    @Test
    public void notifyingTheUserWithNotificationNotifiesTheObservers() {
        when(notificationSettingsMock.isNotificationEnabled(SAMPLE_NOTIFICATION_TYPE)).thenReturn(true);
        user.notify(notificationMock);
        verify(userObserverMock).userNotificationQueued(user, notificationMock);
    }

}