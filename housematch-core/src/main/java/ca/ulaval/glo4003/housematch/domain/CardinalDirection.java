package ca.ulaval.glo4003.housematch.domain;

public enum CardinalDirection {
    NORTH("North"), SOUTH("South"), WEST("West"), EAST("East");

    private final String displayName;

    CardinalDirection(final String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
