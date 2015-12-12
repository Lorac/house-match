package ca.ulaval.glo4003.housematch.web.viewmodels;

import java.util.Map;

import ca.ulaval.glo4003.housematch.domain.property.PropertyType;

public class StatisticsViewModel extends ViewModel {

    public static final String NAME = "statisticsViewModel";

    private Integer numberOfActiveBuyers;
    private Integer numberOfActiveSellers;
    private Integer numberOfSoldPropertiesThisYear;
    private Map<PropertyType, Integer> numberOfPropertiesForSale;

    @Override
    public String getName() {
        return NAME;
    }

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

    public Integer getNumberOfSoldPropertiesThisYear() {
        return numberOfSoldPropertiesThisYear;
    }

    public void setNumberOfSoldPropertiesThisYear(Integer numberOfSoldPropertiesThisYear) {
        this.numberOfSoldPropertiesThisYear = numberOfSoldPropertiesThisYear;
    }

    public Map<PropertyType, Integer> getNumberOfPropertiesForSale() {
        return numberOfPropertiesForSale;
    }

    public void setNumberOfPropertiesForSale(Map<PropertyType, Integer> numberOfPropertiesForSale) {
        this.numberOfPropertiesForSale = numberOfPropertiesForSale;
    }

}
