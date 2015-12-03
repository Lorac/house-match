package ca.ulaval.glo4003.housematch.domain.picture;

public class Picture {
    private String pathToPicture; //TODO : see if we store a String type
    private PictureStatus status;
    
    public Picture(String pathToPicture, PictureStatus status) {
        this.pathToPicture = pathToPicture;
        this.status = status;
    }
}
