package ca.ulaval.glo4003.housematch.persistence.user;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyRepository;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import org.jasypt.util.text.TextEncryptor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class XmlUserAdapterTest {

    private static final UserRole SAMPLE_ROLE = UserRole.SELLER;
    private static final String SAMPLE_PASSWORD = "PASSWORD1234";
    private static final String SAMPLE_EMAIL = "email@hotmail.com";
    private static final String SAMPLE_USERNAME = "Alice";
    private static final UUID SAMPLE_ACTIVATION_CODE = UUID.randomUUID();
    private static final Boolean SAMPLE_BOOLEAN = true;

    private PropertyRepository propertyRepositoryMock;
    private TextEncryptor textEncryptorMock;
    private User userMock;
    private XmlUser xmlUserMock;
    private Property propertyMock;

    private XmlUserAdapter xmlUserAdapter;
    private List<Property> properties = new ArrayList<Property>();
    private List<Integer> propertyRefs = new ArrayList<Integer>();

    @Before
    public void init() throws Exception {
        initMocks();
        xmlUserAdapter = new XmlUserAdapter(propertyRepositoryMock, textEncryptorMock);
    }

    private void initMocks() {
        propertyRepositoryMock = mock(PropertyRepository.class);
        textEncryptorMock = mock(TextEncryptor.class);
        propertyMock = mock(Property.class);
        initUserMock();
        initXmlUserMock();
    }

    private void initUserMock() {
        userMock = mock(User.class);
        when(userMock.getUsername()).thenReturn(SAMPLE_USERNAME);
        when(userMock.getPassword()).thenReturn(SAMPLE_PASSWORD);
        when(userMock.getEmail()).thenReturn(SAMPLE_EMAIL);
        when(userMock.getRole()).thenReturn(SAMPLE_ROLE);
        when(userMock.getActivationCode()).thenReturn(SAMPLE_ACTIVATION_CODE);
        when(userMock.isActivated()).thenReturn(SAMPLE_BOOLEAN);
        when(userMock.getProperties()).thenReturn(properties);
    }

    private void initXmlUserMock() {
        xmlUserMock = mock(XmlUser.class);
        xmlUserMock.username = SAMPLE_USERNAME;
        xmlUserMock.password = SAMPLE_PASSWORD;
        xmlUserMock.email = SAMPLE_EMAIL;
        xmlUserMock.role = SAMPLE_ROLE;
        xmlUserMock.activationCode = SAMPLE_ACTIVATION_CODE;
        xmlUserMock.activated = SAMPLE_BOOLEAN;
        xmlUserMock.propertyRef = propertyRefs;
    }

    @Test
    public void simpleUserPropertiesAreConvertedDuringMarshalling() throws Exception {
        xmlUserAdapter.marshal(userMock);

        assertEquals(userMock.getUsername(), xmlUserMock.username);
        assertEquals(userMock.getEmail(), xmlUserMock.email);
        assertEquals(userMock.getRole(), xmlUserMock.role);
        assertEquals(userMock.getActivationCode(), xmlUserMock.activationCode);
        assertEquals(userMock.isActivated(), xmlUserMock.activated);
    }

    @Test
    public void passwordIsEncryptedDuringMarshalling() throws Exception {
        xmlUserAdapter.marshal(userMock);
        verify(textEncryptorMock).encrypt(SAMPLE_PASSWORD);
    }

    @Test
    public void propertiesAreMarshalledAsReferencesDuringMarshalling() throws Exception {
        properties.add(propertyMock);
        XmlUser xmlUser = xmlUserAdapter.marshal(userMock);
        assertThat(xmlUser.propertyRef, contains(propertyMock.hashCode()));
    }

    @Test
    public void simpleXmlUserPropertiesAreConvertedDuringUnmarshalling() throws Exception {
        xmlUserAdapter.unmarshal(xmlUserMock);

        assertEquals(xmlUserMock.username, userMock.getUsername());
        assertEquals(xmlUserMock.email, userMock.getEmail());
        assertEquals(xmlUserMock.role, userMock.getRole());
        assertEquals(xmlUserMock.activationCode, userMock.getActivationCode());
        assertEquals(xmlUserMock.activated, userMock.isActivated());
    }

    @Test
    public void passwordIsDecrpytedDuringUnmarshalling() throws Exception {
        xmlUserAdapter.unmarshal(xmlUserMock);
        verify(textEncryptorMock).decrypt(SAMPLE_PASSWORD);
    }

    @Test
    public void propertiesAreDereferencedDuringUnmarshalling() throws Exception {
        when(propertyRepositoryMock.getByHashCode(propertyMock.hashCode())).thenReturn(propertyMock);
        propertyRefs.add(propertyMock.hashCode());

        User user = xmlUserAdapter.unmarshal(xmlUserMock);

        assertThat(user.getProperties(), contains(propertyMock));
    }
}
