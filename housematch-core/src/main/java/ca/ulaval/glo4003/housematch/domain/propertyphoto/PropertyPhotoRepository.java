package ca.ulaval.glo4003.housematch.domain.propertyphoto;

import java.io.IOException;

public interface PropertyPhotoRepository {

    void persist(PropertyPhoto propertyPhoto, byte[] fileBytes) throws PropertyPhotoAlreadyExistsException, IOException;

    PropertyPhoto getByHashCode(Integer hashCode) throws PropertyPhotoNotFoundException;

    byte[] getDataByHashCode(Integer hashCode) throws PropertyPhotoNotFoundException, IOException;

    byte[] getThumbnailDataByHashCode(Integer hashCode) throws PropertyPhotoNotFoundException, IOException;

    void delete(PropertyPhoto propertyPhoto) throws PropertyPhotoNotFoundException, IOException;
}
