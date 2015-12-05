package ca.ulaval.glo4003.housematch.domain.propertyphoto;

import java.io.IOException;
import java.util.List;

public interface PropertyPhotoRepository {

    void persist(PropertyPhoto propertyPhoto, byte[] fileBytes) throws PropertyPhotoAlreadyExistsException, IOException;

    PropertyPhoto getByHashCode(Integer hashCode) throws PropertyPhotoNotFoundException;

    List<PropertyPhoto> getByStatus(PropertyPhotoStatus propertyPhotoStatus);

    byte[] getThumbnailData(PropertyPhoto propertyPhoto) throws PropertyPhotoNotFoundException, IOException;

    void delete(PropertyPhoto propertyPhoto) throws PropertyPhotoNotFoundException, IOException;

    void update(PropertyPhoto propertyPhoto) throws PropertyPhotoNotFoundException;
}
