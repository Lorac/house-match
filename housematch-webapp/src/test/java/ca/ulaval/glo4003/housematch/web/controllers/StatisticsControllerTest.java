package ca.ulaval.glo4003.housematch.web.controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ca.ulaval.glo4003.housematch.domain.statistics.PropertyStatistics;
import ca.ulaval.glo4003.housematch.domain.statistics.UserStatistics;
import ca.ulaval.glo4003.housematch.services.statistics.PropertyStatisticsService;
import ca.ulaval.glo4003.housematch.services.statistics.UserStatisticsService;
import ca.ulaval.glo4003.housematch.web.assemblers.StatisticsViewModelAssembler;

public class StatisticsControllerTest extends BaseControllerTest {

    private PropertyStatisticsService propertyStatisticsServiceMock;
    private UserStatisticsService userStatisticsServiceMock;
    private PropertyStatistics propertyStatisticsMock;
    private UserStatistics userStatisticsMock;
    private StatisticsViewModelAssembler statisticsViewModelAssemblerMock;

    private StatisticsController statisticsController;

    @Before
    public void init() throws Exception {
        super.init();
        initMocks();
        statisticsController = new StatisticsController(propertyStatisticsServiceMock, userStatisticsServiceMock,
                statisticsViewModelAssemblerMock);
        mockMvc = MockMvcBuilders.standaloneSetup(statisticsController).setViewResolvers(viewResolver).build();
    }

    private void initMocks() {
        propertyStatisticsServiceMock = mock(PropertyStatisticsService.class);
        userStatisticsServiceMock = mock(UserStatisticsService.class);
        propertyStatisticsMock = mock(PropertyStatistics.class);
        userStatisticsMock = mock(UserStatistics.class);
        statisticsViewModelAssemblerMock = mock(StatisticsViewModelAssembler.class);
    }

    @Test
    public void statisticsControllerRendersStatisticsView() throws Exception {
        ResultActions results = performGetRequest(StatisticsController.STATISTICS_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(StatisticsController.STATISTICS_VIEW_NAME));
    }

    @Test
    public void statisticsControllerAssemblesTheStatisticsUsingTheStatisticsViewModelAssemblerDuringStatisticsViewRequest()
            throws Exception {
        when(propertyStatisticsServiceMock.getStatistics()).thenReturn(propertyStatisticsMock);
        when(userStatisticsServiceMock.getStatistics()).thenReturn(userStatisticsMock);

        performGetRequest(StatisticsController.STATISTICS_URL);

        verify(statisticsViewModelAssemblerMock).assembleFromStatistics(propertyStatisticsMock, userStatisticsMock);
    }
}
