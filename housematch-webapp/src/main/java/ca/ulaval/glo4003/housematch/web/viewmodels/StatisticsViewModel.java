package ca.ulaval.glo4003.housematch.web.viewmodels;

import javax.validation.constraints.NotNull;

import ca.ulaval.glo4003.housematch.domain.statistics.PropertyStatistics;
import ca.ulaval.glo4003.housematch.domain.statistics.UserStatistics;

public class StatisticsViewModel extends ViewModel {

    public static final String NAME = "statistics";

    @NotNull
    private PropertyStatistics propertyStatistics;

    @NotNull
    private UserStatistics userStatistics;

    @Override
    public String getName() {
        return NAME;
    }

    public PropertyStatistics getPropertyStatistics() {
        return propertyStatistics;
    }

    public void setPropertyStatistics(PropertyStatistics propertyStatistics) {
        this.propertyStatistics = propertyStatistics;
    }

    public UserStatistics getUserStatistics() {
        return userStatistics;
    }

    public void setUserStatistics(UserStatistics userStatistics) {
        this.userStatistics = userStatistics;
    }

}
