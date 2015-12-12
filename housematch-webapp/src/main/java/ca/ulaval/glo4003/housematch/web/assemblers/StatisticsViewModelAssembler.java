package ca.ulaval.glo4003.housematch.web.assemblers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.domain.statistics.PropertyStatistics;
import ca.ulaval.glo4003.housematch.domain.statistics.Statistic;
import ca.ulaval.glo4003.housematch.domain.statistics.UserStatistics;
import ca.ulaval.glo4003.housematch.web.viewmodels.StatisticsViewModel;

public class StatisticsViewModelAssembler {

    public StatisticsViewModel assemble(PropertyStatistics propertyStatistics, UserStatistics userStatistics) {
        StatisticsViewModel statisticsViewModel = new StatisticsViewModel();
        statisticsViewModel.setNumberOfActiveBuyers(userStatistics.getNumberOfActiveBuyers());
        statisticsViewModel.setNumberOfActiveSellers(userStatistics.getNumberOfActiveSellers());
        statisticsViewModel.setNumberOfSoldPropertiesThisYear(propertyStatistics.getNumberOfSoldPropertiesThisYear());
        statisticsViewModel.setNumberOfPropertiesForSale(assemblePropertiesForSale(propertyStatistics, statisticsViewModel));
        return statisticsViewModel;
    }

    private Map<PropertyType, Integer> assemblePropertiesForSale(PropertyStatistics propertyStatistics, StatisticsViewModel viewModel) {
        Map<PropertyType, Integer> numberOfPropertiesForSale = new HashMap<>();
        for (Entry<PropertyType, Statistic<Integer>> entry : propertyStatistics.getNumberOfPropertiesForSale().entrySet()) {
            numberOfPropertiesForSale.put(entry.getKey(), entry.getValue().getValue());
        }
        return numberOfPropertiesForSale;
    }
}
