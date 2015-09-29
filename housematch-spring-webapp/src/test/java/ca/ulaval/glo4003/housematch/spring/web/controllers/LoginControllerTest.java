package ca.ulaval.glo4003.housematch.spring.web.controllers;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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

import ca.ulaval.glo4003.housematch.domain.user.InvalidPasswordException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotActivatedException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.services.UserService;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.AlertMessageType;

public class LoginControllerTest extends MvcControllerTest {

    private static final String USERNAME_PARAMETER_NAME = "username";
    private static final String SAMPLE_USERNAME = "Alice";
    private static final String PASSWORD_PARAMETER_NAME = "password";
    private static final String SAMPLE_PASSWORD = "PASSWORD1234";

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
        MockHttpServletRequestBuilder getRequest = get(LoginController.LOGIN_URL).accept(MediaType.ALL);
        ResultActions results = mockMvc.perform(getRequest);

        results.andExpect(status().isOk());
        results.andExpect(view().name(LoginController.LOGIN_VIEW_NAME));
    }

    @Test
    public void loginControllerShouldRenderLoginViewWithUsernameAndPassword() throws Exception {
        MockHttpServletRequestBuilder getRequest = get(LoginController.LOGIN_URL).accept(MediaType.ALL);
        ResultActions results = mockMvc.perform(getRequest);

        results.andExpect(model().attribute(LoginController.LOGIN_FORM_VIEWMODEL_NAME, hasProperty("username")));
        results.andExpect(model().attribute(LoginController.LOGIN_FORM_VIEWMODEL_NAME, hasProperty("password")));
    }

    @Test
    public void loginControllerRedirectsToHomeViewUponSuccessfulLogin() throws Exception {
        ResultActions results = postLoginForm();

        results.andExpect(status().is3xxRedirection());
        results.andExpect(redirectedUrl(LoginController.HOME_URL));
    }

    @Test
    public void loginControllerValidatesLoginFromTheSpecifiedLoginFormViewModel() throws Exception {
        postLoginForm();
        verify(userServiceMock).validateUserLogin(SAMPLE_USERNAME, SAMPLE_PASSWORD);
    }

    @Test
    public void loginRequestReturnsErrorMessageOnUserNotFoundException() throws Exception {
        doThrow(new UserNotFoundException()).when(userServiceMock).validateUserLogin(SAMPLE_USERNAME, SAMPLE_PASSWORD);

        ResultActions results = postLoginForm();

        results.andExpect(view().name(RegistrationController.LOGIN_VIEW_NAME));
        results.andExpect(model().attribute(RegistrationController.ALERT_MESSAGE_VIEW_MODEL_NAME,
                hasProperty("messageType", is(AlertMessageType.ERROR))));
    }

    @Test
    public void loginRequestReturnsErrorMessageOnInvalidPasswordException() throws Exception {
        doThrow(new InvalidPasswordException()).when(userServiceMock).validateUserLogin(SAMPLE_USERNAME,
                SAMPLE_PASSWORD);

        ResultActions results = postLoginForm();

        results.andExpect(view().name(RegistrationController.LOGIN_VIEW_NAME));
        results.andExpect(model().attribute(RegistrationController.ALERT_MESSAGE_VIEW_MODEL_NAME,
                hasProperty("messageType", is(AlertMessageType.ERROR))));
    }

    @Test
    public void loginRequestReturnsErrorMessageOnUserNotActivatedException() throws Exception {
        doThrow(new UserNotActivatedException()).when(userServiceMock).validateUserLogin(SAMPLE_USERNAME,
                SAMPLE_PASSWORD);

        ResultActions results = postLoginForm();

        results.andExpect(view().name(RegistrationController.LOGIN_VIEW_NAME));
        results.andExpect(model().attribute(RegistrationController.ALERT_MESSAGE_VIEW_MODEL_NAME,
                hasProperty("messageType", is(AlertMessageType.ERROR))));
    }

    @Test
    public void logoutRequestRendersLoginPageUponLogout() throws Exception {
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
