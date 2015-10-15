package ca.ulaval.glo4003.housematch.spring.web.controllers;

import static org.hamcrest.Matchers.hasProperty;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ca.ulaval.glo4003.housematch.spring.web.viewmodels.LoginFormViewModel;

public class LoginControllerTest extends BaseControllerTest {

    private static final String USERNAME_PARAMETER_NAME = "username";
    private static final String PASSWORD_PARAMETER_NAME = "password";
    private LoginController loginController;

    @Before
    public void init() throws Exception {
        super.init();
        loginController = new LoginController();
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).setViewResolvers(viewResolver).build();
    }

    @Test
    public void loginControllerRendersLoginViewWhenNoUserIsLoggedIn() throws Exception {
        mockHttpSession.removeAttribute(HomeController.USER_ATTRIBUTE_NAME);

        ResultActions results = performGetRequest(LoginController.LOGIN_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(LoginController.LOGIN_VIEW_NAME));
    }

    @Test
    public void loginControllerRendersLoginViewWithUsernameAndPasswordWhenNoUserIsLoggedIn() throws Exception {
        mockHttpSession.removeAttribute(HomeController.USER_ATTRIBUTE_NAME);

        ResultActions results = performGetRequest(LoginController.LOGIN_URL);

        results.andExpect(model().attribute(LoginFormViewModel.NAME, hasProperty(USERNAME_PARAMETER_NAME)));
        results.andExpect(model().attribute(LoginFormViewModel.NAME, hasProperty(PASSWORD_PARAMETER_NAME)));
    }

    @Test
    public void loginControllerRedirectsToHomeViewWhenUserIsAlreadyLoggedInAndAttemptsToAccessLoginPage() throws Exception {
        ResultActions results = performGetRequest(LoginController.LOGIN_URL);

        results.andExpect(status().is3xxRedirection());
        results.andExpect(redirectedUrl(LoginController.HOME_URL));
    }
}
