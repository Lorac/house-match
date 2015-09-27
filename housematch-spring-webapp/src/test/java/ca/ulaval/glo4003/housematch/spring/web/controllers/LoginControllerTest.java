package ca.ulaval.glo4003.housematch.spring.web.controllers;

import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ca.ulaval.glo4003.housematch.services.UserService;

public class LoginControllerTest extends MvcControllerTest {

    private UserService userServiceMock;
    private LoginController loginController;

    @Before
    public void init() {
        super.init();
        userServiceMock = mock(UserService.class);
        loginController = new LoginController(userServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).setViewResolvers(viewResolver).build();
    }

    @Test
    public void loginControllerShouldRenderLoginView() throws Exception {
        MockHttpServletRequestBuilder getRequest = get("/login").accept(MediaType.ALL);
        ResultActions results = mockMvc.perform(getRequest);

        results.andExpect(status().isOk());
        results.andExpect(view().name("login"));
    }

    @Test
    public void loginControllerShouldRenderLoginViewWithUsernameAndPassword() throws Exception {
        MockHttpServletRequestBuilder getRequest = get("/login").accept(MediaType.ALL);
        ResultActions results = mockMvc.perform(getRequest);

        results.andExpect(model().attribute("loginForm", hasProperty("username")));
        results.andExpect(model().attribute("loginForm", hasProperty("password")));

    }

    @Test
    public void logoutControllerRendersLoginPageUponLogout() throws Exception {
        MockHttpServletRequestBuilder getRequest = get("/logout").accept(MediaType.ALL);
        ResultActions results = mockMvc.perform(getRequest);

        results.andExpect(view().name("login"));
        results.andExpect(status().isOk());
    }
}
