package ca.ulaval.glo4003.housematch.services.propertyphoto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyRepository;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoFactory;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoRepository;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoStatus;

public class PropertyPhotoServiceTest {

    private static final String SAMPLE_FILE_NAME = "text.txt";
    private static final int SAMPLE_HASH_CODE = 22;
    private static final byte[] SAMPLE_BYTES = new byte[3];

    private PropertyRepository propertyRepositoryMock;
    private PropertyPhotoRepository propertyPhotoRepositoryMock;
    private PropertyPhotoFactory propertyPhotoFactoryMock;
    private PropertyPhoto propertyPhotoMock;
    private Property propertyMock;

    List<PropertyPhoto> photos = new ArrayList<>();

    private PropertyPhotoService propertyPhotoService;

    @Before
    public void init() throws Exception {
        initMocks();
        initStubs();
        propertyPhotoService = new PropertyPhotoService(propertyRepositoryMock, propertyPhotoRepositoryMock, propertyPhotoFactoryMock);
    }

    private void initMocks() {
        propertyRepositoryMock = mock(PropertyRepository.class);
        propertyPhotoRepositoryMock = mock(PropertyPhotoRepository.class);
        propertyPhotoFactoryMock = mock(PropertyPhotoFactory.class);
        propertyPhotoMock = mock(PropertyPhoto.class);
        propertyMock = mock(Property.class);
    }

    private void initStubs() throws Exception {
        when(propertyPhotoFactoryMock.createPropertyPhoto(SAMPLE_BYTES, SAMPLE_FILE_NAME)).thenReturn(propertyPhotoMock);
        when(propertyPhotoRepositoryMock.getByHashCode(SAMPLE_HASH_CODE)).thenReturn(propertyPhotoMock);
        when(propertyMock.getPhotoByHashCode(SAMPLE_HASH_CODE)).thenReturn(propertyPhotoMock);
    }

    @Test
    public void gettingPhotoThumbnailDataGetsThePhotoFromThePropertyPhotoRepository() throws Exception {
        propertyPhotoService.getPhotoThumbnailData(SAMPLE_HASH_CODE);
        verify(propertyPhotoRepositoryMock).getByHashCode(SAMPLE_HASH_CODE);
    }

    @Test
    public void gettingPhotoThumbnailDataGetsThumbnailDataFromThePropertyPhotoRepository() throws Exception {
        propertyPhotoService.getPhotoThumbnailData(SAMPLE_HASH_CODE);
        verify(propertyPhotoRepositoryMock).getThumbnailData(propertyPhotoMock);
    }

    @Test(expected = PropertyPhotoServiceException.class)
    public void gettingPhotoThrowsPropertyPhotoServiceExceptionOnIOException() throws Exception {
        doThrow(new IOException()).when(propertyPhotoRepositoryMock).getThumbnailData(propertyPhotoMock);
        propertyPhotoService.getPhotoThumbnailData(SAMPLE_HASH_CODE);
    }

    @Test
    public void addingPhotoCreatesPhotoFromThePropertyPhotoFactory() throws Exception {
        propertyPhotoService.addPhoto(propertyMock, SAMPLE_BYTES, SAMPLE_FILE_NAME);
        verify(propertyPhotoFactoryMock).createPropertyPhoto(SAMPLE_BYTES, SAMPLE_FILE_NAME);
    }

    @Test
    public void addingPhotoPersistsPhotoToPropertyPhotoRepository() throws Exception {
        propertyPhotoService.addPhoto(propertyMock, SAMPLE_BYTES, SAMPLE_FILE_NAME);
        verify(propertyPhotoRepositoryMock).persist(propertyPhotoMock, SAMPLE_BYTES);
    }

    @Test
    public void addingPhotoAddsPhotoToProperty() throws Exception {
        propertyPhotoService.addPhoto(propertyMock, SAMPLE_BYTES, SAMPLE_FILE_NAME);
        verify(propertyMock).addPhoto(propertyPhotoMock);
    }

    @Test
    public void addingPhotoUpdatesPhotoToPropertyRepository() throws Exception {
        propertyPhotoService.addPhoto(propertyMock, SAMPLE_BYTES, SAMPLE_FILE_NAME);
        verify(propertyRepositoryMock).update(propertyMock);
    }

    @Test
    public void addingPhotoReturnsThePropertyHashCode() throws Exception {
        int returnedHashCode = propertyPhotoService.addPhoto(propertyMock, SAMPLE_BYTES, SAMPLE_FILE_NAME);
        assertEquals(propertyPhotoMock.hashCode(), returnedHashCode);
    }

