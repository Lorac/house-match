package ca.ulaval.glo4003.housematch.persistence.marshalling;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.doThrow;
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

    private static final Object SAMPLE_OBJECT = new Object();
    private XmlMarshaller<Object> xmlMarshaller;
    private Marshaller marshallerMock;
    private Unmarshaller unmarshallerMock;
    private InputStream inputStreamMock;
    private OutputStream outputStreamMock;

    @Before
    public void init() {
        initMocks();
        xmlMarshaller = new XmlMarshaller<Object>(marshallerMock, unmarshallerMock);
    }

    private void initMocks() {
        marshallerMock = mock(Marshaller.class);
        unmarshallerMock = mock(Unmarshaller.class);
        inputStreamMock = mock(InputStream.class);
        outputStreamMock = mock(OutputStream.class);
    }

    @Test
    public void xmlMarshallerCorrectlyInstantiatesUsingDefaultMarshallers() {
        xmlMarshaller = new XmlMarshaller<Object>(Object.class);
        assertNotNull(xmlMarshaller.getMarshaller());
        assertNotNull(xmlMarshaller.getUnmarshaller());
    }

    @Test
    public void unmarshallingUnmarshallsTheSpecifiedInputStreamToAnObject() throws JAXBException {
        when(unmarshallerMock.unmarshal(inputStreamMock)).thenReturn(SAMPLE_OBJECT);
        Object unmarshalledObject = xmlMarshaller.unmarshal(inputStreamMock);
        assertSame(SAMPLE_OBJECT, unmarshalledObject);
    }

    @Test(expected = MarshallingException.class)
    public void unmarshallingThrowsMarshallingExceptionOnJAXBException() throws JAXBException {
        doThrow(new JAXBException("")).when(unmarshallerMock).unmarshal(inputStreamMock);
        xmlMarshaller.unmarshal(inputStreamMock);
    }

    @Test
    public void marshallingMarshalsTheSpecifiedObjectToAnOutputStream() throws JAXBException {
        xmlMarshaller.marshal(SAMPLE_OBJECT, outputStreamMock);
        verify(marshallerMock).marshal(SAMPLE_OBJECT, outputStreamMock);
    }

    @Test(expected = MarshallingException.class)
    public void marshallingThrowsMarshallingExceptionOnJAXBException() throws JAXBException {
        doThrow(new JAXBException("")).when(marshallerMock).marshal(SAMPLE_OBJECT, outputStreamMock);
        xmlMarshaller.marshal(SAMPLE_OBJECT, outputStreamMock);
    }
}
