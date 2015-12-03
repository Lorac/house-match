package ca.ulaval.glo4003.housematch.services.picture;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.picture.Picture;
import ca.ulaval.glo4003.housematch.domain.picture.PictureFactory;
import ca.ulaval.glo4003.housematch.domain.picture.PictureRepository;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.services.property.PropertyService;

public class PictureServiceTest {
    private static final String SAMPLE_PATH = "this/is/a/nice/path.png";
    private static final Integer SAMPLE_HASH_CODE = 123455;
    
    private PictureFactory pictureFactoryMock;
    private PictureRepository pictureRepositoryMock;
    private PropertyService propertyServiceMock;
    private Property propertyMock;
    private Picture pictureMock;
    
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
    }
    
    private void initStubs() throws PropertyNotFoundException {
        when(pictureFactoryMock.createPicture(any(String.class))).thenReturn(pictureMock);
        when(propertyServiceMock.getPropertyByHashCode(any(Integer.class))).thenReturn(propertyMock);
    }
    
    @Test
    public void addingAPictureToAPropertyCallsThePropertyService() throws PictureServiceException, PropertyNotFoundException {
        pictureService.addPictureToProperty(SAMPLE_PATH, SAMPLE_HASH_CODE);
        verify(propertyServiceMock).getPropertyByHashCode(SAMPLE_HASH_CODE);
    }
}