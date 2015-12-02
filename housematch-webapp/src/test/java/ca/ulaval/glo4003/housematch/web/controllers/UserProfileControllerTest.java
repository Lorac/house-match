package ca.ulaval.glo4003.housematch.web.controllers;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationSettings;
import ca.ulaval.glo4003.housematch.services.user.UserService;
import ca.ulaval.glo4003.housematch.services.user.UserServiceException;
import ca.ulaval.glo4003.housematch.web.converters.NotificationSettingsConverter;
import ca.ulaval.glo4003.housematch.web.viewmodels.AlertMessageType;
import ca.ulaval.glo4003.housematch.web.viewmodels.AlertMessageViewModel;
import ca.ulaval.glo4003.housematch.web.viewmodels.ContactInformationFormViewModel;
import ca.ulaval.glo4003.housematch.web.viewmodels.NotificationSettingsFormViewModel;

public class UserProfileControllerTest extends BaseControllerTest {

    private static final String ADDRESS_PARAMETER_NAME = "address";
    private static final String EMAIL_PARAMETER_NAME = "email";
    private static final String SAMPLE_EMAIL = "Potato@gmail.com";

    private UserService userServiceMock;
    private NotificationSettingsConverter notificationSettingsConverterMock;
    private NotificationSettings notificationSettingsMock;

    private UserProfileController userProfileController;

    @Before
    public void init() throws Exception {
        super.init();
        userServiceMock = mock(UserService.class);
        notificationSettingsConverterMock = mock(NotificationSettingsConverter.class);
        notificationSettingsMock = mock(NotificationSettings.class);

        userProfileController = new UserProfileController(userServiceMock, notificationSettingsConverterMock);
        mockMvc = MockMvcBuilders.standaloneSetup(userProfileController).setViewResolvers(viewResolver).build();
    }

    @Test
    public void userProfileControllerRendersUserProfileSettingsView() throws Exception {
        ResultActions results = performGetRequest(UserProfileController.USER_PROFILE_SETTINGS_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(UserProfileController.USER_PROFILE_SETTINGS_VIEW_NAME));
    }

    @Test
    public void userProfileControllerRendersContactInformationUpdateView() throws Exception {
        ResultActions results = performGetRequest(UserProfileController.CONTACT_INFO_UPDATE_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(UserProfileController.CONTACT_INFO_UPDATE_VIEW_NAME));
    }

    @Test
    public void userProfileControllerRendersContactInformationUpdateViewWithTheCorrectFields() throws Exception {
        ResultActions results = performGetRequest(UserProfileController.CONTACT_INFO_UPDATE_URL);

        results.andExpect(model().attribute(ContactInformationFormViewModel.NAME, hasProperty(ADDRESS_PARAMETER_NAME)));
        results.andExpect(model().attribute(ContactInformationFormViewModel.NAME, hasProperty(EMAIL_PARAMETER_NAME)));
    }

    @Test
    public void userProfileControllerRendersUpdateConfirmationUponSuccessfulContactInformationUpdate() throws Exception {
        ResultActions results = postContactInformationUpdateForm();

        results.andExpect(status().isOk());
        results.andExpect(view().name(UserProfileController.CONTACT_INFO_UPDATE_CONFIRMATION_VIEW_NAME));
    }

    @Test
    public void userProfileControllerRendersAlertMessageOnUserServiceExceptionDuringUpdate() throws Exception {
        doThrow(new UserServiceException()).when(userServiceMock).updateUserContactInformation(eq(userMock), any(Address.class),
                eq(SAMPLE_EMAIL));

        ResultActions results = postContactInformationUpdateForm();

        results.andExpect(view().name(UserProfileController.CONTACT_INFO_UPDATE_VIEW_NAME));
        results.andExpect(model().attribute(AlertMessageViewModel.NAME, hasProperty("messageType", is(AlertMessageType.ERROR))));
    }

    @Test
    public void userProfileControllerRendersNotificationSettingsView() throws Exception {
        ResultActions results = performGetRequest(UserProfileController.NOTIFICATION_SETTINGS_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(UserProfileController.NOTIFICATION_SETTINGS_VIEW_NAME));
    }

    @Test
    public void userProfileControllerGetsTheUserNotificationSettingsFromTheUserServiceOnNotificationSettingsViewRequest() throws Exception {
        performGetRequest(UserProfileController.NOTIFICATION_SETTINGS_URL);
        verify(userServiceMock).getUserNotificationSettings(userMock);
    }

    @Test
    public void userProfileControllerConvertsTheNotificationSettingsUsingTheConverterOnNotificationSettingsViewRequest() throws Exception {
        when(userServiceMock.getUserNotificationSettings(userMock)).thenReturn(notificationSettingsMock);
        performGetRequest(UserProfileController.NOTIFICATION_SETTINGS_URL);
        verify(notificationSettingsConverterMock).convert(notificationSettingsMock);
    }

    @Test
    public void userProfileControllerUpdatesTheUserNotificationSettingsUsingTheUserServiceOnNotificationSettingsUpdateRequest()
            throws Exception {
        when(notificationSettingsConverterMock.convert(any(NotificationSettingsFormViewModel.class))).thenReturn(notificationSettingsMock);
        postNotificationSettingsUpdateForm();
        verify(userServiceMock).updateUserNotificationSettings(userMock, notificationSettingsMock);
    }

    @Test
    public void userProfileControllerRedirectsToUserProfileSettingsViewOnNotificationSettingsUponSuccessfulUpdateRequest()
            throws Exception {
        ResultActions results = postNotificationSettingsUpdateForm();

        results.andExpect(status().is3xxRedirection());
        results.andExpect(redirectedUrl(UserProfileController.USER_PROFILE_SETTINGS_URL));
    }

    private ResultActions postContactInformationUpdateForm() throws Exception {
        MockHttpServletRequestBuilder postRequest = post(UserProfileController.CONTACT_INFO_UPDATE_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        postRequest = buildContactInformationUpdateFormParams(postRequest);

        return mockMvc.perform(postRequest);
    }

    private MockHttpServletRequestBuilder buildContactInformationUpdateFormParams(MockHttpServletRequestBuilder postRequest) {
        return postRequest.param(EMAIL_PARAMETER_NAME, SAMPLE_EMAIL).session(mockHttpSession);
    }

    private ResultActions postNotificationSettingsUpdateForm() throws Exception {
        MockHttpServletRequestBuilder postRequest = post(UserProfileController.NOTIFICATION_SETTINGS_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED).session(mockHttpSession);
        return mockMvc.perform(postRequest);
    }
}
