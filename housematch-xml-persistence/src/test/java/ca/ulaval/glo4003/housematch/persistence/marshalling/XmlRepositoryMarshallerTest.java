package ca.ulaval.glo4003.housematch.persistence.marshalling;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.jasypt.util.text.TextEncryptor;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.persistence.ResourceLoader;
import ca.ulaval.glo4003.housematch.persistence.XmlRepositoryAssembler;

public class XmlRepositoryMarshallerTest {

    private static final String SAMPLE_RESOURCE_NAME = "resource";

    private ResourceLoader resourceLoaderMock;
    private TextEncryptor textEncryptorMock;
    private Marshaller marshallerMock;
    private Unmarshaller unmarshallerMock;
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
        inputStreamMock = mock(InputStream.class);
        outputStreamMock = mock(OutputStream.class);
    }

    private void stubMethods() throws Exception {
        when(resourceLoaderMock.loadResourceAsInputStream(any(XmlRepositoryMarshaller.class), eq(SAMPLE_RESOURCE_NAME)))
                .thenReturn(inputStreamMock);
        when(resourceLoaderMock.loadResourceAsOutputStream(any(XmlRepositoryMarshaller.class),
                eq(SAMPLE_RESOURCE_NAME))).thenReturn(outputStreamMock);
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
    public void theXmlRepositoryAssemblerIsMarshalledToAnOutputStreamDuringMarshalling() throws JAXBException {
        xmlRepositoryMarshaller.marshal();
        verify(marshallerMock).marshal(any(XmlRepositoryAssembler.class), eq(outputStreamMock));
    }
}
