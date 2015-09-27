package ca.ulaval.glo4003.housematch.spring.web.controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.services.UserService;

public class RegistrationControllerTest extends MvcControllerTest {

    private static final String USERNAME_PARAMETER_NAME = "username";
    private static final String SAMPLE_USERNAME = "Alice";
    private static final String PASSWORD_PARAMETER_NAME = "password";
    private static final String SAMPLE_PASSWORD = "PASSWORD1234";
    private static final String EMAIL_PARAMETER_NAME = "email";
    private static final String SAMPLE_EMAIL = "email@hotmail.com";
    private static final String ROLE_PARAMETER_NAME = "role";
    private static final UserRole SAMPLE_ROLE = UserRole.SELLER;

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
    public void registrationControllerRendersActivationNoticeViewUponSuccessfulRegistration() throws Exception {
        MockHttpServletRequestBuilder postRequest = post(RegistrationController.REGISTRATION_REQUEST_MAPPING)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);

        ResultActions results = mockMvc.perform(postRequest);

        results.andExpect(status().isOk());
        results.andExpect(view().name(RegistrationController.ACTIVATION_NOTICE_VIEW_NAME));
    }

    @Test
    public void registrationControllerCreatesUserFromTheSpecifiedLoginFormViewModel() throws Exception {
        MockHttpServletRequestBuilder postRequest = post(RegistrationController.REGISTRATION_REQUEST_MAPPING)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        postRequest = buildRegistrationFormParams(postRequest);

        mockMvc.perform(postRequest);

        verify(userServiceMock).createUser(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
    }

    private MockHttpServletRequestBuilder buildRegistrationFormParams(MockHttpServletRequestBuilder postRequest) {
        return postRequest.param(USERNAME_PARAMETER_NAME, SAMPLE_USERNAME)
                .param(PASSWORD_PARAMETER_NAME, SAMPLE_PASSWORD).param(EMAIL_PARAMETER_NAME, SAMPLE_EMAIL)
                .param(ROLE_PARAMETER_NAME, SAMPLE_ROLE.toString());
    }
}
