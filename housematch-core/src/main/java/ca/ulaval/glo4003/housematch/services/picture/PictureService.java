package ca.ulaval.glo4003.housematch.services.picture;

import ca.ulaval.glo4003.housematch.domain.picture.Picture;
import ca.ulaval.glo4003.housematch.domain.picture.PictureAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.picture.PictureFactory;
import ca.ulaval.glo4003.housematch.domain.picture.PictureRepository;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.services.property.PropertyService;
import ca.ulaval.glo4003.housematch.services.property.PropertyServiceException;

public class PictureService {
    private PictureFactory pictureFactory;
    private PictureRepository pictureRepository;
    private PropertyService propertyService;
    
    public PictureService(final PictureFactory pictureFactory, final PictureRepository pictureRepository, final PropertyService propertyService) {
        this.pictureFactory = pictureFactory;
        this.pictureRepository = pictureRepository;
        this.propertyService = propertyService;
    }
    
    public void addPictureToProperty(String pathToPropertyPicture, Integer propertyHashCode) throws PictureServiceException {
        try {
            Property property = propertyService.getPropertyByHashCode(propertyHashCode);
            Picture propertyPicture = createPicture(pathToPropertyPicture);
            linkPictureToExistingProperty(property, propertyPicture);
        } catch (PictureAlreadyExistsException | PropertyServiceException | PropertyNotFoundException e) {
            throw new PictureServiceException(e);
        }
    }
    
    private Picture createPicture(String pathToPicture) throws PictureAlreadyExistsException {
        Picture picture = pictureFactory.createPicture(pathToPicture);
        pictureRepository.persist(picture);
        return picture;
    }
    
    private void linkPictureToExistingProperty(Property property, Picture propertyPicture) throws PropertyServiceException, PictureAlreadyExistsException {
        property.addPictureToProperty(propertyPicture);
        propertyService.updateProperty(property, property.getPropertyDetails(), property.getSellingPrice());
    }
    
    public void removePictureFromProperty() {
        //TODO
    }
    
    public void approvePicture() {
        //TODO
    }
    
    public void rejectPicture() {
        //TODO
    }
}
