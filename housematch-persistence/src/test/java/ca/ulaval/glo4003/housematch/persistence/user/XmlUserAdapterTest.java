package ca.ulaval.glo4003.housematch.persistence.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.notification.Notification;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationSettings;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyRepository;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserFactory;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.domain.user.UserStatus;

public class XmlUserAdapterTest {

    private static final UserRole SAMPLE_ROLE = UserRole.SELLER;
    private static final UserStatus SAMPLE_STATUS = UserStatus.ACTIVE;
    private static final String SAMPLE_ENCRYPTED_PASSWORD = "ENCRYPTEDPASSWORD";
    private static final String SAMPLE_PASSWORD = "PASSWORD1234";
    private static final String SAMPLE_EMAIL = "email@hotmail.com";
    private static final String SAMPLE_USERNAME = "Alice";
    private static final UUID SAMPLE_ACTIVATION_CODE = UUID.randomUUID();
    private static final ZonedDateTime SAMPLE_DATE = ZonedDateTime.now();
    private static final Boolean SAMPLE_BOOLEAN = true;

    private UserFactory userFactoryMock;
    private PropertyRepository propertyRepositoryMock;
    private User userMock;
    private XmlUser xmlUserMock;
    private Property propertyMock;
    private NotificationSettings notificationSettingsMock;

    private XmlUserAdapter xmlUserAdapter;
    private Set<Property> propertiesForSale = new HashSet<>();
    private Set<Property> purchasedProperties = new HashSet<>();
    private Set<Integer> propertyForSaleHashCodes = new HashSet<>();
    private Set<Integer> purchasedPropertyHashCodes = new HashSet<>();
    private Set<Property> favoriteProperties = new HashSet<>();
    private Set<Integer> favoritePropertiesHashCodes = new HashSet<>();
    private Queue<Notification> notificationsQueue = new ConcurrentLinkedQueue<>();

    @Before
    public void init() throws Exception {
        initMocks();
        initStubs();
        xmlUserAdapter = new XmlUserAdapter(userFactoryMock, propertyRepositoryMock);
    }

    private void initMocks() {
        userFactoryMock = mock(UserFactory.class);
        propertyRepositoryMock = mock(PropertyRepository.class);
        propertyMock = mock(Property.class);
        notificationSettingsMock = mock(NotificationSettings.class);
        initUserMock();
        initXmlUserMock();
    }

    private void initUserMock() {
        userMock = mock(User.class);
        when(userMock.getUsername()).thenReturn(SAMPLE_USERNAME);
        when(userMock.getPasswordHash()).thenReturn(SAMPLE_PASSWORD);
        when(userMock.getEmail()).thenReturn(SAMPLE_EMAIL);
        when(userMock.getRole()).thenReturn(SAMPLE_ROLE);
        when(userMock.getActivationCode()).thenReturn(SAMPLE_ACTIVATION_CODE);
        when(userMock.isActivated()).thenReturn(SAMPLE_BOOLEAN);
        when(userMock.getStatus()).thenReturn(SAMPLE_STATUS);
        when(userMock.getLastLoginDate()).thenReturn(SAMPLE_DATE);
        when(userMock.getPropertiesForSale()).thenReturn(propertiesForSale);
        when(userMock.getPurchasedProperties()).thenReturn(purchasedProperties);
        when(userMock.getFavoriteProperties()).thenReturn(favoriteProperties);
        when(userMock.getNotificationSettings()).thenReturn(notificationSettingsMock);
        when(userMock.getNotificationQueue()).thenReturn(notificationsQueue);
    }

    private void initXmlUserMock() {
        xmlUserMock = mock(XmlUser.class);
        xmlUserMock.username = SAMPLE_USERNAME;
        xmlUserMock.passwordHash = SAMPLE_ENCRYPTED_PASSWORD;
        xmlUserMock.email = SAMPLE_EMAIL;
        xmlUserMock.role = SAMPLE_ROLE;
        xmlUserMock.activationCode = SAMPLE_ACTIVATION_CODE;
        xmlUserMock.lastLoginDate = SAMPLE_DATE;
        xmlUserMock.activated = SAMPLE_BOOLEAN;
        xmlUserMock.status = SAMPLE_STATUS;
        xmlUserMock.propertiesForSale = propertyForSaleHashCodes;
        xmlUserMock.purchasedProperties = purchasedPropertyHashCodes;
        xmlUserMock.favoriteProperties = favoritePropertiesHashCodes;
        xmlUserMock.notificationSettings = notificationSettingsMock;
        xmlUserMock.notificationsQueue = notificationsQueue;
    }

