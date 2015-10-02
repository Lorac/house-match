package ca.ulaval.glo4003.housematch.persistence.marshalling;

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.jasypt.util.text.TextEncryptor;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.persistence.ResourceLoader;

public class XmlRepositoryMarshallerTest {

    private static final String SAMPLE_RESOURCE_NAME = "resource";
    private static final List<User> SAMPLE_USER_LIST = new ArrayList<User>();

    private ResourceLoader resourceLoaderMock;
    private TextEncryptor textEncryptorMock;
    private Marshaller marshallerMock;
    private Unmarshaller unmarshallerMock;
    private XmlRootElementNode xmlRootElementNodeMock;
    private InputStream inputStreamMock;
    private OutputStream outputStreamMock;

    private XmlRepositoryMarshaller xmlRepositoryMarshaller;

    @Before
    public void init() throws Exception {
        initMocks();
        stubMethods();
        xmlRepositoryMarshaller = new XmlRepositoryMarshaller(marshallerMock, unmarshallerMock, resourceLoaderMock,
                SAMPLE_RESOURCE_NAME, textEncryptorMock);
    }

    public void initMocks() {
        resourceLoaderMock = mock(ResourceLoader.class);
        textEncryptorMock = mock(TextEncryptor.class);
        marshallerMock = mock(Marshaller.class);
        unmarshallerMock = mock(Unmarshaller.class);
        xmlRootElementNodeMock = mock(XmlRootElementNode.class);
        inputStreamMock = mock(InputStream.class);
        outputStreamMock = mock(OutputStream.class);
    }

    private void stubMethods() throws Exception {
        when(resourceLoaderMock.loadResourceAsInputStream(any(XmlRepositoryMarshaller.class), eq(SAMPLE_RESOURCE_NAME)))
                .thenReturn(inputStreamMock);
        when(resourceLoaderMock.loadResourceAsOutputStream(any(XmlRepositoryMarshaller.class),
                eq(SAMPLE_RESOURCE_NAME))).thenReturn(outputStreamMock);
        when(unmarshallerMock.unmarshal(any(InputStream.class))).thenReturn(xmlRootElementNodeMock);
    }

    @Test
    public void onXmlRepostoryMarshallerInstantiationTheSpecifiedResourceIsLoadedAsInputStream() throws Exception {
        verify(resourceLoaderMock).loadResourceAsInputStream(xmlRepositoryMarshaller, SAMPLE_RESOURCE_NAME);
    }

    @Test
    public void onXmlRepostoryMarshallerInstantiationTheInputStreamIsUnmarshalled() throws Exception {
        verify(unmarshallerMock).unmarshal(inputStreamMock);
    }

    @Test
    public void theSpecifiedResourceIsLoadedAsAnOutputStreamDuringMarshalling() throws Exception {
        xmlRepositoryMarshaller.marshal();
        verify(resourceLoaderMock).loadResourceAsOutputStream(xmlRepositoryMarshaller, SAMPLE_RESOURCE_NAME);
    }

    @Test
    public void theXmlRootElementNodeIsMarshalledToAnOutputStreamDuringMarshalling() throws Exception {
        xmlRepositoryMarshaller.marshal();
        verify(marshallerMock).marshal(any(XmlRootElementNode.class), eq(outputStreamMock));
    }

    @Test
    public void gettingUsersReturnsTheUsersFromTheXmlRootElementNode() {
        when(xmlRootElementNodeMock.getUsers()).thenReturn(SAMPLE_USER_LIST);

        List<User> returnedUsers = xmlRepositoryMarshaller.getUsers();

        verify(xmlRootElementNodeMock).getUsers();
        assertSame(SAMPLE_USER_LIST, returnedUsers);
    }

    @Test
    public void settingUsersSetsTheUsersInTheXmlRootElementNode() {
        xmlRepositoryMarshaller.setUsers(SAMPLE_USER_LIST);
        verify(xmlRootElementNodeMock).setUsers(SAMPLE_USER_LIST);
    }

    @Test
    public void settingUsersMarshallsTheXmlRootElementNode() throws Exception {
        xmlRepositoryMarshaller.setUsers(SAMPLE_USER_LIST);
        verify(marshallerMock).marshal(xmlRootElementNodeMock, outputStreamMock);
    }

}
