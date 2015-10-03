package ca.ulaval.glo4003.housematch.domain.address;

import java.util.List;

public class Country {

    private final String displayName;
    private final String threeLetterAbbreviation;
    private final List<State> states;

    Country(final String displayName, final String threeLetterAbbreviation, final List<State> states) {
        this.displayName = displayName;
        this.threeLetterAbbreviation = threeLetterAbbreviation;
        this.states = states;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getThreeLetterAbbreviation() {
        return this.threeLetterAbbreviation;
    }

    public List<State> getStates() {
        return this.states;
    }
}
