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
import ca.ulaval.glo4003.housematch.domain.picture.PictureRepository;
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
    public void init() throws PropertyNotFoundException {
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
    
    private void initStubs() throws PropertyNotFoundException {
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
        verify(pictureRepositoryMock).persist(pictureMock);
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
}