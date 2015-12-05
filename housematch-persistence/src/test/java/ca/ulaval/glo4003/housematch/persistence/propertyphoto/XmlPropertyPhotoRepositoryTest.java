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
import ca.ulaval.glo4003.housematch.utils.FileUtilsWrapper;
import ca.ulaval.glo4003.housematch.utils.ResourceLoader;
import ca.ulaval.glo4003.housematch.utils.ThumbnailGenerator;

public class XmlPropertyPhotoRepositoryTest {

    private static final PropertyPhotoStatus SAMPLE_PROPERTY_PHOTO_STATUS = PropertyPhotoStatus.APPROVED;
    private static final PropertyPhotoStatus ANOTHER_SAMPLE_PROPERTY_PHOTO_STATUS = PropertyPhotoStatus.REJECTED;

    private XmlRepositoryMarshaller<XmlPropertyPhotoRootElement> xmlRepositoryMarshallerMock;
    private XmlPropertyPhotoAdapter xmlPropertyPhotoAdapterMock;
    private XmlPropertyPhotoRootElement xmlPropertyPhotoRootElementMock;
    private PropertyPhoto propertyPhotoMock;
    private PropertyPhoto anotherPropertyPhotoMock;
    private ResourceLoader resourceLoaderMock;
    private FileUtilsWrapper fileUtilsWrapperMock;
    private ThumbnailGenerator thumbnailGeneratorMock;

    private byte[] fileBytes;
    private XmlPropertyPhotoRepository xmlPropertyPhotoRepository;

    @Before
    public void init() throws Exception {
        initMocks();
        initStubs();
        xmlPropertyPhotoRepository = new XmlPropertyPhotoRepository(xmlRepositoryMarshallerMock, xmlPropertyPhotoAdapterMock,
                resourceLoaderMock, fileUtilsWrapperMock, thumbnailGeneratorMock);
    }

    @SuppressWarnings("unchecked")
    private void initMocks() {
        propertyPhotoMock = mock(PropertyPhoto.class);
        anotherPropertyPhotoMock = mock(PropertyPhoto.class);
        xmlPropertyPhotoAdapterMock = mock(XmlPropertyPhotoAdapter.class);
        xmlPropertyPhotoRootElementMock = mock(XmlPropertyPhotoRootElement.class);
        xmlRepositoryMarshallerMock = mock(XmlRepositoryMarshaller.class);
        resourceLoaderMock = mock(ResourceLoader.class);
        fileUtilsWrapperMock = mock(FileUtilsWrapper.class);
        thumbnailGeneratorMock = mock(ThumbnailGenerator.class);
    }

    private void initStubs() throws Exception, IOException {
        when(xmlRepositoryMarshallerMock.unmarshal()).thenReturn(xmlPropertyPhotoRootElementMock);
        when(fileUtilsWrapperMock.readByteArrayFromFile(anyString())).thenReturn(fileBytes);
        when(propertyPhotoMock.getStatus()).thenReturn(SAMPLE_PROPERTY_PHOTO_STATUS);
        when(anotherPropertyPhotoMock.getStatus()).thenReturn(ANOTHER_SAMPLE_PROPERTY_PHOTO_STATUS);
    }

    @Test
    public void persistingPropertyPersistsPhotoToRepository() throws Exception {
        xmlPropertyPhotoRepository.persist(propertyPhotoMock, fileBytes);

        PropertyPhoto propertyPhoto = xmlPropertyPhotoRepository.getByHashCode(propertyPhotoMock.hashCode());
        assertSame(propertyPhotoMock, propertyPhoto);
    }

    @Test
    public void persistingPhotoMarshallsThePhotoInTheRepositoryMarshaller() throws Exception {
        xmlPropertyPhotoRepository.persist(propertyPhotoMock, fileBytes);

        verify(xmlPropertyPhotoRootElementMock).setPropertyPhotos(anyCollectionOf(PropertyPhoto.class));
        verify(xmlRepositoryMarshallerMock).marshal(xmlPropertyPhotoRootElementMock);
    }

    @Test(expected = PropertyPhotoAlreadyExistsException.class)
    public void persistingPhotoWhichAlreadyExistsThrowsPropertyPhotoAlreadyExistsException() throws Exception {
        xmlPropertyPhotoRepository.persist(propertyPhotoMock, fileBytes);
        xmlPropertyPhotoRepository.persist(propertyPhotoMock, fileBytes);
    }

