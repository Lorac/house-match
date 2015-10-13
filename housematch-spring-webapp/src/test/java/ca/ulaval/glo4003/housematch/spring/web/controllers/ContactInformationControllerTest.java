package ca.ulaval.glo4003.housematch.spring.web.controllers;

import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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

import ca.ulaval.glo4003.housematch.services.user.UserService;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.ContactInformationFormViewModel;

public class ContactInformationControllerTest extends MvcControllerTest {

    private static final String ADDRESS_PARAMETER_NAME = "address";
    private static final String EMAIL_PARAMETER_NAME = "email";
    private static final String SAMPLE_EMAIL = "Potato@gmail.com";

    private UserService userServiceMock;
    private ContactInformationController contactInformationController;

    @Before
    public void init() {
        super.init();
        userServiceMock = mock(UserService.class);
        contactInformationController = new ContactInformationController(authorizationValidatorMock, userServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(contactInformationController).setViewResolvers(viewResolver).build();
    }

    @Test
    public void userProfileControllerRendersModifyUserProfileView() throws Exception {
        ResultActions results = performGetRequest(ContactInformationController.CONTACT_INFO_UPDATE_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(ContactInformationController.CONTACT_INFO_UPDATE_VIEW_NAME));
    }

    @Test
    public void userProfileControllerRendersUserProfileControllerWithTheCorrectFields() throws Exception {
        ResultActions results = performGetRequest(ContactInformationController.CONTACT_INFO_UPDATE_URL);

        results.andExpect(
                model().attribute(ContactInformationFormViewModel.VIEWMODEL_NAME, hasProperty(ADDRESS_PARAMETER_NAME)));
        results.andExpect(
                model().attribute(ContactInformationFormViewModel.VIEWMODEL_NAME, hasProperty(EMAIL_PARAMETER_NAME)));
    }

    @Test
    public void userProfileControllerCallsAuthorizationValidationServiceOnUserProfileViewAccess() throws Exception {
        performGetRequest(PropertyListingController.CONTACT_INFO_UPDATE_URL);
        verify(authorizationValidatorMock).validateResourceAccess(
                PropertyListingController.CONTACT_INFO_UPDATE_VIEW_NAME, mockHttpSession,
                PropertyListingController.USER_ATTRIBUTE_NAME);
    }

    @Test
    public void userProfileControllerRendersUserProfileConfirmationUponModification() throws Exception {
        ResultActions results = postUserProfileModificationForm();

        results.andExpect(status().isOk());
        results.andExpect(view().name(ContactInformationController.CONTACT_INFO_UPDATE_CONFIRMATION_VIEW_NAME));
    }

    private ResultActions postUserProfileModificationForm() throws Exception {
        MockHttpServletRequestBuilder postRequest = post(ContactInformationController.CONTACT_INFO_UPDATE_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        postRequest = buildPropertyListingCreationFormParams(postRequest);

        return mockMvc.perform(postRequest);
    }

    private MockHttpServletRequestBuilder buildPropertyListingCreationFormParams(
            MockHttpServletRequestBuilder postRequest) {
        return postRequest.param(EMAIL_PARAMETER_NAME, SAMPLE_EMAIL).session(mockHttpSession);
    }
}
