package ca.ulaval.glo4003.housematch.domain.address;

public enum Region {
    QC("Quebec", Country.CAN),
    ON("Ontario", Country.CAN),
    NS("Nova Scotia", Country.CAN),
    NB("New Brunswick", Country.CAN),
    MB("Manitoba", Country.CAN),
    BC("British Columbia", Country.CAN),
    PE("Prince Edward Island", Country.CAN),
    SK("Saskatchewan", Country.CAN),
    AB("Alberta", Country.CAN),
    NL("Newfoundland and Labrador", Country.CAN),
    NT("Northwest Territories", Country.CAN);

    private final String name;
    private final Country country;

    Region(final String name, final Country country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return name;
    }
}
