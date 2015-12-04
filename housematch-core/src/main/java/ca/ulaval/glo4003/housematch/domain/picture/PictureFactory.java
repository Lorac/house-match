package ca.ulaval.glo4003.housematch.domain.picture;

public class PictureFactory {
    public Picture createPicture(final String pathToPicture) {
        return new Picture(pathToPicture, PictureStatus.WAITING_FOR_APPROVAL);
    }
}
