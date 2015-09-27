package ca.ulaval.glo4003.housematch.persistence.marshalling;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Before;
import org.junit.Test;

public class XmlMarshallerTest {

    private XmlMarshaller<Object> xmlMarshaller;
    private static final Object SAMPLE_OBJECT = new Object();
    private Marshaller marshallerMock;
    private Unmarshaller unmarshallerMock;
    private InputStream inputStreamMock;
    private OutputStream outputStreamMock;

    @Before
    public void init() {
        initMocks();
        xmlMarshaller = new XmlMarshaller<>(marshallerMock, unmarshallerMock);
    }

    private void initMocks() {
        marshallerMock = mock(Marshaller.class);
        unmarshallerMock = mock(Unmarshaller.class);
        inputStreamMock = mock(InputStream.class);
        outputStreamMock = mock(OutputStream.class);
    }

    @Test
    public void xmlMarshallerCorrectlyInstantiatesUsingDefaultMarshallers() {
        xmlMarshaller = new XmlMarshaller<>(Object.class);
        assertNotNull(xmlMarshaller.getMarshaller());
        assertNotNull(xmlMarshaller.getUnmarshaller());
    }

    @Test
    public void unmarshalMethodUnmarshallsTheSpecifiedInputStreamToAnXmlRepositoryAssembler() throws JAXBException {
        when(unmarshallerMock.unmarshal(inputStreamMock)).thenReturn(SAMPLE_OBJECT);
        Object unmarshalledObject = xmlMarshaller.unmarshal(inputStreamMock);
        assertSame(SAMPLE_OBJECT, unmarshalledObject);
    }

    @Test
    public void marshalMethodMarshallsTheSpecifiedXmlRepositoryAssemblerToAnOutputStream() throws JAXBException {
        xmlMarshaller.marshal(SAMPLE_OBJECT, outputStreamMock);
        verify(marshallerMock).marshal(SAMPLE_OBJECT, outputStreamMock);
    }
}
