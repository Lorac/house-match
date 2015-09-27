package ca.ulaval.glo4003.housematch.domain.user;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.jasypt.util.text.TextEncryptor;
import org.junit.Before;
import org.junit.Test;

public class XmlUserAdapterTest {

    private static final UserRole SAMPLE_ROLE = UserRole.SELLER;
    private static final String SAMPLE_PASSWORD = "PASSWORD1234";
    private static final String SAMPLE_EMAIL = "email@hotmail.com";
    private static final String SAMPLE_USERNAME = "Alice";
    private static final Boolean SAMPLE_BOOLEAN = true;

    private TextEncryptor textEncryptorMock;
    private User userMock;
    private XmlUser xmlUserMock;

    private XmlUserAdapter xmlUserAdapter;

    @Before
    public void init() throws Exception {
        textEncryptorMock = mock(TextEncryptor.class);
        initUserMock();
        initXmlUserMock();

        xmlUserAdapter = new XmlUserAdapter(textEncryptorMock);
    }

    private void initUserMock() {
        userMock = mock(User.class);
        userMock.username = SAMPLE_USERNAME;
        userMock.password = SAMPLE_PASSWORD;
        userMock.email = SAMPLE_EMAIL;
        userMock.role = SAMPLE_ROLE;
        userMock.activated = SAMPLE_BOOLEAN;
    }

    private void initXmlUserMock() {
        xmlUserMock = mock(XmlUser.class);
        xmlUserMock.username = SAMPLE_USERNAME;
        xmlUserMock.password = SAMPLE_PASSWORD;
        xmlUserMock.email = SAMPLE_EMAIL;
        xmlUserMock.role = SAMPLE_ROLE;
        xmlUserMock.activated = SAMPLE_BOOLEAN;
    }

    @Test
    public void marshalConvertsSimpleUserProperties() throws Exception {
        xmlUserAdapter.marshal(userMock);

        assertEquals(userMock.username, xmlUserMock.username);
        assertEquals(userMock.email, xmlUserMock.email);
        assertEquals(userMock.role, xmlUserMock.role);
        assertEquals(userMock.activated, xmlUserMock.activated);
    }

    @Test
    public void marshalMethodEncryptsThePassword() throws Exception {
        xmlUserAdapter.marshal(userMock);
        verify(textEncryptorMock).encrypt(SAMPLE_PASSWORD);
    }

    @Test
    public void unmarshalConvertsSimpleXmlUserProperties() throws Exception {
        xmlUserAdapter.unmarshal(xmlUserMock);

        assertEquals(xmlUserMock.username, userMock.username);
        assertEquals(xmlUserMock.email, userMock.email);
        assertEquals(xmlUserMock.role, userMock.role);
        assertEquals(xmlUserMock.activated, userMock.activated);
    }

    @Test
    public void unmarshalMethodDecryptsThePassword() throws Exception {
        xmlUserAdapter.unmarshal(xmlUserMock);
        verify(textEncryptorMock).decrypt(SAMPLE_PASSWORD);
    }

}
