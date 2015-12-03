package ca.ulaval.glo4003.housematch.domain.picture;

public interface PictureRepository {
    void persist(Picture picture) throws PictureAlreadyExistsException;

    Picture getPictureByHashCode(Integer hashCode) throws PictureNotFoundException;

    void removePictureByHashCode(Integer hashCode) throws PictureNotFoundException;

    void updatePicture(Picture picture) throws PictureNotFoundException;
}
