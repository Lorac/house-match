package ca.ulaval.glo4003.housematch.persistence.propertyphoto;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoFactory;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoStatus;

public class XmlPropertyPhotoAdapterTest {

    private static final PropertyPhotoStatus SAMPLE_PROPERTY_PHOTO_STATUS = PropertyPhotoStatus.APPROVED;
    private static final int SAMPLE_HASH_CODE = 3434;

    private PropertyPhotoFactory propertyPhotoFactoryMock;
    private PropertyPhoto propertyPhotoMock;
    private XmlPropertyPhoto xmlPropertyPhotoMock;

    private XmlPropertyPhotoAdapter xmlPropertyPhotoAdapter;

    @Before
    public void init() throws Exception {
        initMocks();
        initStubs();
        xmlPropertyPhotoAdapter = new XmlPropertyPhotoAdapter(propertyPhotoFactoryMock);
    }

    private void initMocks() {
        propertyPhotoFactoryMock = mock(PropertyPhotoFactory.class);
        initPropertyMock();
        initXmlPropertyPhotoMock();
    }

    private void initPropertyMock() {
        propertyPhotoMock = mock(PropertyPhoto.class);
        when(propertyPhotoMock.getStatus()).thenReturn(SAMPLE_PROPERTY_PHOTO_STATUS);
    }

    private void initXmlPropertyPhotoMock() {
        xmlPropertyPhotoMock = mock(XmlPropertyPhoto.class);
        xmlPropertyPhotoMock.hashCode = propertyPhotoMock.hashCode();
        xmlPropertyPhotoMock.status = SAMPLE_PROPERTY_PHOTO_STATUS;
    }

    private void initStubs() {
        when(propertyPhotoFactoryMock.createPropertyPhoto(SAMPLE_HASH_CODE)).thenReturn(propertyPhotoMock);
    }

    @Test
    public void propertyAttributesAreConvertedDuringMarshalling() throws Exception {
        xmlPropertyPhotoAdapter.marshal(propertyPhotoMock);

        assertEquals(propertyPhotoMock.hashCode(), xmlPropertyPhotoMock.hashCode);
        assertEquals(propertyPhotoMock.getStatus(), xmlPropertyPhotoMock.status);
    }

    @Test
    public void xmlPropertyAttributesAreConvertedDuringUnmarshalling() throws Exception {
        xmlPropertyPhotoAdapter.unmarshal(xmlPropertyPhotoMock);

        assertEquals(xmlPropertyPhotoMock.hashCode, propertyPhotoMock.hashCode());
        assertEquals(xmlPropertyPhotoMock.status, propertyPhotoMock.getStatus());
    }
}
