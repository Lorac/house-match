package ca.ulaval.glo4003.housematch.services.propertyphoto;

import java.io.IOException;
import java.util.List;

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

    public byte[] getPhotoThumbnailData(int photoHashCode) throws PropertyPhotoServiceException, PropertyPhotoNotFoundException {
        try {
            PropertyPhoto propertyPhoto = propertyPhotoRepository.getByHashCode(photoHashCode);
            return propertyPhotoRepository.getThumbnailData(propertyPhoto);
        } catch (IOException e) {
            throw new PropertyPhotoServiceException(e);
        }
    }

    public Integer addPhoto(Property property, byte[] fileBytes, String originalFileName)
            throws PropertyPhotoServiceException, PropertyPhotoAlreadyExistsException {
        try {
            PropertyPhoto propertyPhoto = propertyPhotoFactory.createPropertyPhoto(fileBytes, originalFileName);
            propertyPhotoRepository.persist(propertyPhoto, fileBytes);
            property.addPhoto(propertyPhoto);
            propertyRepository.update(property);
            return propertyPhoto.hashCode();
        } catch (IOException e) {
            throw new PropertyPhotoServiceException(e);
        }
    }

    public void removePhoto(Property property, int photoHashCode) throws PropertyPhotoServiceException, PropertyPhotoNotFoundException {
        try {
            PropertyPhoto propertyPhoto = property.getPhotoByHashCode(photoHashCode);
            propertyPhotoRepository.delete(propertyPhoto);
            property.removePhoto(propertyPhoto);
            propertyRepository.update(property);
        } catch (IOException e) {
            throw new PropertyPhotoServiceException(e);
        }
    }

    public void approvePhoto(int photoHashCode) throws PropertyPhotoNotFoundException {
        PropertyPhoto propertyPhoto = propertyPhotoRepository.getByHashCode(photoHashCode);
        propertyPhoto.updateStatus(PropertyPhotoStatus.APPROVED);
        propertyPhotoRepository.update(propertyPhoto);
    }

    public void rejectPhoto(int photoHashCode) throws PropertyPhotoServiceException, PropertyPhotoNotFoundException {
        try {
            PropertyPhoto propertyPhoto = propertyPhotoRepository.getByHashCode(photoHashCode);
            propertyPhoto.updateStatus(PropertyPhotoStatus.REJECTED);
            propertyPhotoRepository.delete(propertyPhoto);
        } catch (IOException e) {
            throw new PropertyPhotoServiceException(e);
        }
    }

    public List<PropertyPhoto> getPhotosWaitingForApproval() {
        return propertyPhotoRepository.getByStatus(PropertyPhotoStatus.PENDING_APPROVAL);
    }
}
