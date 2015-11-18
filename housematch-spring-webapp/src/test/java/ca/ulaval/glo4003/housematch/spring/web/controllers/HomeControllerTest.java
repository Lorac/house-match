package ca.ulaval.glo4003.housematch.spring.web.controllers;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.services.property.PropertyService;
import ca.ulaval.glo4003.housematch.services.user.UserService;
import ca.ulaval.glo4003.housematch.spring.web.assemblers.StatisticsViewModelAssembler;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.StatisticsViewModel;
import ca.ulaval.glo4003.housematch.statistics.property.PropertyStatistics;
import ca.ulaval.glo4003.housematch.statistics.user.UserStatistics;

public class HomeControllerTest extends BaseControllerTest {

    private PropertyService propertyServiceMock;
    private UserService userServiceMock;
    private PropertyStatistics propertyStatisticsMock;
    private UserStatistics userStatisticsMock;
    private StatisticsViewModelAssembler statisticsViewModelAssemblerMock;
    private StatisticsViewModel statisticsViewModelMock;
    private HomeController homeController;

    @Before
    public void init() throws Exception {
        super.init();
        initMocks();
        homeController = new HomeController(propertyServiceMock, userServiceMock, statisticsViewModelAssemblerMock);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).setViewResolvers(viewResolver).build();
    }

    private void initMocks() {
        propertyServiceMock = mock(PropertyService.class);
        userServiceMock = mock(UserService.class);
        propertyStatisticsMock = mock(PropertyStatistics.class);
        userStatisticsMock = mock(UserStatistics.class);
        statisticsViewModelMock = mock(StatisticsViewModel.class);
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
    public void homeControllerRendersHomeViewWithStatisticsForAnonymousUser() throws Exception {
        when(statisticsViewModelAssemblerMock.assembleFromStatistics(anyObject(), anyObject())).thenReturn(statisticsViewModelMock);
        mockHttpSession.removeAttribute(HomeController.USER_ATTRIBUTE_NAME);

        ResultActions results = performGetRequest(HomeController.HOME_URL);

        results.andExpect(model().attributeExists(StatisticsViewModel.NAME));
    }

    @Test
    public void homeControllerAssemblesTheStatisticsUsingTheStatisticsViewModelAssembler() throws Exception {
        mockHttpSession.removeAttribute(HomeController.USER_ATTRIBUTE_NAME);
        when(propertyServiceMock.getStatistics()).thenReturn(propertyStatisticsMock);
        when(userServiceMock.getStatistics()).thenReturn(userStatisticsMock);

        performGetRequest(HomeController.HOME_URL);

        verify(statisticsViewModelAssemblerMock).assembleFromStatistics(propertyStatisticsMock, userStatisticsMock);
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
}