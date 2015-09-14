package ca.ulaval.glo4003.housematch.persistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Before;
import org.junit.Before;
import org.junit.Test;
import org.junit.Test;

public class XmlMarshallerTest {

    private XmlMarshaller xmlMarshaller;
    private XmlRootNodeAssembler xmlRootNodeAssemblerMock;
    private Marshaller marshallerMock;
    private Unmarshaller unmarshallerMock;
    private InputStream inputStreamMock;
    private OutputStream outputStreamMock;

    @Before
    public void init() {
        initMocks();
        xmlMarshaller = new XmlMarshaller(marshallerMock, unmarshallerMock);
    }

    public void initMocks() {
        marshallerMock = mock(Marshaller.class);
        unmarshallerMock = mock(Unmarshaller.class);
        inputStreamMock = mock(InputStream.class);
        outputStreamMock = mock(OutputStream.class);
    }

    @Test
    public void xmlMarshallerCorrectlyInstantiatesUsingDefaultMarshallers() {
        xmlMarshaller = new XmlMarshaller();
        assertNotNull(xmlMarshaller.getMarshaller());
        assertNotNull(xmlMarshaller.getUnmarshaller());
    }

    @Test
    public void unmarshalMethodUnmarshallsTheSpecifiedInputStreamToAnXmlRootNodeAssembler() throws JAXBException {
        when(unmarshallerMock.unmarshal(inputStreamMock)).thenReturn(xmlRootNodeAssemblerMock);
        xmlMarshaller.unmarshal(inputStreamMock);
        assertSame(xmlRootNodeAssemblerMock, xmlMarshaller.getRootNodeAssembler());
    }

    @Test
    public void marshalMethodMarshallsTheSpecifiedXmlRootNodeAssemblerToAnOutputStream() throws JAXBException {
        xmlMarshaller.marshal(outputStreamMock);
        verify(marshallerMock, times(1)).marshal(xmlRootNodeAssemblerMock, outputStreamMock);
    }
}
