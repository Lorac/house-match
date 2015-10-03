package ca.ulaval.glo4003.housematch.domain.property;

public enum PropertyType {
    HOUSE("House"), APARTMENT("Appartment");

    private final String displayName;

    PropertyType(final String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
