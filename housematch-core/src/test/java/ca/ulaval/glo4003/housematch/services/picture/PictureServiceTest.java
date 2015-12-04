package ca.ulaval.glo4003.housematch.services.picture;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.never;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.picture.Picture;
import ca.ulaval.glo4003.housematch.domain.picture.PictureAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.picture.PictureFactory;
import ca.ulaval.glo4003.housematch.domain.picture.PictureNotFoundException;
import ca.ulaval.glo4003.housematch.domain.picture.PictureRepository;
import ca.ulaval.glo4003.housematch.domain.picture.PictureStatus;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.services.property.PropertyService;
import ca.ulaval.glo4003.housematch.services.property.PropertyServiceException;

public class PictureServiceTest {
    private static final String SAMPLE_PATH = "this/is/a/nice/path.png";
    private static final Integer SAMPLE_HASH_CODE = 123455;
    private static final BigDecimal SAMPLE_SELLING_PRICE = BigDecimal.valueOf(1000000);
    
    private PictureFactory pictureFactoryMock;
    private PictureRepository pictureRepositoryMock;
    private PropertyService propertyServiceMock;
    private Property propertyMock;
    private Picture pictureMock;
    private PropertyDetails propertyDetailsMock;
    
    private PictureService pictureService;
    
    @Before
    public void init() throws PropertyNotFoundException, PictureNotFoundException {
        initMocks();
        initStubs();
        pictureService = new PictureService(pictureFactoryMock, pictureRepositoryMock, propertyServiceMock);
    }
    
    private void initMocks() {
        pictureFactoryMock = mock(PictureFactory.class);
        pictureRepositoryMock = mock(PictureRepository.class);
        propertyServiceMock = mock(PropertyService.class);
        propertyMock = mock(Property.class);
        pictureMock = mock(Picture.class);
        propertyDetailsMock = mock(PropertyDetails.class);
    }
    
    private void initStubs() throws PropertyNotFoundException, PictureNotFoundException {
        when(pictureRepositoryMock.getPictureByHashCode(any(Integer.class))).thenReturn(pictureMock);
        when(pictureFactoryMock.createPicture(any(String.class))).thenReturn(pictureMock);
        when(propertyServiceMock.getPropertyByHashCode(any(Integer.class))).thenReturn(propertyMock);
        when(propertyMock.getPropertyDetails()).thenReturn(propertyDetailsMock);
        when(propertyMock.getSellingPrice()).thenReturn(SAMPLE_SELLING_PRICE);
    }
    
    @Test
    public void addingAPictureToAPropertyCallsAFactoryToCreateTheNewPicture() throws PictureServiceException {
        pictureService.addPictureToProperty(SAMPLE_PATH, SAMPLE_HASH_CODE);
        verify(pictureFactoryMock).createPicture(SAMPLE_PATH);
    }
    
    @Test
    public void addingAPictureToAPropertyCallsThePropertyServiceToFetchTheProperty() throws PictureServiceException, PropertyNotFoundException {
        pictureService.addPictureToProperty(SAMPLE_PATH, SAMPLE_HASH_CODE);
        verify(propertyServiceMock).getPropertyByHashCode(SAMPLE_HASH_CODE);
    }
    
    @Test
    public void addingAPictureToAPropertyPersistsThePicture() throws PictureServiceException, PictureAlreadyExistsException {
        pictureService.addPictureToProperty(SAMPLE_PATH, SAMPLE_HASH_CODE);
        verify(pictureRepositoryMock).persist(any(Picture.class));
    }
    
    @Test
    public void addingAPictureToAPropertyUpdatesTheChangesToTheProperty() throws PictureServiceException, PropertyServiceException {
        pictureService.addPictureToProperty(SAMPLE_PATH, SAMPLE_HASH_CODE);
        verify(propertyServiceMock).updateProperty(propertyMock, propertyDetailsMock, SAMPLE_SELLING_PRICE);
    }
    
    @Test(expected=PictureServiceException.class)
    public void addingAPictureThatAlreadyExistsDoesNotPersistThePicture() throws PictureAlreadyExistsException , PictureServiceException {
        doThrow(new PictureAlreadyExistsException()).when(pictureRepositoryMock).persist(pictureMock);
        pictureService.addPictureToProperty(SAMPLE_PATH, SAMPLE_HASH_CODE);
    }
    
    @Test(expected=PictureServiceException.class)
    public void addingAPictureThatAlreadyExistsStopsThePictureToPropertyLinkingProcess() throws PictureAlreadyExistsException , PictureServiceException, PropertyServiceException {
        doThrow(new PictureAlreadyExistsException()).when(pictureRepositoryMock).persist(pictureMock);
        pictureService.addPictureToProperty(SAMPLE_PATH, SAMPLE_HASH_CODE);
        verifyZeroInteractions(propertyMock);
        verify(propertyServiceMock, never()).updateProperty(any(Property.class), any(PropertyDetails.class), any(BigDecimal.class));
    }
    
    @Test
    public void removingAPictureFromAPropertyCallsThePropertyServiceToFetchTheProperty() throws PictureServiceException, PropertyNotFoundException {
        pictureService.removePictureFromProperty(SAMPLE_HASH_CODE, SAMPLE_HASH_CODE);
        verify(propertyServiceMock).getPropertyByHashCode(any(Integer.class));
    }
    