    private void initStubs() {
        when(userFactoryMock.createUser(anyString(), anyString(), anyString(), any(UserRole.class))).thenReturn(userMock);
    }

    @Test
    public void simpleUserAttributesAreConvertedDuringMarshalling() throws Exception {
        xmlUserAdapter.marshal(userMock);

        assertEquals(userMock.getUsername(), xmlUserMock.username);
        assertEquals(userMock.getEmail(), xmlUserMock.email);
        assertEquals(userMock.getRole(), xmlUserMock.role);
        assertEquals(userMock.getActivationCode(), xmlUserMock.activationCode);
        assertEquals(userMock.isActivated(), xmlUserMock.activated);
        assertEquals(userMock.getLastLoginDate(), xmlUserMock.lastLoginDate);
        assertEquals(userMock.getStatus(), xmlUserMock.status);
        assertEquals(userMock.getNotificationSettings(), xmlUserMock.notificationSettings);
        assertEquals(userMock.getNotificationQueue(), xmlUserMock.notificationsQueue);
    }

    @Test
    public void propertiesForSaleAreMarshalledAsReferenceDuringMarshalling() throws Exception {
        propertiesForSale.add(propertyMock);
        XmlUser xmlUser = xmlUserAdapter.marshal(userMock);
        assertThat(xmlUser.propertiesForSale, contains(propertyMock.hashCode()));
    }

    @Test
    public void purchasedPropertiesAreMarshalledAsReferenceDuringMarshalling() throws Exception {
        purchasedProperties.add(propertyMock);
        XmlUser xmlUser = xmlUserAdapter.marshal(userMock);
        assertThat(xmlUser.purchasedProperties, contains(propertyMock.hashCode()));
    }

    @Test
    public void simpleXmlUserAttributesAreConvertedDuringUnmarshalling() throws Exception {
        xmlUserAdapter.unmarshal(xmlUserMock);

        assertEquals(xmlUserMock.username, userMock.getUsername());
        assertEquals(xmlUserMock.email, userMock.getEmail());
        assertEquals(xmlUserMock.role, userMock.getRole());
        assertEquals(xmlUserMock.activationCode, userMock.getActivationCode());
        assertEquals(xmlUserMock.activated, userMock.isActivated());
        assertEquals(xmlUserMock.lastLoginDate, userMock.getLastLoginDate());
        assertEquals(xmlUserMock.status, userMock.getStatus());
        assertEquals(xmlUserMock.notificationSettings, userMock.getNotificationSettings());
        assertEquals(xmlUserMock.notificationsQueue, userMock.getNotificationQueue());
    }

    @Test
    public void propertiesForSaleAreDereferencedDuringUnmarshalling() throws Exception {
        when(propertyRepositoryMock.getByHashCode(propertyMock.hashCode())).thenReturn(propertyMock);
        propertiesForSale.add(propertyMock);
        propertyForSaleHashCodes.add(propertyMock.hashCode());

        xmlUserAdapter.unmarshal(xmlUserMock);

        verify(userMock).setPropertiesForSale(eq(propertiesForSale));
    }

    @Test
    public void purchasedPropertiesAreDereferencedDuringUnmarshalling() throws Exception {
        when(propertyRepositoryMock.getByHashCode(propertyMock.hashCode())).thenReturn(propertyMock);
        purchasedProperties.add(propertyMock);
        purchasedPropertyHashCodes.add(propertyMock.hashCode());

        xmlUserAdapter.unmarshal(xmlUserMock);

        verify(userMock).setPurchasedProperties(eq(purchasedProperties));
    }

    @Test
    public void favoritePropertiesAreDereferencedDuringUnmarshalling() throws Exception {
        when(propertyRepositoryMock.getByHashCode(propertyMock.hashCode())).thenReturn(propertyMock);
        favoriteProperties.add(propertyMock);
        favoritePropertiesHashCodes.add(propertyMock.hashCode());

        xmlUserAdapter.unmarshal(xmlUserMock);

        verify(userMock).setFavoriteProperties(eq(favoriteProperties));
    }
}
