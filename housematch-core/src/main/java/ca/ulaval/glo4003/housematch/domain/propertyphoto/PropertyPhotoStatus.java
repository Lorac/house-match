package ca.ulaval.glo4003.housematch.domain.propertyphoto;

public enum PropertyPhotoStatus {
    APPROVED("Approved"),
    REJECTED("Rejected"),
    WAITING_FOR_APPROVAL("Waiting for approval");

    private final String displayName;

    PropertyPhotoStatus(final String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
