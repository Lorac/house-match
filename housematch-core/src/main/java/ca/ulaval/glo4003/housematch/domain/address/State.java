package ca.ulaval.glo4003.housematch.domain.address;

public class State {

    private final String abbreviation;
    private final String name;

    public State(final String abbreviation, final String name) {
        this.abbreviation = abbreviation;
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getdisplayName() {
        return name;
    }

}
