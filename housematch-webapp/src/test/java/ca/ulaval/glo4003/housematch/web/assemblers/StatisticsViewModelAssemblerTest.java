package ca.ulaval.glo4003.housematch.web.assemblers;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.domain.statistics.PropertyStatistics;
import ca.ulaval.glo4003.housematch.domain.statistics.Statistic;
import ca.ulaval.glo4003.housematch.domain.statistics.UserStatistics;
import ca.ulaval.glo4003.housematch.web.viewmodels.StatisticsViewModel;

public class StatisticsViewModelAssemblerTest {

    private static final Integer SAMPLE_NUMBER_OF_ACTIVE_BUYERS = 4;
    private static final Integer SAMPLE_NUMBER_OF_ACTIVE_SELLERS = 3;
    private static final Integer SAMPLE_NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR = 4;
    private static final Integer SAMPLE_NUMBER_OF_PROPERTIES_FOR_SALE = 6;
    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.CONDO_LOFT;

    private PropertyStatistics propertyStatisticsMock;
    private UserStatistics userStatisticsMock;

    private Map<PropertyType, Statistic<Integer>> numberOfPropertiesForSaleStats = new HashMap<>();
    private Statistic<Integer> statisticMock;
    private StatisticsViewModelAssembler assembler;

    @Before
    public void init() {
        initMocks();
        initStubs();
        numberOfPropertiesForSaleStats.put(SAMPLE_PROPERTY_TYPE, statisticMock);
        assembler = new StatisticsViewModelAssembler();
    }

    @SuppressWarnings("unchecked")
    private void initMocks() {
        propertyStatisticsMock = mock(PropertyStatistics.class);
        userStatisticsMock = mock(UserStatistics.class);
        statisticMock = mock(Statistic.class);
    }

    private void initStubs() {
        when(userStatisticsMock.getNumberOfActiveBuyers()).thenReturn(SAMPLE_NUMBER_OF_ACTIVE_BUYERS);
        when(userStatisticsMock.getNumberOfActiveSellers()).thenReturn(SAMPLE_NUMBER_OF_ACTIVE_SELLERS);
        when(propertyStatisticsMock.getNumberOfSoldPropertiesThisYear()).thenReturn(SAMPLE_NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR);
        when(propertyStatisticsMock.getNumberOfPropertiesForSale(SAMPLE_PROPERTY_TYPE)).thenReturn(SAMPLE_NUMBER_OF_PROPERTIES_FOR_SALE);
        when(propertyStatisticsMock.getNumberOfPropertiesForSale()).thenReturn(numberOfPropertiesForSaleStats);
        when(statisticMock.getValue()).thenReturn(SAMPLE_NUMBER_OF_PROPERTIES_FOR_SALE);
    }

    @Test
    public void assemblesTheViewModelFromTheSpecifiedProperty() {
        StatisticsViewModel viewModel = assembler.assemble(propertyStatisticsMock, userStatisticsMock);

        assertSame(SAMPLE_NUMBER_OF_ACTIVE_BUYERS, viewModel.getNumberOfActiveBuyers());
        assertSame(SAMPLE_NUMBER_OF_ACTIVE_SELLERS, viewModel.getNumberOfActiveSellers());
        assertSame(SAMPLE_NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR, viewModel.getNumberOfSoldPropertiesThisYear());
        assertSame(SAMPLE_NUMBER_OF_PROPERTIES_FOR_SALE, viewModel.getNumberOfPropertiesForSale().get(SAMPLE_PROPERTY_TYPE));
    }

}
