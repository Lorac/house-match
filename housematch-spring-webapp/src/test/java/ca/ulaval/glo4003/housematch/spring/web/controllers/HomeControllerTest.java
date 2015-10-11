package ca.ulaval.glo4003.housematch.spring.web.controllers;

import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class HomeControllerTest extends MvcControllerTest {

    private HomeController homeController;

    @Before
    public void init() {
        super.init();
        homeController = new HomeController();
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).setViewResolvers(viewResolver).build();
    }

    @Test
    public void homeControllerShouldRenderHomeViewForAnonymousUser() throws Exception {
        mockHttpSession.removeAttribute(HomeController.USER_ATTRIBUTE_NAME);

        ResultActions results = performGetRequest(HomeController.HOME_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(HomeController.HOME_VIEW_NAME));
    }

    @Test
    public void homeControllerShouldRenderAdminHomeViewWhenUserRoleIsAdmin() throws Exception {
        when(userMock.getRole()).thenReturn(UserRole.ADMINISTRATOR);

        ResultActions results = performGetRequest(HomeController.HOME_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(HomeController.ADMIN_HOME_VIEW_NAME));
    }

    @Test
    public void homeControllerShouldRenderSellerHomeViewWhenUserRoleIsSeller() throws Exception {
        when(userMock.getRole()).thenReturn(UserRole.SELLER);

        ResultActions results = performGetRequest(HomeController.HOME_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(HomeController.SELLER_HOME_VIEW_NAME));
    }

    @Test
    public void homeControllerShouldRenderBuyerHomeViewWhenUserRoleIsBuyer() throws Exception {
        when(userMock.getRole()).thenReturn(UserRole.BUYER);

        ResultActions results = performGetRequest(HomeController.HOME_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(HomeController.BUYER_HOME_VIEW_NAME));
    }
}