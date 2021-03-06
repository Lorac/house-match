package ca.ulaval.glo4003.housematch.persistence.propertyphoto;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoNotFoundException;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoStatus;
import ca.ulaval.glo4003.housematch.persistence.marshalling.XmlRepositoryMarshaller;
import ca.ulaval.glo4003.housematch.utils.IOWrapper;
import ca.ulaval.glo4003.housematch.utils.ResourceLoader;
import ca.ulaval.glo4003.housematch.utils.ThumbnailGenerator;

public class XmlPropertyPhotoRepositoryTest {

    private static final PropertyPhotoStatus SAMPLE_PROPERTY_PHOTO_STATUS = PropertyPhotoStatus.APPROVED;
    private static final PropertyPhotoStatus ANOTHER_SAMPLE_PROPERTY_PHOTO_STATUS = PropertyPhotoStatus.REJECTED;
    private static final byte[] SAMPLE_BYTES = new byte[3];

    private XmlRepositoryMarshaller<XmlPropertyPhotoRootElement> xmlRepositoryMarshallerMock;
    private XmlPropertyPhotoAdapter xmlPropertyPhotoAdapterMock;
    private XmlPropertyPhotoRootElement xmlPropertyPhotoRootElementMock;
    private PropertyPhoto propertyPhotoMock;
    private PropertyPhoto anotherPropertyPhotoMock;
    private ResourceLoader resourceLoaderMock;
    private IOWrapper ioWrapperMock;
    private ThumbnailGenerator thumbnailGeneratorMock;

    private XmlPropertyPhotoRepository xmlPropertyPhotoRepository;

    @Before
    public void init() throws Exception {
        initMocks();
        initStubs();
        xmlPropertyPhotoRepository = new XmlPropertyPhotoRepository(xmlRepositoryMarshallerMock, xmlPropertyPhotoAdapterMock,
                resourceLoaderMock, ioWrapperMock, thumbnailGeneratorMock);
    }

    @SuppressWarnings("unchecked")
    private void initMocks() {
        propertyPhotoMock = mock(PropertyPhoto.class);
        anotherPropertyPhotoMock = mock(PropertyPhoto.class);
        xmlPropertyPhotoAdapterMock = mock(XmlPropertyPhotoAdapter.class);
        xmlPropertyPhotoRootElementMock = mock(XmlPropertyPhotoRootElement.class);
        xmlRepositoryMarshallerMock = mock(XmlRepositoryMarshaller.class);
        resourceLoaderMock = mock(ResourceLoader.class);
        ioWrapperMock = mock(IOWrapper.class);
        thumbnailGeneratorMock = mock(ThumbnailGenerator.class);
    }

    private void initStubs() throws Exception, IOException {
        when(xmlRepositoryMarshallerMock.unmarshal()).thenReturn(xmlPropertyPhotoRootElementMock);
        when(ioWrapperMock.readByteArrayFromFile(anyString())).thenReturn(SAMPLE_BYTES);
        when(propertyPhotoMock.getStatus()).thenReturn(SAMPLE_PROPERTY_PHOTO_STATUS);
        when(anotherPropertyPhotoMock.getStatus()).thenReturn(ANOTHER_SAMPLE_PROPERTY_PHOTO_STATUS);
    }

    @Test
    public void persistingPropertyPersistsPhotoToRepository() throws Exception {
        xmlPropertyPhotoRepository.persist(propertyPhotoMock, SAMPLE_BYTES);

        PropertyPhoto propertyPhoto = xmlPropertyPhotoRepository.getByHashCode(propertyPhotoMock.hashCode());
        assertSame(propertyPhotoMock, propertyPhoto);
    }

    @Test
    public void persistingPhotoMarshallsThePhotoInTheRepositoryMarshaller() throws Exception {
        xmlPropertyPhotoRepository.persist(propertyPhotoMock, SAMPLE_BYTES);

        verify(xmlPropertyPhotoRootElementMock).setPropertyPhotos(anyCollectionOf(PropertyPhoto.class));
        verify(xmlRepositoryMarshallerMock).marshal(xmlPropertyPhotoRootElementMock);
    }

    @Test(expected = PropertyPhotoAlreadyExistsException.class)
    public void persistingPhotoWhichAlreadyExistsThrowsPropertyPhotoAlreadyExistsException() throws Exception {
        xmlPropertyPhotoRepository.persist(propertyPhotoMock, SAMPLE_BYTES);
        xmlPropertyPhotoRepository.persist(propertyPhotoMock, SAMPLE_BYTES);
    }

