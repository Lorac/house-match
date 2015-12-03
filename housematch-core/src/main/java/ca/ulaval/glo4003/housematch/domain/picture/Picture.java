package ca.ulaval.glo4003.housematch.domain.picture;

public class Picture {
    private String pathToPicture;
    private PictureStatus status;

    public Picture(final String pathToPicture, final PictureStatus status) {
        this.pathToPicture = pathToPicture;
        this.status = status;
    }
}
