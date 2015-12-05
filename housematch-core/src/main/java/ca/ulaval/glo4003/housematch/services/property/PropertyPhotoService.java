package ca.ulaval.glo4003.housematch.services.property;

import java.io.IOException;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyRepository;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoFactory;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoNotFoundException;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoRepository;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoStatus;

public class PropertyPhotoService {

    private PropertyRepository propertyRepository;
    private PropertyPhotoRepository propertyPhotoRepository;
    private PropertyPhotoFactory propertyPhotoFactory;

    public PropertyPhotoService(final PropertyRepository propertyRepository, final PropertyPhotoRepository propertyPhotoRepository,
            final PropertyPhotoFactory propertyPhotoFactory) {
        this.propertyRepository = propertyRepository;
        this.propertyPhotoRepository = propertyPhotoRepository;
        this.propertyPhotoFactory = propertyPhotoFactory;
    }

    public Integer addPhoto(Property property, byte[] fileBytes, String originalFileName) throws PropertyPhotoServiceException {
        try {
            PropertyPhoto propertyPhoto = propertyPhotoFactory.createPropertyPhoto(fileBytes, originalFileName);
            propertyPhotoRepository.persist(propertyPhoto, fileBytes);
            property.addPhoto(propertyPhoto);
            propertyRepository.update(property);
            return propertyPhoto.hashCode();
        } catch (PropertyPhotoAlreadyExistsException | IOException e) {
            throw new PropertyPhotoServiceException(e);
        }
    }

    public byte[] getPhotoThumbnailData(int photoHashCode) throws PropertyPhotoServiceException {
        try {
            PropertyPhoto propertyPhoto = propertyPhotoRepository.getByHashCode(photoHashCode);
            return Base64.encodeBase64(propertyPhotoRepository.getThumbnailData(propertyPhoto));
        } catch (IOException | PropertyPhotoNotFoundException e) {
            throw new PropertyPhotoServiceException(e);
        }
    }

    public void deletePhoto(Property property, int photoHashCode) throws PropertyPhotoServiceException {
        try {
            PropertyPhoto propertyPhoto = property.getPhotoByHashCode(photoHashCode);
            propertyPhotoRepository.delete(propertyPhoto);
            property.removePhoto(propertyPhoto);
            propertyRepository.update(property);
        } catch (IOException | PropertyPhotoNotFoundException e) {
            throw new PropertyPhotoServiceException(e);
        }
    }

    public void approvePhoto(Property property, int photoHashCode) throws PropertyPhotoServiceException {
        try {
            PropertyPhoto propertyPhoto = property.getPhotoByHashCode(photoHashCode);
            propertyPhoto.updateStatus(PropertyPhotoStatus.APPROVED);
            propertyPhotoRepository.update(propertyPhoto);
        } catch (PropertyPhotoNotFoundException e) {
            throw new PropertyPhotoServiceException(e);
        }
    }

    public List<PropertyPhoto> getPhotosWaitingForApproval() {
        return propertyPhotoRepository.getByStatus(PropertyPhotoStatus.WAITING_FOR_APPROVAL);
    }
}
