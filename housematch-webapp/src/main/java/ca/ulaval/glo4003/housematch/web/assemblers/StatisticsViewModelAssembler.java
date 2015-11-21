package ca.ulaval.glo4003.housematch.web.assemblers;

import ca.ulaval.glo4003.housematch.statistics.property.PropertyStatistics;
import ca.ulaval.glo4003.housematch.statistics.user.UserStatistics;
import ca.ulaval.glo4003.housematch.web.viewmodels.StatisticsViewModel;

public class StatisticsViewModelAssembler {

    public StatisticsViewModel assembleFromStatistics(PropertyStatistics propertyStatistics, UserStatistics userStatistics) {
        StatisticsViewModel statisticsViewModel = new StatisticsViewModel();
        statisticsViewModel.setPropertyStatistics(propertyStatistics);
        statisticsViewModel.setUserStatistics(userStatistics);
        return statisticsViewModel;
    }
}
