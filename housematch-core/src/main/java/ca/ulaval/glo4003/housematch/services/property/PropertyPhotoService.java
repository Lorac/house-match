package ca.ulaval.glo4003.housematch.services.property;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoFactory;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoNotFoundException;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoRepository;

public class PropertyPhotoService {

    private PropertyPhotoRepository propertyPhotoRepository;
    private PropertyPhotoFactory propertyPhotoFactory;

    public PropertyPhotoService(final PropertyPhotoRepository propertyPhotoRepository, final PropertyPhotoFactory propertyPhotoFactory) {
        this.propertyPhotoRepository = propertyPhotoRepository;
        this.propertyPhotoFactory = propertyPhotoFactory;
    }

    public Integer addPropertyPhoto(Property property, byte[] fileBytes, String originalFileName) throws PropertyPhotoServiceException {
        try {
            PropertyPhoto propertyPhoto = propertyPhotoFactory.createPropertyPhoto(fileBytes, originalFileName);
            property.addPhoto(propertyPhoto);
            propertyPhotoRepository.persist(propertyPhoto, fileBytes);
            return propertyPhoto.hashCode();
        } catch (PropertyPhotoAlreadyExistsException | IOException e) {
            throw new PropertyPhotoServiceException(e);
        }
    }

    public byte[] getPropertyPhotoData(int photoHashCode) throws PropertyPhotoServiceException, PropertyPhotoNotFoundException {
        try {
            return Base64.encodeBase64(propertyPhotoRepository.getDataByHashCode(photoHashCode));
        } catch (IOException e) {
            throw new PropertyPhotoServiceException(e);
        }
    }

    public byte[] getPropertyPhotoThumbnailData(int photoHashCode) throws PropertyPhotoServiceException, PropertyPhotoNotFoundException {
        try {
            return Base64.encodeBase64(propertyPhotoRepository.getThumbnailDataByHashCode(photoHashCode));
        } catch (IOException e) {
            throw new PropertyPhotoServiceException(e);
        }
    }

    public void deletePropertyPhoto(int photoHashCode) throws PropertyPhotoServiceException, PropertyPhotoNotFoundException {
        try {
            PropertyPhoto propertyPhoto = propertyPhotoRepository.getByHashCode(photoHashCode);
            propertyPhotoRepository.delete(propertyPhoto);
        } catch (IOException e) {
            throw new PropertyPhotoServiceException(e);
        }
    }
}
