package ca.ulaval.glo4003.housematch.spring.web.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class HomeControllerTest extends BaseControllerTest {

    private HomeController homeController;

    @Before
    public void init() throws Exception {
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
    public void homeControllerRendersAdminHomeView() throws Exception {
        ResultActions results = performGetRequest(HomeController.ADMIN_HOME_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(HomeController.ADMIN_HOME_VIEW_NAME));
    }

    @Test
    public void homeControllerRendersSellerHomeView() throws Exception {
        ResultActions results = performGetRequest(HomeController.SELLER_HOME_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(HomeController.SELLER_HOME_VIEW_NAME));
    }

    @Test
    public void homeControllerRendersBuyerHomeView() throws Exception {
        ResultActions results = performGetRequest(HomeController.BUYER_HOME_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(HomeController.BUYER_HOME_VIEW_NAME));
    }
}