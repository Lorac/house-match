package ca.ulaval.glo4003.housematch.domain.propertyphoto;

import java.io.IOException;

public interface PropertyPhotoRepository {

    void persist(PropertyPhoto propertyPhoto, byte[] fileBytes) throws PropertyPhotoAlreadyExistsException, IOException;

    PropertyPhoto getByHashCode(Integer hashCode) throws PropertyPhotoNotFoundException;

    byte[] getPhotoData(PropertyPhoto propertyPhoto) throws PropertyPhotoNotFoundException, IOException;

    byte[] getThumbnailData(PropertyPhoto propertyPhoto) throws PropertyPhotoNotFoundException, IOException;

    void delete(PropertyPhoto propertyPhoto) throws PropertyPhotoNotFoundException, IOException;

    void update(PropertyPhoto propertyPhoto) throws PropertyPhotoNotFoundException;
}