    @Test
    public void persistingPhotoWritesByteArrayToFile() throws Exception {
        xmlPropertyPhotoRepository.persist(propertyPhotoMock, fileBytes);
        verify(fileUtilsWrapperMock).writeByteArrayToFile(eq(fileBytes), anyString());
    }

    @Test
    public void persistingPhotoSavesThumbnailUsingTheThumbnailGenerator() throws Exception {
        xmlPropertyPhotoRepository.persist(propertyPhotoMock, fileBytes);
        verify(thumbnailGeneratorMock).saveThumbnail(anyString(), anyString(), any(Dimension.class), anyString());
    }

    @Test
    public void gettingPhotoByHashCodeRetrievesThePhotoFromTheSpecifiedHashCode() throws Exception {
        xmlPropertyPhotoRepository.persist(propertyPhotoMock, fileBytes);
        PropertyPhoto propertyPhoto = xmlPropertyPhotoRepository.getByHashCode(propertyPhotoMock.hashCode());
        assertSame(propertyPhotoMock, propertyPhoto);
    }

    @Test
    public void updatingPhotoUpdatesPhotoToRepository() throws Exception {
        xmlPropertyPhotoRepository.persist(propertyPhotoMock, fileBytes);
        xmlPropertyPhotoRepository.update(propertyPhotoMock);
        verify(xmlRepositoryMarshallerMock, times(2)).marshal(xmlPropertyPhotoRootElementMock);
    }

    @Test(expected = IllegalStateException.class)
    public void updatingNonExistingPhotoThrowsIllegalStateException() throws Exception {
        xmlPropertyPhotoRepository.update(propertyPhotoMock);
    }

    @Test
    public void gettingPhotoByStatusGetsThePhotoByStatus() throws Exception {
        xmlPropertyPhotoRepository.persist(propertyPhotoMock, fileBytes);
        xmlPropertyPhotoRepository.persist(anotherPropertyPhotoMock, fileBytes);

        List<PropertyPhoto> properties = xmlPropertyPhotoRepository.getByStatus(SAMPLE_PROPERTY_PHOTO_STATUS);

        assertThat(properties, containsInAnyOrder(propertyPhotoMock));
    }

    @Test
    public void gettingThumbnailDataRetrievesTheThumbnailDataFromTheFileSystem() throws Exception {
        xmlPropertyPhotoRepository.persist(propertyPhotoMock, fileBytes);
        byte[] returnedFileBytes = xmlPropertyPhotoRepository.getThumbnailData(propertyPhotoMock);
        assertSame(returnedFileBytes, fileBytes);
    }

    @Test(expected = PropertyPhotoNotFoundException.class)
    public void gettingThumbnailDataThrowsPropertyPhotoNotFoundExceptionOnFileNotFoundException() throws Exception {
        doThrow(new FileNotFoundException()).when(fileUtilsWrapperMock).readByteArrayFromFile(anyString());
        xmlPropertyPhotoRepository.getThumbnailData(propertyPhotoMock);
    }

    @Test
    public void deletingPhotoDeletesThePhotoFromTheRepository() throws Exception, IOException {
        xmlPropertyPhotoRepository.persist(propertyPhotoMock, fileBytes);
        xmlPropertyPhotoRepository.delete(propertyPhotoMock);

        try {
            xmlPropertyPhotoRepository.getByHashCode(propertyPhotoMock.hashCode());
            fail();
        } catch (PropertyPhotoNotFoundException ex) {
            return;
        }
    }

    @Test(expected = PropertyPhotoNotFoundException.class)
    public void deletingNonExistingPhotoThrowsPropertyPhotoNotFoundException() throws Exception {
        xmlPropertyPhotoRepository.delete(propertyPhotoMock);
    }

    @Test
    public void deletingPhotoDeletesFileFromFileSystem() throws Exception {
        xmlPropertyPhotoRepository.persist(propertyPhotoMock, fileBytes);
        xmlPropertyPhotoRepository.delete(propertyPhotoMock);
        verify(fileUtilsWrapperMock, times(2)).delete(anyString());
    }

}
