package ca.ulaval.glo4003.housematch.domain.address;

public enum Country {
    CAN("Canada");

    private final String name;

    Country(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
