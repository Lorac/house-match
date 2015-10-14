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

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.services.user.UserActivationService;
import ca.ulaval.glo4003.housematch.services.user.UserActivationServiceException;
import ca.ulaval.glo4003.housematch.services.user.UserService;
import ca.ulaval.glo4003.housematch.services.user.UserServiceException;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.AlertMessageType;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.AlertMessageViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.EmailReconfirmFormViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.RegistrationFormViewModel;

public class RegistrationControllerTest extends MvcControllerTest {

    private static final String USERNAME_PARAMETER_NAME = "username";
    private static final String SAMPLE_USERNAME = "Alice";
    private static final String PASSWORD_PARAMETER_NAME = "password";
    private static final String SAMPLE_PASSWORD = "PASSWORD1234";
    private static final String EMAIL_PARAMETER_NAME = "email";
    private static final String SAMPLE_EMAIL = "email@hotmail.com";
    private static final String ROLE_PARAMETER_NAME = "role";
    private static final UserRole SAMPLE_ROLE = UserRole.SELLER;
    private static final UUID SAMPLE_ACTIVATION_CODE = UUID.randomUUID();

    private UserService userServiceMock;
    private UserActivationService userActivationServiceMock;
    private RegistrationController registerController;

    @Before
    public void init() throws Exception {
        super.init();
        userServiceMock = mock(UserService.class);
        userActivationServiceMock = mock(UserActivationService.class);
        registerController = new RegistrationController(userServiceMock, userActivationServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(registerController).setViewResolvers(viewResolver).build();
    }

    @Test
    public void registrationControllerRendersRegistrationView() throws Exception {
        ResultActions results = performGetRequest(RegistrationController.REGISTRATION_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(RegistrationController.REGISTRATION_VIEW_NAME));
    }

    @Test
    public void registrationControllerRendersRegistrationViewWithTheCorrectFields() throws Exception {
        ResultActions results = performGetRequest(RegistrationController.REGISTRATION_URL);

        results.andExpect(
                model().attribute(RegistrationFormViewModel.VIEWMODEL_NAME, hasProperty(USERNAME_PARAMETER_NAME)));
        results.andExpect(
                model().attribute(RegistrationFormViewModel.VIEWMODEL_NAME, hasProperty(EMAIL_PARAMETER_NAME)));
        results.andExpect(
                model().attribute(RegistrationFormViewModel.VIEWMODEL_NAME, hasProperty(PASSWORD_PARAMETER_NAME)));
        results.andExpect(
                model().attribute(RegistrationFormViewModel.VIEWMODEL_NAME, hasProperty(ROLE_PARAMETER_NAME)));
    }

    @Test
    public void registrationControllerRendersActivationNoticeViewUponSuccessfulRegistration() throws Exception {
        ResultActions results = postRegistrationForm();

        results.andExpect(status().isOk());
        results.andExpect(view().name(RegistrationController.ACTIVATION_NOTICE_VIEW_NAME));
    }

    @Test
    public void registrationControllerRegistersUserFromTheSpecifiedRegistrationFormViewModelDuringRegistration()
            throws Exception {
        postRegistrationForm();

        verify(userServiceMock).registerUser(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
    }

    @Test
    public void registrationControllerRendersAlertMessageOnUserServiceExceptionDuringRegistration() throws Exception {
        doThrow(new UserServiceException()).when(userServiceMock).registerUser(SAMPLE_USERNAME, SAMPLE_EMAIL,
                SAMPLE_PASSWORD, SAMPLE_ROLE);

        ResultActions results = postRegistrationForm();

        results.andExpect(view().name(RegistrationController.REGISTRATION_VIEW_NAME));
        results.andExpect(model().attribute(AlertMessageViewModel.VIEWMODEL_NAME,
                hasProperty("messageType", is(AlertMessageType.ERROR))));
    }

    @Test
    public void registrationControllerRendersEmailReconfirmationView() throws Exception {
        ResultActions results = performGetRequest(RegistrationController.EMAIL_RECONFIRM_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(RegistrationController.EMAIL_RECONFIRM_VIEW_NAME));
    }

    @Test
    public void registrationControllerRendersEmailReconfirmationWithTheCorrectFields() throws Exception {
        ResultActions results = performGetRequest(RegistrationController.EMAIL_RECONFIRM_URL);

        results.andExpect(
                model().attribute(EmailReconfirmFormViewModel.VIEWMODEL_NAME, hasProperty(EMAIL_PARAMETER_NAME)));
    }

    @Test
    public void registrationControllerUpdatesTheEmailOfTheUserObjectDuringEmailReconfirmation() throws Exception {
        postEmailReconfirmationForm();

        verify(userServiceMock).updateUserEmail(userMock, SAMPLE_EMAIL);
    }

    @Test
    public void registrationControllerRendersAlertMessageOnUserActivationServiceExceptionDuringEmailReconfirmation()
            throws Exception {
        doThrow(new UserActivationServiceException()).when(userServiceMock).updateUserEmail(userMock, SAMPLE_EMAIL);

        ResultActions results = postEmailReconfirmationForm();

        results.andExpect(view().name(RegistrationController.EMAIL_RECONFIRM_VIEW_NAME));
        results.andExpect(model().attribute(AlertMessageViewModel.VIEWMODEL_NAME,
                hasProperty("messageType", is(AlertMessageType.ERROR))));
    }

    @Test
    public void registrationControllerRendersActivationNoticeViewUponSuccessfulEmailReconfirmation() throws Exception {
        ResultActions results = postEmailReconfirmationForm();

        results.andExpect(status().isOk());
        results.andExpect(view().name(RegistrationController.ACTIVATION_NOTICE_VIEW_NAME));
    }

    @Test
    public void registrationControllerActivatesTheUserDuringActivation() throws Exception {
        performActivationRequest();

        verify(userActivationServiceMock).completeActivation(SAMPLE_ACTIVATION_CODE);
    }

    @Test
    public void registrationControllerRendersLoginViewUponSuccessfulActivation() throws Exception {
        ResultActions results = performActivationRequest();

        results.andExpect(status().isOk());
        results.andExpect(view().name(RegistrationController.LOGIN_VIEW_NAME));
    }

    @Test
    public void registrationControllerRendersAlertMessageOnUserActivationServiceExceptionDuringActivation()
            throws Exception {
        doThrow(new UserActivationServiceException()).when(userActivationServiceMock)
                .completeActivation(SAMPLE_ACTIVATION_CODE);

        ResultActions results = performActivationRequest();

        results.andExpect(view().name(RegistrationController.LOGIN_VIEW_NAME));
        results.andExpect(model().attribute(AlertMessageViewModel.VIEWMODEL_NAME,
                hasProperty("messageType", is(AlertMessageType.ERROR))));
    }

    private ResultActions postRegistrationForm() throws Exception {
        MockHttpServletRequestBuilder postRequest = post(RegistrationController.REGISTRATION_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        postRequest = buildRegistrationFormParams(postRequest);

        return mockMvc.perform(postRequest);
    }

    private ResultActions postEmailReconfirmationForm() throws Exception {
        MockHttpServletRequestBuilder postRequest = post(RegistrationController.EMAIL_RECONFIRM_URL);
        postRequest.accept(MediaType.APPLICATION_FORM_URLENCODED);
        postRequest.param(EMAIL_PARAMETER_NAME, SAMPLE_EMAIL);
        postRequest.sessionAttr(RegistrationController.USER_ATTRIBUTE_NAME, userMock);

        return mockMvc.perform(postRequest);
    }

    private ResultActions performActivationRequest() throws Exception {
        MockHttpServletRequestBuilder getRequest = get(
                RegistrationController.ACTIVATION_BASE_URL + SAMPLE_ACTIVATION_CODE);
        getRequest.accept(MediaType.ALL);

        return mockMvc.perform(getRequest);
    }

    private MockHttpServletRequestBuilder buildRegistrationFormParams(MockHttpServletRequestBuilder postRequest) {
        return postRequest.param(USERNAME_PARAMETER_NAME, SAMPLE_USERNAME)
                .param(PASSWORD_PARAMETER_NAME, SAMPLE_PASSWORD).param(EMAIL_PARAMETER_NAME, SAMPLE_EMAIL)
                .param(ROLE_PARAMETER_NAME, SAMPLE_ROLE.toString());
    }
}
