package ca.ulaval.glo4003.housematch.domain.property;

public enum PropertyType {
    SINGLE_FAMILY_HOME("Single family home"),
    CONDO_LOFT("Condo / Loft"),
    COTTAGE("Cottage"),
    MULTIPLEX_INCOME_PROPERTY("Multiplex / Income Property"),
    LOT("Lot"),
    COMMERCIAL("Commercial"),
    FARM("Farm");

    private final String displayName;

    PropertyType(final String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