    @Test(expected = PropertyPhotoServiceException.class)
    public void addingPhotoThrowsPropertyPhotoServiceExceptionOnIOException() throws Exception {
        doThrow(new IOException()).when(propertyPhotoRepositoryMock).persist(propertyPhotoMock, SAMPLE_BYTES);
        propertyPhotoService.addPhoto(propertyMock, SAMPLE_BYTES, SAMPLE_FILE_NAME);
    }

    @Test
    public void removingPhotoGetsThePhotosFromTheProperty() throws Exception {
        propertyPhotoService.removePhoto(propertyMock, SAMPLE_HASH_CODE);
        verify(propertyMock).getPhotoByHashCode(SAMPLE_HASH_CODE);
    }

    @Test
    public void removingPhotoDeletesThePhotoFromThePropertyPhotoRepository() throws Exception {
        propertyPhotoService.removePhoto(propertyMock, SAMPLE_HASH_CODE);
        verify(propertyPhotoRepositoryMock).delete(propertyPhotoMock);
    }

    @Test
    public void removingPhotoRemovePhotoFromThePropertyObject() throws Exception {
        propertyPhotoService.removePhoto(propertyMock, SAMPLE_HASH_CODE);
        verify(propertyMock).removePhoto(propertyPhotoMock);
    }

    @Test
    public void removingPhotoUpdatesThePropertyToThePropertyRepository() throws Exception {
        propertyPhotoService.removePhoto(propertyMock, SAMPLE_HASH_CODE);
        verify(propertyRepositoryMock).update(propertyMock);
    }

    @Test(expected = PropertyPhotoServiceException.class)
    public void removingPhotoThrowsPropertyPhotoServiceExceptionOnIOException() throws Exception {
        doThrow(new IOException()).when(propertyPhotoRepositoryMock).delete(propertyPhotoMock);
        propertyPhotoService.removePhoto(propertyMock, SAMPLE_HASH_CODE);
    }

    @Test
    public void approvingPhotoGetsThePhotoFromThePropertyPhotoRepository() throws Exception {
        propertyPhotoService.approvePhoto(SAMPLE_HASH_CODE);
        verify(propertyPhotoRepositoryMock).getByHashCode(SAMPLE_HASH_CODE);
    }

    @Test
    public void approvingPhotoUpdatesThePhotoStatusOfThePropertyPhotoStatus() throws Exception {
        propertyPhotoService.approvePhoto(SAMPLE_HASH_CODE);
        verify(propertyPhotoMock).updateStatus(PropertyPhotoStatus.APPROVED);
    }

    @Test
    public void approvingPhotoUpdatesThePhotoToThePropertyPhotoRepository() throws Exception {
        propertyPhotoService.approvePhoto(SAMPLE_HASH_CODE);
        verify(propertyPhotoRepositoryMock).update(propertyPhotoMock);
    }

    @Test
    public void rejectingPhotoGetsThePhotoFromThePropertyPhotoRepository() throws Exception {
        propertyPhotoService.rejectPhoto(SAMPLE_HASH_CODE);
        verify(propertyPhotoRepositoryMock).getByHashCode(SAMPLE_HASH_CODE);
    }

    @Test
    public void rejectingPhotoUpdatesThePhotoStatusOfThePropertyPhotoStatus() throws Exception {
        propertyPhotoService.rejectPhoto(SAMPLE_HASH_CODE);
        verify(propertyPhotoMock).updateStatus(PropertyPhotoStatus.REJECTED);
    }

    @Test
    public void rejectingPhotoDeletesThePhotoFromThePropertyPhotoRepository() throws Exception {
        propertyPhotoService.rejectPhoto(SAMPLE_HASH_CODE);
        verify(propertyPhotoRepositoryMock).delete(propertyPhotoMock);
    }

    @Test(expected = PropertyPhotoServiceException.class)
    public void rejectingPhotoThrowsPropertyPhotoServiceExceptionOnIOException() throws Exception {
        doThrow(new IOException()).when(propertyPhotoRepositoryMock).delete(propertyPhotoMock);
        propertyPhotoService.rejectPhoto(SAMPLE_HASH_CODE);
        verify(propertyPhotoRepositoryMock).delete(propertyPhotoMock);
    }

    @Test
    public void gettingPhotosWaitingForApprovalReturnsPhotosWaitingForApprovalFromThePropertyPhotoRepository() {
        when(propertyPhotoRepositoryMock.getByStatus(any(PropertyPhotoStatus.class))).thenReturn(photos);
        List<PropertyPhoto> returnedPhotos = propertyPhotoService.getPhotosWaitingForApproval();
        assertSame(photos, returnedPhotos);
    }

}
