package ca.ulaval.glo4003.housematch.domain.picture;

public interface PictureRepository {
    void persist(Picture picture); //Is it necessary to throw a PictureAlreadyExists exception?
    Picture getPictureByHashCode(Integer hashCode);
}
