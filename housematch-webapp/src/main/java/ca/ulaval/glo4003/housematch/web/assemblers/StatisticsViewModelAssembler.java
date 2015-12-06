package ca.ulaval.glo4003.housematch.web.assemblers;

import ca.ulaval.glo4003.housematch.domain.statistics.PropertyStatistics;
import ca.ulaval.glo4003.housematch.domain.statistics.UserStatistics;
import ca.ulaval.glo4003.housematch.web.viewmodels.StatisticsViewModel;

public class StatisticsViewModelAssembler {

    public StatisticsViewModel assemble(PropertyStatistics propertyStatistics, UserStatistics userStatistics) {
        StatisticsViewModel statisticsViewModel = new StatisticsViewModel();
        statisticsViewModel.setPropertyStatistics(propertyStatistics);
        statisticsViewModel.setUserStatistics(userStatistics);
        return statisticsViewModel;
    }
}
