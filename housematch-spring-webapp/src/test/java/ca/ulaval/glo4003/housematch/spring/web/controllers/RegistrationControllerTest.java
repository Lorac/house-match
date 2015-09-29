package ca.ulaval.glo4003.housematch.spring.web.controllers;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ca.ulaval.glo4003.housematch.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.email.MailSendException;
import ca.ulaval.glo4003.housematch.services.UserService;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.AlertMessageType;
import ca.ulaval.glo4003.housematch.validators.UserCreationValidationException;

public class RegistrationControllerTest extends MvcControllerTest {

    private static final String USERNAME_PARAMETER_NAME = "username";
    private static final String SAMPLE_USERNAME = "Alice";
    private static final String PASSWORD_PARAMETER_NAME = "password";
    private static final String SAMPLE_PASSWORD = "PASSWORD1234";
    private static final String EMAIL_PARAMETER_NAME = "email";
    private static final String SAMPLE_EMAIL = "email@hotmail.com";
    private static final String ROLE_PARAMETER_NAME = "role";
    private static final UserRole SAMPLE_ROLE = UserRole.SELLER;
    private static final int SAMPLE_ACTIVATION_HASH_CODE = 234234;

    private UserService userServiceMock;
    private RegistrationController registerController;

    @Before
    public void init() {
        super.init();
        userServiceMock = mock(UserService.class);
        registerController = new RegistrationController(userServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(registerController).setViewResolvers(viewResolver).build();
    }

    @Test
    public void registrationControllerRendersRegistrationView() throws Exception {
        MockHttpServletRequestBuilder getRequest = get("/register").accept(MediaType.ALL);

        ResultActions results = mockMvc.perform(getRequest);

        results.andExpect(status().isOk());
        results.andExpect(view().name("register"));
    }

    @Test
    public void registrationControllerRendersRegistrationViewWithTheCorrectFields() throws Exception {
        MockHttpServletRequestBuilder getRequest = get(RegistrationController.REGISTRATION_URL).accept(MediaType.ALL);
        ResultActions results = mockMvc.perform(getRequest);

        results.andExpect(model().attribute(LoginController.REGISTRATION_FORM_VIEWMODEL_NAME, hasProperty("username")));
        results.andExpect(model().attribute(LoginController.REGISTRATION_FORM_VIEWMODEL_NAME, hasProperty("email")));
        results.andExpect(model().attribute(LoginController.REGISTRATION_FORM_VIEWMODEL_NAME, hasProperty("password")));
        results.andExpect(model().attribute(LoginController.REGISTRATION_FORM_VIEWMODEL_NAME, hasProperty("role")));
    }

    @Test
    public void registrationControllerRendersActivationNoticeViewUponSuccessfulRegistration() throws Exception {
        ResultActions results = postRegistrationForm();

        results.andExpect(status().isOk());
        results.andExpect(view().name(RegistrationController.ACTIVATION_NOTICE_VIEW_NAME));
    }

    @Test
    public void registrationControllerCreatesUserFromTheSpecifiedRegistrationFormViewModelDuringRegistration() throws Exception {
        postRegistrationForm();

        verify(userServiceMock).createUser(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
    }

    @Test
    public void registrationControllerRendersAlertMessageOnMailSendExceptionDuringRegistration() throws Exception {
        doThrow(new MailSendException()).when(userServiceMock).createUser(SAMPLE_USERNAME, SAMPLE_EMAIL,
                SAMPLE_PASSWORD, SAMPLE_ROLE);

        ResultActions results = postRegistrationForm();

        results.andExpect(view().name(RegistrationController.REGISTRATION_VIEW_NAME));
        results.andExpect(model().attribute(RegistrationController.ALERT_MESSAGE_VIEW_MODEL_NAME,
                hasProperty("messageType", is(AlertMessageType.ERROR))));
    }

    @Test
    public void registrationControllerRendersAlertMessageOnUserAlreadyExistsExceptionDuringRegistration() throws Exception {
        doThrow(new UserAlreadyExistsException()).when(userServiceMock).createUser(SAMPLE_USERNAME, SAMPLE_EMAIL,
                SAMPLE_PASSWORD, SAMPLE_ROLE);

        ResultActions results = postRegistrationForm();

        results.andExpect(view().name(RegistrationController.REGISTRATION_VIEW_NAME));
        results.andExpect(model().attribute(RegistrationController.ALERT_MESSAGE_VIEW_MODEL_NAME,
                hasProperty("messageType", is(AlertMessageType.ERROR))));
    }

    @Test
    public void registrationControllerRendersAlertMessageOnUserCreationValidationExceptionDuringRegistration() throws Exception {
        doThrow(new UserCreationValidationException()).when(userServiceMock).createUser(SAMPLE_USERNAME, SAMPLE_EMAIL,
                SAMPLE_PASSWORD, SAMPLE_ROLE);

        ResultActions results = postRegistrationForm();

        results.andExpect(view().name(RegistrationController.REGISTRATION_VIEW_NAME));
        results.andExpect(model().attribute(RegistrationController.ALERT_MESSAGE_VIEW_MODEL_NAME,
                hasProperty("messageType", is(AlertMessageType.ERROR))));
    }

    @Test
    public void registrationControllerActivatesTheUserDuringActivation() throws Exception {
        performActivationRequest();

        verify(userServiceMock).activateUser(SAMPLE_ACTIVATION_HASH_CODE);
    }

    @Test
    public void registrationControllerRendersLoginViewUponSuccessfulActivation() throws Exception {
        ResultActions results = performActivationRequest();

        results.andExpect(status().isOk());
        results.andExpect(view().name(RegistrationController.LOGIN_VIEW_NAME));
    }

    @Test
    public void activationControllerRendersAlertMessageOnUserNotFoundExceptionDuringRegistration() throws Exception {
        doThrow(new UserNotFoundException()).when(userServiceMock).activateUser(SAMPLE_ACTIVATION_HASH_CODE);

        ResultActions results = performActivationRequest();

        results.andExpect(view().name(RegistrationController.LOGIN_VIEW_NAME));
        results.andExpect(model().attribute(RegistrationController.ALERT_MESSAGE_VIEW_MODEL_NAME,
                hasProperty("messageType", is(AlertMessageType.ERROR))));
    }

    private ResultActions postRegistrationForm() throws Exception {
        MockHttpServletRequestBuilder postRequest = post(RegistrationController.REGISTRATION_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        postRequest = buildRegistrationFormParams(postRequest);

        return mockMvc.perform(postRequest);
    }

    private ResultActions performActivationRequest() throws Exception {
        MockHttpServletRequestBuilder getRequest = get(
                RegistrationController.ACTIVATION_BASE_URL + SAMPLE_ACTIVATION_HASH_CODE).accept(MediaType.ALL);

        return mockMvc.perform(getRequest);
    }

    private MockHttpServletRequestBuilder buildRegistrationFormParams(MockHttpServletRequestBuilder postRequest) {
        return postRequest.param(USERNAME_PARAMETER_NAME, SAMPLE_USERNAME)
                .param(PASSWORD_PARAMETER_NAME, SAMPLE_PASSWORD).param(EMAIL_PARAMETER_NAME, SAMPLE_EMAIL)
                .param(ROLE_PARAMETER_NAME, SAMPLE_ROLE.toString());
    }
}
