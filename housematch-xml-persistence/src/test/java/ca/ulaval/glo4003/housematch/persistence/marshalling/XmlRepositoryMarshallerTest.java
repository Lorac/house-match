package ca.ulaval.glo4003.housematch.persistence.marshalling;

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.utils.ResourceLoader;

public class XmlRepositoryMarshallerTest {

    private static final String SAMPLE_RESOURCE_NAME = "resource";
    private static final Object SAMPLE_OBJECT = new Object();

    private ResourceLoader resourceLoaderMock;
    private Marshaller marshallerMock;
    private Unmarshaller unmarshallerMock;
    private InputStream inputStreamMock;
    private OutputStream outputStreamMock;
    private XmlAdapter<Object, Object> xmlAdapterMock;

    private XmlRepositoryMarshaller<Object> xmlRepositoryMarshaller;

    @Before
    public void init() throws Exception {
        initMocks();
        stubMethods();
        xmlRepositoryMarshaller = new XmlRepositoryMarshaller<Object>(marshallerMock, unmarshallerMock,
                resourceLoaderMock, SAMPLE_RESOURCE_NAME);
    }

    @SuppressWarnings("unchecked")
    public void initMocks() {
        resourceLoaderMock = mock(ResourceLoader.class);
        marshallerMock = mock(Marshaller.class);
        unmarshallerMock = mock(Unmarshaller.class);
        inputStreamMock = mock(InputStream.class);
        outputStreamMock = mock(OutputStream.class);
        xmlAdapterMock = mock(XmlAdapter.class);
    }

    private void stubMethods() throws Exception {
        when(resourceLoaderMock.loadResourceAsInputStream(any(XmlRepositoryMarshaller.class), eq(SAMPLE_RESOURCE_NAME)))
                .thenReturn(inputStreamMock);
        when(resourceLoaderMock.loadResourceAsOutputStream(any(XmlRepositoryMarshaller.class),
                eq(SAMPLE_RESOURCE_NAME))).thenReturn(outputStreamMock);
        when(unmarshallerMock.unmarshal(any(InputStream.class))).thenReturn(SAMPLE_OBJECT);
    }

    @Test
    public void theSpecifiedResourceIsLoadedAsInputStreamDuringUnmarshalling() throws Exception {
        xmlRepositoryMarshaller.unmarshal();
        verify(resourceLoaderMock).loadResourceAsInputStream(xmlRepositoryMarshaller, SAMPLE_RESOURCE_NAME);
    }

    @Test
    public void theObjectIsUnmarshalledFromTheSpecifiedInputStreamDuringUnmarshalling() throws Exception {
        xmlRepositoryMarshaller.unmarshal();
        verify(unmarshallerMock).unmarshal(inputStreamMock);
    }

    @Test
    public void theSpecifiedInputStreamIsUnmarshalledToAnObjectDuringUnmarshalling() throws Exception {
        when(unmarshallerMock.unmarshal(inputStreamMock)).thenReturn(SAMPLE_OBJECT);
        Object unmarshalledObject = xmlRepositoryMarshaller.unmarshal();
        assertSame(SAMPLE_OBJECT, unmarshalledObject);
    }

    @Test
    public void theSpecifiedResourceIsLoadedAsAnOutputStreamDuringMarshalling() throws Exception {
        xmlRepositoryMarshaller.marshal(SAMPLE_OBJECT);
        verify(resourceLoaderMock).loadResourceAsOutputStream(xmlRepositoryMarshaller, SAMPLE_RESOURCE_NAME);
    }

    @Test
    public void theObjectIsMarshalledToAnOutputStreamDuringMarshalling() throws Exception {
        xmlRepositoryMarshaller.marshal(SAMPLE_OBJECT);
        verify(marshallerMock).marshal(SAMPLE_OBJECT, outputStreamMock);
    }

    @Test
    public void settingTheMarshallingAdaptersSetsTheMarshallingAdaptersOnTheMarshallers() {
        xmlRepositoryMarshaller.setMarshallingAdapters(xmlAdapterMock);

        verify(marshallerMock).setAdapter(xmlAdapterMock);
        verify(unmarshallerMock).setAdapter(xmlAdapterMock);
    }
}
