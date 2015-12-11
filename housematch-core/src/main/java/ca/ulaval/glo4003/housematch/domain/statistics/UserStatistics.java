package ca.ulaval.glo4003.housematch.domain.statistics;

public class UserStatistics {

    private Integer numberOfActiveBuyers = 0;
    private Integer numberOfActiveSellers = 0;

    public Integer getNumberOfActiveBuyers() {
        return numberOfActiveBuyers;
    }

    public void setNumberOfActiveBuyers(Integer numberOfActiveBuyers) {
        this.numberOfActiveBuyers = numberOfActiveBuyers;
    }

    public Integer getNumberOfActiveSellers() {
        return numberOfActiveSellers;
    }

    public void setNumberOfActiveSellers(Integer numberOfActiveSellers) {
        this.numberOfActiveSellers = numberOfActiveSellers;
    }
}