    @Test
    public void persistingPhotoWritesByteArrayToFile() throws Exception {
        xmlPropertyPhotoRepository.persist(propertyPhotoMock, SAMPLE_BYTES);
        verify(ioWrapperMock).writeByteArrayToFile(eq(SAMPLE_BYTES), anyString());
    }

    @Test
    public void persistingPhotoSavesThumbnailUsingTheThumbnailGenerator() throws Exception {
        xmlPropertyPhotoRepository.persist(propertyPhotoMock, SAMPLE_BYTES);
        verify(thumbnailGeneratorMock).saveThumbnail(anyString(), anyString(), any(Dimension.class), anyString());
    }

    @Test
    public void gettingPhotoByHashCodeRetrievesThePhotoFromTheSpecifiedHashCode() throws Exception {
        xmlPropertyPhotoRepository.persist(propertyPhotoMock, SAMPLE_BYTES);
        PropertyPhoto propertyPhoto = xmlPropertyPhotoRepository.getByHashCode(propertyPhotoMock.hashCode());
        assertSame(propertyPhotoMock, propertyPhoto);
    }

    @Test
    public void updatingPhotoUpdatesPhotoToRepository() throws Exception {
        xmlPropertyPhotoRepository.persist(propertyPhotoMock, SAMPLE_BYTES);
        xmlPropertyPhotoRepository.update(propertyPhotoMock);
        verify(xmlRepositoryMarshallerMock, times(2)).marshal(xmlPropertyPhotoRootElementMock);
    }

    @Test(expected = IllegalStateException.class)
    public void updatingNonExistingPhotoThrowsIllegalStateException() throws Exception {
        xmlPropertyPhotoRepository.update(propertyPhotoMock);
    }

    @Test
    public void gettingPhotoByStatusGetsThePhotoByStatus() throws Exception {
        xmlPropertyPhotoRepository.persist(propertyPhotoMock, SAMPLE_BYTES);
        xmlPropertyPhotoRepository.persist(anotherPropertyPhotoMock, SAMPLE_BYTES);

        List<PropertyPhoto> properties = xmlPropertyPhotoRepository.getByStatus(SAMPLE_PROPERTY_PHOTO_STATUS);

        assertThat(properties, containsInAnyOrder(propertyPhotoMock));
    }

    @Test
    public void gettingThumbnailDataRetrievesTheThumbnailDataFromTheFileSystem() throws Exception {
        xmlPropertyPhotoRepository.persist(propertyPhotoMock, SAMPLE_BYTES);
        byte[] returnedFileBytes = xmlPropertyPhotoRepository.getThumbnailData(propertyPhotoMock);
        assertSame(returnedFileBytes, SAMPLE_BYTES);
    }

    @Test(expected = PropertyPhotoNotFoundException.class)
    public void gettingThumbnailDataThrowsPropertyPhotoNotFoundExceptionOnFileNotFoundException() throws Exception {
        doThrow(new FileNotFoundException()).when(ioWrapperMock).readByteArrayFromFile(anyString());
        xmlPropertyPhotoRepository.getThumbnailData(propertyPhotoMock);
    }

    @Test
    public void deletingPhotoDeletesThePhotoFromTheRepository() throws Exception, IOException {
        xmlPropertyPhotoRepository.persist(propertyPhotoMock, SAMPLE_BYTES);
        xmlPropertyPhotoRepository.delete(propertyPhotoMock);

        try {
            xmlPropertyPhotoRepository.getByHashCode(propertyPhotoMock.hashCode());
            fail();
        } catch (PropertyPhotoNotFoundException ex) {
            return;
        }
    }

    @Test
    public void deletingPhotoMarshallsTheRepository() throws Exception {
        xmlPropertyPhotoRepository.persist(propertyPhotoMock, SAMPLE_BYTES);
        xmlPropertyPhotoRepository.delete(propertyPhotoMock);
        verify(xmlRepositoryMarshallerMock, times(2)).marshal(xmlPropertyPhotoRootElementMock);
    }

    @Test(expected = PropertyPhotoNotFoundException.class)
    public void deletingNonExistingPhotoThrowsPropertyPhotoNotFoundException() throws Exception {
        xmlPropertyPhotoRepository.delete(propertyPhotoMock);
    }

    @Test
    public void deletingPhotoDeletesFileFromFileSystem() throws Exception {
        xmlPropertyPhotoRepository.persist(propertyPhotoMock, SAMPLE_BYTES);
        xmlPropertyPhotoRepository.delete(propertyPhotoMock);
        verify(ioWrapperMock, times(2)).deleteFile(anyString());
    }

}