    @Test
    public void removingAPictureFromAPropertyRemovesThePropertyFromThePictureRepository() throws PictureNotFoundException, PictureServiceException {
        pictureService.removePictureFromProperty(SAMPLE_HASH_CODE, SAMPLE_HASH_CODE);
        verify(pictureRepositoryMock).removePictureByHashCode(SAMPLE_HASH_CODE);
    }
    
    @Test
    public void removingAPictureFromAPropertyCallsThePictureRemovalMethodOfTheProperty() throws PictureServiceException, PictureNotFoundException {
        pictureService.removePictureFromProperty(SAMPLE_HASH_CODE, SAMPLE_HASH_CODE);
        verify(propertyMock).removePropertyPicture(any(Picture.class));
    }
    
    @Test
    public void removingAPictureFromAPropertyUpdatesTheChangesToThePropertyWithThePropertyServices() throws PictureServiceException, PropertyServiceException {
        pictureService.removePictureFromProperty(SAMPLE_HASH_CODE, SAMPLE_HASH_CODE);
        verify(propertyServiceMock).updateProperty(any(Property.class), any(PropertyDetails.class), any(BigDecimal.class));
    }
    
    @Test(expected=PictureServiceException.class)
    public void removingAPictureThatIsNotContainedInAPropertyThrowsAnException() throws PictureServiceException, PictureNotFoundException {
        doThrow(new PictureNotFoundException()).when(propertyMock).removePropertyPicture(any(Picture.class));
        pictureService.removePictureFromProperty(SAMPLE_HASH_CODE, SAMPLE_HASH_CODE);
    }
    
    @Test(expected=PictureServiceException.class)
    public void removingAPictureThatDoesNotExistInThePictureRepositoryThrowsAnException() throws PictureServiceException, PictureNotFoundException {
        doThrow(new PictureNotFoundException()).when(pictureRepositoryMock).getPictureByHashCode(any(Integer.class));
        pictureService.removePictureFromProperty(SAMPLE_HASH_CODE, SAMPLE_HASH_CODE);
    }
    
    @Test(expected=PictureServiceException.class)
    public void removingAPictureFromAPropertyThatDoesNotExistInThePropertyRepositoryThrowsAnException() throws PictureServiceException, PropertyNotFoundException {
        doThrow(new PropertyNotFoundException()).when(propertyServiceMock).getPropertyByHashCode(any(Integer.class));
        pictureService.removePictureFromProperty(SAMPLE_HASH_CODE, SAMPLE_HASH_CODE);
    }
    
    @Test(expected=PictureServiceException.class)
    public void removingAPictureAPictureFromAPropertyThatDoesNotContainThePictureThrowsAnException() throws PictureServiceException, PictureNotFoundException {
        doThrow(new PictureNotFoundException()).when(propertyMock).removePropertyPicture(any(Picture.class));
        pictureService.removePictureFromProperty(SAMPLE_HASH_CODE, SAMPLE_HASH_CODE);
    }
    
    @Test
    public void approvingAPictureFetchesThePictureInThePictureRepository() throws PictureNotFoundException, PictureServiceException {
        pictureService.approvePicture(SAMPLE_HASH_CODE);
        verify(pictureRepositoryMock).getPictureByHashCode(any(Integer.class));
    }
    
    @Test(expected=PictureServiceException.class)
    public void approvingAPictureThatDoesNotExistInThePictureRepositoryThrowsAnException() throws PictureNotFoundException, PictureServiceException {
        doThrow(new PictureNotFoundException()).when(pictureRepositoryMock).getPictureByHashCode(any(Integer.class));
        pictureService.approvePicture(SAMPLE_HASH_CODE);
        verify(pictureRepositoryMock).getPictureByHashCode(any(Integer.class));
    }
    
    @Test
    public void approvingAPictureUpdatesThePictureInThePictureRepository() throws PictureServiceException, PictureNotFoundException {
        pictureService.approvePicture(SAMPLE_HASH_CODE);
        verify(pictureRepositoryMock).updatePicture(any(Picture.class));
    }
    
    @Test(expected=PictureServiceException.class)
    public void approvingAPictureThatDoesNotExistInThePictureRepositoryNeverCallsTheUpdateMethod() throws PictureServiceException, PictureNotFoundException {
        doThrow(new PictureNotFoundException()).when(pictureRepositoryMock).getPictureByHashCode(any(Integer.class));
        pictureService.approvePicture(SAMPLE_HASH_CODE);
        verify(pictureRepositoryMock, never()).updatePicture(any(Picture.class));
    }
    
    @Test
    public void approvingAPictureChangesItsStatusToTheApprovedPictureStatus() throws PictureServiceException {
        pictureService.approvePicture(SAMPLE_HASH_CODE);
        verify(pictureMock).changeStatus(PictureStatus.APPROVED);
    }
    
    @Test(expected=PictureServiceException.class)
    public void approvingAPictureThatIsNotContainedInThePictureRepositoryNeverChangesTheStatusOfThePicture() throws PictureServiceException, PictureNotFoundException {
        doThrow(new PictureNotFoundException()).when(pictureRepositoryMock).getPictureByHashCode(any(Integer.class));
        pictureService.approvePicture(SAMPLE_HASH_CODE);
        verify(pictureMock, never()).changeStatus(PictureStatus.APPROVED);
    }
    
    
}