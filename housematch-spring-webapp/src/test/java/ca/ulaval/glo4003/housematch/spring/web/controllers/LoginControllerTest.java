package ca.ulaval.glo4003.housematch.spring.web.controllers;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ca.ulaval.glo4003.housematch.services.UserActivationService;
import ca.ulaval.glo4003.housematch.services.UserActivationServiceException;
import ca.ulaval.glo4003.housematch.services.UserService;
import ca.ulaval.glo4003.housematch.services.UserServiceException;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.AlertMessageType;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.AlertMessageViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.LoginFormViewModel;

public class LoginControllerTest extends MvcControllerTest {

    private static final String USERNAME_PARAMETER_NAME = "username";
    private static final String SAMPLE_USERNAME = "Alice";
    private static final String PASSWORD_PARAMETER_NAME = "password";
    private static final String SAMPLE_PASSWORD = "PASSWORD1234";

    private UserService userServiceMock;
    private UserActivationService userActivationServiceMock;
    private LoginController loginController;

    @Before
    public void init() {
        super.init();
        userServiceMock = mock(UserService.class);
        userActivationServiceMock = mock(UserActivationService.class);
        loginController = new LoginController(userServiceMock, userActivationServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).setViewResolvers(viewResolver).build();
    }

    @Test
    public void loginControllerRendersLoginView() throws Exception {
        MockHttpServletRequestBuilder getRequest = get(LoginController.LOGIN_URL).accept(MediaType.ALL);
        ResultActions results = mockMvc.perform(getRequest);

        results.andExpect(status().isOk());
        results.andExpect(view().name(LoginController.LOGIN_VIEW_NAME));
    }

    @Test
    public void loginControllerRendersLoginViewWithUsernameAndPassword() throws Exception {
        MockHttpServletRequestBuilder getRequest = get(LoginController.LOGIN_URL).accept(MediaType.ALL);
        ResultActions results = mockMvc.perform(getRequest);

        results.andExpect(model().attribute(LoginFormViewModel.VIEWMODEL_NAME, hasProperty("username")));
        results.andExpect(model().attribute(LoginFormViewModel.VIEWMODEL_NAME, hasProperty("password")));
    }

    @Test
    public void loginControllerRedirectsToHomeViewWhenUserIsAlreadyLoggedInAndAttemptsToAccessLoginPage()
            throws Exception {
        MockHttpServletRequestBuilder getRequest = get(LoginController.LOGIN_URL).accept(MediaType.ALL);
        getRequest.sessionAttr(LoginController.USER_ATTRIBUTE_NAME, userMock);

        ResultActions results = mockMvc.perform(getRequest);

        results.andExpect(status().is3xxRedirection());
        results.andExpect(redirectedUrl(LoginController.HOME_URL));
    }

    @Test
    public void loginControllerRedirectsToHomeViewUponSuccessfulLogin() throws Exception {
        ResultActions results = postLoginForm();

        results.andExpect(status().is3xxRedirection());
        results.andExpect(redirectedUrl(LoginController.HOME_URL));
    }

    @Test
    public void loginControllerRetrievesTheUserFromLoginCredentials() throws Exception {
        postLoginForm();
        verify(userServiceMock).getUserByLoginCredentials(SAMPLE_USERNAME, SAMPLE_PASSWORD);
    }

    @Test
    public void loginControllerRendersAlertMessageOnUserServiceExceptionDuringLogin() throws Exception {
        doThrow(new UserServiceException()).when(userServiceMock).getUserByLoginCredentials(SAMPLE_USERNAME,
                SAMPLE_PASSWORD);

        ResultActions results = postLoginForm();

        results.andExpect(view().name(RegistrationController.LOGIN_VIEW_NAME));
        results.andExpect(model().attribute(AlertMessageViewModel.VIEWMODEL_NAME,
                hasProperty("messageType", is(AlertMessageType.ERROR))));
    }

    @Test
    public void loginControllerRedirectsToEmailReconfirmationViewOnUserActivationServiceExceptionDuringLogin()
            throws Exception {
        when(userServiceMock.getUserByLoginCredentials(SAMPLE_USERNAME, SAMPLE_PASSWORD)).thenReturn(userMock);
        doThrow(new UserActivationServiceException()).when(userActivationServiceMock).validateActivation(userMock);

        ResultActions results = postLoginForm();

        results.andExpect(status().is3xxRedirection());
        results.andExpect(redirectedUrl(LoginController.EMAIL_RECONFIRM_URL));
    }

    @Test
    public void logoutControllerRendersLoginPageUponLogout() throws Exception {
        MockHttpServletRequestBuilder getRequest = get(LoginController.LOGOUT_URL).accept(MediaType.ALL);
        ResultActions results = mockMvc.perform(getRequest);

        results.andExpect(view().name(LoginController.LOGIN_VIEW_NAME));
        results.andExpect(status().isOk());
    }

    private ResultActions postLoginForm() throws Exception {
        MockHttpServletRequestBuilder postRequest = post(LoginController.LOGIN_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        postRequest = buildLoginFormParams(postRequest);

        return mockMvc.perform(postRequest);
    }

    private MockHttpServletRequestBuilder buildLoginFormParams(MockHttpServletRequestBuilder postRequest) {
        return postRequest.param(USERNAME_PARAMETER_NAME, SAMPLE_USERNAME)
                .param(PASSWORD_PARAMETER_NAME, SAMPLE_PASSWORD).session(mockHttpSession);
    }
}
