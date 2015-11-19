package ca.ulaval.glo4003.housematch.web.assemblers;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.statistics.property.PropertyStatistics;
import ca.ulaval.glo4003.housematch.statistics.user.UserStatistics;
import ca.ulaval.glo4003.housematch.web.viewmodels.StatisticsViewModel;

public class StatisticsViewModelAssemblerTest {

    private PropertyStatistics propertyStatisticsMock;
    private UserStatistics userStatisticsMock;

    private StatisticsViewModelAssembler assembler;

    @Before
    public void init() {
        initMocks();
        assembler = new StatisticsViewModelAssembler();
    }

    private void initMocks() {
        propertyStatisticsMock = mock(PropertyStatistics.class);
        userStatisticsMock = mock(UserStatistics.class);
    }

    @Test
    public void assemblesTheViewModelFromTheSpecifiedProperty() {
        StatisticsViewModel viewModel = assembler.assembleFromStatistics(propertyStatisticsMock, userStatisticsMock);

        assertSame(propertyStatisticsMock, viewModel.getPropertyStatistics());
        assertSame(userStatisticsMock, viewModel.getUserStatistics());
    }

}
