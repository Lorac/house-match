package ca.ulaval.glo4003.housematch.domain.streetaddress;

public enum State {
    QC("Quebec", Country.CAN),
    ON("Ontario", Country.CAN),
    NS("Nova Scotia", Country.CAN),
    NB("New Brunswick", Country.CAN),
    MB("Manitoba", Country.CAN),
    BC("British Columbia", Country.CAN),
    PE("Prince Edward Island", Country.CAN),
    SK("Saskatchewan", Country.CAN),
    AB("Alberta", Country.CAN),
    NL("Newfouland and Labrador", Country.CAN);

    private final String name;
    private final Country country;

    State(final String name, final Country country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }
}
