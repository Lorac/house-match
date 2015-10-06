package ca.ulaval.glo4003.housematch.spring.web.controllers;

import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Mockito.mock;
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
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.ProfileModificationFormViewModel;

public class ProfileModificationControllerTest extends MvcControllerTest {

    private static final String ADDRESS_PARAMETER_NAME = "address";
    private static final String EMAIL_PARAMETER_NAME = "email";
    private static final String SAMPLE_EMAIL = "Potato@gmail.com";
    private static final String INVALID_EMAIL = "x";
    private static final String SAMPLE_USERNAME = "Potato";

    private UserService userServiceMock;
    private ProfileModificationController profileModificationController;

    @Before
    public void init() {
        super.init();
        userServiceMock = mock(UserService.class);
        profileModificationController = new ProfileModificationController(userServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(profileModificationController).setViewResolvers(viewResolver).build();
    }

    @Test
    public void profileModificationControllerRendersModifyUserProfileView() throws Exception {
        ResultActions results = performGetRequest(ProfileModificationController.MODIFY_USER_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(ProfileModificationController.MODIFY_USER_VIEW_NAME));
    }

    @Test
    public void profileModificationControllerRendersPropertyListingControllerWithTheCorrectFields() throws Exception {
        ResultActions results = performGetRequest(ProfileModificationController.MODIFY_USER_URL);

        results.andExpect(model().attribute(ProfileModificationFormViewModel.VIEWMODEL_NAME,
                hasProperty(ADDRESS_PARAMETER_NAME)));
        results.andExpect(
                model().attribute(ProfileModificationFormViewModel.VIEWMODEL_NAME, hasProperty(EMAIL_PARAMETER_NAME)));
    }

    @Test
    public void profileModificationControllerRendersprofileModificationConfirmationUponModification() throws Exception {
        ResultActions results = postProfileModificationCreationForm();

        results.andExpect(status().isOk());
        results.andExpect(view().name(ProfileModificationController.MODIFIED_USER_SAVED_VIEW_NAME));
    }

    private ResultActions postProfileModificationCreationForm() throws Exception {
        MockHttpServletRequestBuilder postRequest = post(ProfileModificationController.MODIFY_USER_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        postRequest = buildPropertyListingCreationFormParams(postRequest);

        return mockMvc.perform(postRequest);
    }

    private MockHttpServletRequestBuilder buildPropertyListingCreationFormParams(
            MockHttpServletRequestBuilder postRequest) {
        return postRequest.param(EMAIL_PARAMETER_NAME, SAMPLE_EMAIL).session(mockHttpSession);
    }
}
