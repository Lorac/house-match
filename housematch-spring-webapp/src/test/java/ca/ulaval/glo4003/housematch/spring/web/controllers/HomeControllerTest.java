package ca.ulaval.glo4003.housematch.spring.web.controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.spring.web.security.AuthorizationValidator;

public class HomeControllerTest extends MvcControllerTest {

    private HomeController homeController;
    private AuthorizationValidator authorizationValidtorMock;

    @Before
    public void init() {
        super.init();
        authorizationValidtorMock = mock(AuthorizationValidator.class);
        homeController = new HomeController(authorizationValidtorMock);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).setViewResolvers(viewResolver).build();
    }

    @Test
    public void homeControllerShouldRenderHomeViewForAnonymousUser() throws Exception {
        MockHttpServletRequestBuilder getRequest = get(HomeController.HOME_REQUEST_MAPPING).accept(MediaType.ALL);
        ResultActions results = mockMvc.perform(getRequest);

        results.andExpect(status().isOk());
        results.andExpect(view().name(HomeController.HOME_VIEW_NAME));
    }

    @Test
    public void homeControllerShouldRedirectToAdminHomeViewWhenUserRoleIsAdmin() throws Exception {
        when(userMock.getRole()).thenReturn(UserRole.ADMINISTRATOR);

        MockHttpServletRequestBuilder getRequest = get(HomeController.HOME_REQUEST_MAPPING).accept(MediaType.ALL);
        ResultActions results = mockMvc.perform(getRequest.session(mockHttpSession));

        results.andExpect(status().is3xxRedirection());
        results.andExpect(redirectedUrl(HomeController.ADMIN_HOME_REQUEST_MAPPING));
    }

    @Test
    public void homeControllerShouldRedirectToSellerHomeViewWhenUserRoleIsSeller() throws Exception {
        when(userMock.getRole()).thenReturn(UserRole.SELLER);

        MockHttpServletRequestBuilder getRequest = get(HomeController.HOME_REQUEST_MAPPING).accept(MediaType.ALL);
        ResultActions results = mockMvc.perform(getRequest.session(mockHttpSession));

        results.andExpect(status().is3xxRedirection());
        results.andExpect(redirectedUrl(HomeController.SELLER_HOME_REQUEST_MAPPING));
    }

    @Test
    public void homeControllerShouldRedirectToBuyerHomeViewWhenUserRoleIsBuyer() throws Exception {
        when(userMock.getRole()).thenReturn(UserRole.BUYER);

        MockHttpServletRequestBuilder getRequest = get(HomeController.HOME_REQUEST_MAPPING).accept(MediaType.ALL);
        ResultActions results = mockMvc.perform(getRequest.session(mockHttpSession));

        results.andExpect(status().is3xxRedirection());
        results.andExpect(redirectedUrl(HomeController.BUYER_HOME_REQUEST_MAPPING));
    }

    @Test
    public void homeControllerShouldRenderAdminHomeView() throws Exception {
        MockHttpServletRequestBuilder getRequest = get(HomeController.ADMIN_HOME_REQUEST_MAPPING).accept(MediaType.ALL);
        ResultActions results = mockMvc.perform(getRequest);

        results.andExpect(status().isOk());
        results.andExpect(view().name(HomeController.ADMIN_HOME_VIEW_NAME));
    }

    @Test
    public void homeControllerShouldRenderSellerHomeView() throws Exception {
        MockHttpServletRequestBuilder getRequest = get(HomeController.SELLER_HOME_REQUEST_MAPPING)
                .accept(MediaType.ALL);
        ResultActions results = mockMvc.perform(getRequest);

        results.andExpect(status().isOk());
        results.andExpect(view().name(HomeController.SELLER_HOME_VIEW_NAME));
    }

    @Test
    public void homeControllerShouldRenderBuyerHomeView() throws Exception {
        MockHttpServletRequestBuilder getRequest = get(HomeController.BUYER_HOME_REQUEST_MAPPING).accept(MediaType.ALL);
        ResultActions results = mockMvc.perform(getRequest);

        results.andExpect(status().isOk());
        results.andExpect(view().name(HomeController.BUYER_HOME_VIEW_NAME));
    }
}