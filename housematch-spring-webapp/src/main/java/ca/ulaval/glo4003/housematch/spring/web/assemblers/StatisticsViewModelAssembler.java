package ca.ulaval.glo4003.housematch.spring.web.assemblers;

import ca.ulaval.glo4003.housematch.spring.web.viewmodels.StatisticsViewModel;
import ca.ulaval.glo4003.housematch.statistics.property.PropertyStatistics;
import ca.ulaval.glo4003.housematch.statistics.user.UserStatistics;

public class StatisticsViewModelAssembler {

    public StatisticsViewModel assembleFromStatistics(PropertyStatistics propertyStatistics, UserStatistics userStatistics) {
        StatisticsViewModel statisticsViewModel = new StatisticsViewModel();
        statisticsViewModel.setPropertyStatistics(propertyStatistics);
        statisticsViewModel.setUserStatistics(userStatistics);
        return statisticsViewModel;
    }
}
