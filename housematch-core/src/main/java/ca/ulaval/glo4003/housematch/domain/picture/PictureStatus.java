package ca.ulaval.glo4003.housematch.domain.picture;

public enum PictureStatus {
    ACCEPTED("Accepted"),
    REFUSED("Refused"),
    WAITING_FOR_MODERATION("Waiting for moderation");
    
    private final String status;
    
    PictureStatus(final String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
