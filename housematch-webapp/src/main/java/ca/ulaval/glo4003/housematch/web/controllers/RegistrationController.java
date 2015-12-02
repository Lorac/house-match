package ca.ulaval.glo4003.housematch.web.controllers;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.services.user.UserActivationService;
import ca.ulaval.glo4003.housematch.services.user.UserActivationServiceException;
import ca.ulaval.glo4003.housematch.services.user.UserService;
import ca.ulaval.glo4003.housematch.services.user.UserServiceException;
import ca.ulaval.glo4003.housematch.web.viewmodels.AlertMessageType;
import ca.ulaval.glo4003.housematch.web.viewmodels.EmailReconfirmFormViewModel;
import ca.ulaval.glo4003.housematch.web.viewmodels.LoginFormViewModel;
import ca.ulaval.glo4003.housematch.web.viewmodels.RegistrationFormViewModel;

@Controller
public class RegistrationController extends BaseController {

    public static final String REGISTRATION_URL = "/register";
    static final String REGISTRATION_VIEW_NAME = "registration/registration";
    static final String ACTIVATION_BASE_URL = "/activation/";
    static final String ACTIVATION_URL = "/activation/{activationCode}";
    static final String ACTIVATION_NOTICE_VIEW_NAME = "registration/activationNotice";
    public static final String EMAIL_RECONFIRM_URL = "/emailReconfirm";
    static final String EMAIL_RECONFIRM_VIEW_NAME = "registration/emailReconfirm";

    @Inject
    private UserService userService;
    @Inject
    private UserActivationService userActivationService;

    protected RegistrationController() {
        // Required for Spring init
    }

    public RegistrationController(final UserService userService, final UserActivationService userActivationService) {
        this.userService = userService;
        this.userActivationService = userActivationService;
    }

    @ModelAttribute("publiclyRegistrableRoles")
    public List<UserRole> getPubliclyRegistrableRoles() {
        return userService.getPubliclyRegistrableUserRoles();
    }

    @RequestMapping(value = REGISTRATION_URL, method = RequestMethod.GET)
    public final ModelAndView displayRegistrationView(ModelMap modelMap) {
        modelMap.put(RegistrationFormViewModel.NAME, new RegistrationFormViewModel());
        return new ModelAndView(REGISTRATION_VIEW_NAME, modelMap);
    }

    @RequestMapping(value = REGISTRATION_URL, method = RequestMethod.POST)
    public final ModelAndView register(RegistrationFormViewModel registrationForm) {
        try {
            userService.registerUser(registrationForm.getUsername(), registrationForm.getEmail(), registrationForm.getPassword(),
                    registrationForm.getRole());
            return new ModelAndView(ACTIVATION_NOTICE_VIEW_NAME);
        } catch (UserServiceException e) {
            return showAlertMessage(REGISTRATION_VIEW_NAME, registrationForm, e.getMessage());
        }
    }

    @RequestMapping(value = EMAIL_RECONFIRM_URL, method = RequestMethod.GET)
    public final ModelAndView displayEmailReconfirmView(EmailReconfirmFormViewModel emailReconfirmFormViewModel, ModelMap modelMap,
            RedirectAttributes redirectAttributes) {

        modelMap.put(EmailReconfirmFormViewModel.NAME, new EmailReconfirmFormViewModel());
        return new ModelAndView(EMAIL_RECONFIRM_VIEW_NAME);
    }

    @RequestMapping(value = EMAIL_RECONFIRM_URL, method = RequestMethod.POST)
    public final ModelAndView resendActivationLink(EmailReconfirmFormViewModel emailReconfirmFormViewModel, HttpSession session) {
        try {
            userActivationService.resendActivationMail(getUserFromHttpSession(session), emailReconfirmFormViewModel.getEmail());
            session.invalidate();
            return new ModelAndView(ACTIVATION_NOTICE_VIEW_NAME);
        } catch (UserActivationServiceException e) {
            return showAlertMessage(EMAIL_RECONFIRM_VIEW_NAME, emailReconfirmFormViewModel, e.getMessage());
        }
    }

    @RequestMapping(value = ACTIVATION_URL, method = RequestMethod.GET)
    public final ModelAndView activate(@PathVariable UUID activationCode, RedirectAttributes redirectAttributes) {
        try {
            userActivationService.completeActivation(activationCode);
            return showAlertMessage(LoginController.LOGIN_VIEW_NAME, new LoginFormViewModel(),
                    "Your account has been successfully activated. You can now log in.", AlertMessageType.SUCCESS);
        } catch (UserActivationServiceException e) {
            return showAlertMessage(LoginController.LOGIN_VIEW_NAME, new LoginFormViewModel(), e.getMessage());
        }
    }
}
