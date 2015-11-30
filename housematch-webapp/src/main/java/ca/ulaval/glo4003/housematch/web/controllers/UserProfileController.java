package ca.ulaval.glo4003.housematch.web.controllers;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import ca.ulaval.glo4003.housematch.domain.notification.NotificationSettings;
import ca.ulaval.glo4003.housematch.services.user.UserService;
import ca.ulaval.glo4003.housematch.services.user.UserServiceException;
import ca.ulaval.glo4003.housematch.web.converters.NotificationSettingsConverter;
import ca.ulaval.glo4003.housematch.web.viewmodels.ContactInformationFormViewModel;
import ca.ulaval.glo4003.housematch.web.viewmodels.NotificationSettingsFormViewModel;

@Controller
public class UserProfileController extends BaseController {

    public static final String USER_PROFILE_SETTINGS_URL = "/user/profileSettings";
    public static final String CONTACT_INFO_UPDATE_URL = "/user/updateContactInformation";
    public static final String NOTIFICATION_SETTINGS_URL = "/buyer/notificationSettings";
    static final String USER_PROFILE_SETTINGS_VIEW_NAME = "user/profileSettings";
    static final String CONTACT_INFO_UPDATE_VIEW_NAME = "user/contactInformationUpdate";
    static final String CONTACT_INFO_UPDATE_CONFIRMATION_VIEW_NAME = "user/contactInformationUpdateConfirmation";
    static final String NOTIFICATION_SETTINGS_VIEW_NAME = "buyer/notificationSettings";

    @Inject
    private UserService userService;
    @Inject
    private NotificationSettingsConverter notificationSettingsConverter;

    protected UserProfileController() {
        // Required for Mockito
    }

    public UserProfileController(final UserService userService, final NotificationSettingsConverter notificationSettingsConverter) {
        this.userService = userService;
        this.notificationSettingsConverter = notificationSettingsConverter;
    }

    @RequestMapping(value = USER_PROFILE_SETTINGS_URL, method = RequestMethod.GET)
    public final ModelAndView displayUserProfileSettingsView() {
        return new ModelAndView(USER_PROFILE_SETTINGS_VIEW_NAME);
    }

    @RequestMapping(value = CONTACT_INFO_UPDATE_URL, method = RequestMethod.GET)
    public final ModelAndView displayContactInformationView() {
        return new ModelAndView(CONTACT_INFO_UPDATE_VIEW_NAME, ContactInformationFormViewModel.NAME, new ContactInformationFormViewModel());
    }

    @RequestMapping(value = CONTACT_INFO_UPDATE_URL, method = RequestMethod.POST)
    public final ModelAndView submitContactInformation(ContactInformationFormViewModel viewModel, HttpSession httpSession) {
        try {
            userService.updateUserContactInformation(getUserFromHttpSession(httpSession), viewModel.getAddress(), viewModel.getEmail());
            return new ModelAndView(CONTACT_INFO_UPDATE_CONFIRMATION_VIEW_NAME);
        } catch (UserServiceException e) {
            return showAlertMessage(CONTACT_INFO_UPDATE_VIEW_NAME, viewModel, e.getMessage());
        }
    }

    @RequestMapping(value = NOTIFICATION_SETTINGS_URL, method = RequestMethod.GET)
    public final ModelAndView displayNotificationSettingsModificationView(HttpSession httpSession) {
        NotificationSettings notificationSettings = userService.getUserNotificationSettings(getUserFromHttpSession(httpSession));
        NotificationSettingsFormViewModel viewModel = notificationSettingsConverter.convert(notificationSettings);
        return new ModelAndView(NOTIFICATION_SETTINGS_VIEW_NAME, NotificationSettingsFormViewModel.NAME, viewModel);
    }

    @RequestMapping(value = NOTIFICATION_SETTINGS_URL, method = RequestMethod.POST)
    public final ModelAndView updateNotificationSettings(HttpSession httpSession, NotificationSettingsFormViewModel viewModel) {
        NotificationSettings notificationSettings = notificationSettingsConverter.convert(viewModel);
        userService.updateUserNotificationSettings(getUserFromHttpSession(httpSession), notificationSettings);
        return new ModelAndView(new RedirectView(USER_PROFILE_SETTINGS_URL));
    }

}
