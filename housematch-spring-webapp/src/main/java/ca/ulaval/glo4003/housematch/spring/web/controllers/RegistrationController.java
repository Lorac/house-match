package ca.ulaval.glo4003.housematch.spring.web.controllers;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.services.user.InvalidEmailException;
import ca.ulaval.glo4003.housematch.services.user.UserActivationService;
import ca.ulaval.glo4003.housematch.services.user.UserActivationServiceException;
import ca.ulaval.glo4003.housematch.services.user.UserService;
import ca.ulaval.glo4003.housematch.services.user.UserServiceException;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.AlertMessageType;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.EmailReconfirmFormViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.LoginFormViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.RegistrationFormViewModel;

@Controller
public class RegistrationController extends MvcController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserActivationService userActivationService;

    protected RegistrationController() {
        // Required for Mockito
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
        modelMap.put(RegistrationFormViewModel.VIEWMODEL_NAME, new RegistrationFormViewModel());
        return new ModelAndView(REGISTRATION_VIEW_NAME, modelMap);
    }

    @RequestMapping(value = REGISTRATION_URL, method = RequestMethod.POST)
    public final ModelAndView register(RegistrationFormViewModel registrationForm) {
        try {
            userService.registerUser(registrationForm.getUsername(), registrationForm.getEmail(),
                    registrationForm.getPassword(), registrationForm.getRole());
            return new ModelAndView(ACTIVATION_NOTICE_VIEW_NAME);
        } catch (UserServiceException e) {
            return showAlertMessage(REGISTRATION_VIEW_NAME, registrationForm, e.getMessage());
        }
    }

    @RequestMapping(value = EMAIL_RECONFIRM_URL, method = RequestMethod.GET)
    public final ModelAndView displayEmailReconfirmView(EmailReconfirmFormViewModel emailReconfirmForm,
            ModelMap modelMap, RedirectAttributes redirectAttributes) {

        modelMap.put(EmailReconfirmFormViewModel.VIEWMODEL_NAME, new EmailReconfirmFormViewModel());
        return new ModelAndView(EMAIL_RECONFIRM_VIEW_NAME);
    }

    @RequestMapping(value = EMAIL_RECONFIRM_URL, method = RequestMethod.POST)
    public final ModelAndView resendActivationLink(EmailReconfirmFormViewModel emailReconfirmForm,
            HttpSession session) {

        try {
            userService.updateUserEmail(getUserFromHttpSession(session), emailReconfirmForm.getEmail());
            session.invalidate();
            return new ModelAndView(ACTIVATION_NOTICE_VIEW_NAME);
        } catch (UserActivationServiceException | InvalidEmailException e) {
            return showAlertMessage(EMAIL_RECONFIRM_VIEW_NAME, emailReconfirmForm, e.getMessage());
        }
    }

    @RequestMapping(value = ACTIVATION_URL, method = RequestMethod.GET)
    public final ModelAndView activate(@PathVariable UUID activationCode, RedirectAttributes redirectAttributes) {
        try {
            userActivationService.completeActivation(activationCode);
            return showAlertMessage(LOGIN_VIEW_NAME, new LoginFormViewModel(),
                    "Your account has been successfully activated. You can now log in.", AlertMessageType.SUCCESS);
        } catch (UserActivationServiceException e) {
            return showAlertMessage(LOGIN_VIEW_NAME, new LoginFormViewModel(), e.getMessage());
        }
    }
}
