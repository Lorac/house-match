package ca.ulaval.glo4003.housematch.web.controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ca.ulaval.glo4003.housematch.domain.statistics.PropertyStatistics;
import ca.ulaval.glo4003.housematch.domain.statistics.UserStatistics;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.services.statistics.PropertyStatisticsService;
import ca.ulaval.glo4003.housematch.services.statistics.UserStatisticsService;
import ca.ulaval.glo4003.housematch.web.assemblers.StatisticsViewModelAssembler;

public class HomeControllerTest extends BaseControllerTest {

    private PropertyStatisticsService propertyStatisticsServiceMock;
    private UserStatisticsService userStatisticsServiceMock;
    private PropertyStatistics propertyStatisticsMock;
    private UserStatistics userStatisticsMock;
    private StatisticsViewModelAssembler statisticsViewModelAssemblerMock;
    private HomeController homeController;

    @Before
    public void init() throws Exception {
        super.init();
        initMocks();
        homeController = new HomeController(propertyStatisticsServiceMock, userStatisticsServiceMock, statisticsViewModelAssemblerMock);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).setViewResolvers(viewResolver).build();
    }

    private void initMocks() {
        propertyStatisticsServiceMock = mock(PropertyStatisticsService.class);
        userStatisticsServiceMock = mock(UserStatisticsService.class);
        propertyStatisticsMock = mock(PropertyStatistics.class);
        userStatisticsMock = mock(UserStatistics.class);
        statisticsViewModelAssemblerMock = mock(StatisticsViewModelAssembler.class);
    }

    @Test
    public void homeControllerRendersHomeViewForAnonymousUser() throws Exception {
        mockHttpSession.removeAttribute(HomeController.USER_ATTRIBUTE_NAME);

        ResultActions results = performGetRequest(HomeController.HOME_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(HomeController.HOME_VIEW_NAME));
    }

    @Test
    public void homeControllerRedirectsToAdminHomeViewWhenUserRoleIsAdmin() throws Exception {
        when(userMock.getRole()).thenReturn(UserRole.ADMINISTRATOR);

        ResultActions results = performGetRequest(HomeController.HOME_URL);

        results.andExpect(status().is3xxRedirection());
        results.andExpect(redirectedUrl(HomeController.ADMIN_HOME_URL));
    }

    @Test
    public void homeControllerRedirectsToSellerHomeViewWhenUserRoleIsSeller() throws Exception {
        when(userMock.getRole()).thenReturn(UserRole.SELLER);

        ResultActions results = performGetRequest(HomeController.HOME_URL);

        results.andExpect(status().is3xxRedirection());
        results.andExpect(redirectedUrl(HomeController.SELLER_HOME_URL));
    }

    @Test
    public void homeControllerRedirectsToBuyerHomeViewWhenUserRoleIsBuyer() throws Exception {
        when(userMock.getRole()).thenReturn(UserRole.BUYER);

        ResultActions results = performGetRequest(HomeController.HOME_URL);

        results.andExpect(status().is3xxRedirection());
        results.andExpect(redirectedUrl(HomeController.BUYER_HOME_URL));
    }

    @Test
    public void homeControllerRendersStatisticsView() throws Exception {
        ResultActions results = performGetRequest(HomeController.STATISTICS_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(HomeController.STATISTICS_VIEW_NAME));
    }

    @Test
    public void homeControllerAssemblesTheStatisticsUsingTheStatisticsViewModelAssemblerDuringStatisticsViewRequest() throws Exception {
        when(propertyStatisticsServiceMock.getStatistics()).thenReturn(propertyStatisticsMock);
        when(userStatisticsServiceMock.getStatistics()).thenReturn(userStatisticsMock);

        performGetRequest(HomeController.STATISTICS_URL);

        verify(statisticsViewModelAssemblerMock).assembleFromStatistics(propertyStatisticsMock, userStatisticsMock);
    }
}