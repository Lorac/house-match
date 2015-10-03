package ca.ulaval.glo4003.housematch.spring.web.controllers;

import java.util.List;

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

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.email.MailSendException;
import ca.ulaval.glo4003.housematch.services.UserActivationService;
import ca.ulaval.glo4003.housematch.services.UserService;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.AlertMessageType;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.EmailReconfirmFormViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.LoginFormViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.RegistrationFormViewModel;
import ca.ulaval.glo4003.housematch.validators.UserCreationValidationException;

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
        modelMap.put(REGISTRATION_FORM_VIEWMODEL_NAME, new RegistrationFormViewModel());
        return new ModelAndView(REGISTRATION_VIEW_NAME, modelMap);
    }

    @RequestMapping(value = REGISTRATION_URL, method = RequestMethod.POST)
    public final ModelAndView register(RegistrationFormViewModel registerForm, ModelMap modelMap, HttpSession session) {
        try {
            userService.createUser(registerForm.getUsername(), registerForm.getEmail(), registerForm.getPassword(),
                    registerForm.getRole());
        } catch (UserCreationValidationException e) {
            return showAlertMessage(REGISTRATION_VIEW_NAME, REGISTRATION_FORM_VIEWMODEL_NAME, registerForm,
                    e.getMessage(), AlertMessageType.ERROR);
        } catch (UserAlreadyExistsException e) {
            return showAlertMessage(REGISTRATION_VIEW_NAME, REGISTRATION_FORM_VIEWMODEL_NAME, registerForm,
                    "A user with this username already exists.", AlertMessageType.ERROR);
        } catch (MailSendException e) {
            return showAlertMessage(REGISTRATION_VIEW_NAME, REGISTRATION_FORM_VIEWMODEL_NAME, registerForm,
                    "Could not send activation mail. Please check that the email address you entered is valid.",
                    AlertMessageType.ERROR);
        }

        return new ModelAndView(ACTIVATION_NOTICE_VIEW_NAME);
    }

    @RequestMapping(value = EMAIL_RECONFIRM_URL, method = RequestMethod.GET)
    public final ModelAndView displayEmailReconfirmView(EmailReconfirmFormViewModel emailReconfirmForm,
            ModelMap modelMap, HttpSession session, RedirectAttributes redirectAttributes) {
        modelMap.put(EMAIL_RECONFIRM_FORM_VIEWMODEL_NAME, new EmailReconfirmFormViewModel());
        return new ModelAndView(EMAIL_RECONFIRM_VIEW_NAME);
    }

    @RequestMapping(value = EMAIL_RECONFIRM_URL, method = RequestMethod.POST)
    public final ModelAndView resendActivationLink(EmailReconfirmFormViewModel emailReconfirmForm, ModelMap modelMap,
            HttpSession session, RedirectAttributes redirectAttributes) {

        try {
            User user = getUserFromHttpSession(session);
            userActivationService.updateActivationEmail(user, emailReconfirmForm.getEmail());
        } catch (MailSendException e) {
            return showAlertMessage(EMAIL_RECONFIRM_VIEW_NAME, EMAIL_RECONFIRM_FORM_VIEWMODEL_NAME, emailReconfirmForm,
                    "Could not send activation mail. Please check that the email address you entered is valid.",
                    AlertMessageType.ERROR);
        }

        session.invalidate();
        return new ModelAndView(ACTIVATION_NOTICE_VIEW_NAME);
    }

    @RequestMapping(value = ACTIVATION_URL, method = RequestMethod.GET)
    public final ModelAndView activate(@PathVariable Integer activationCode, ModelMap modelMap,
            RedirectAttributes redirectAttributes) {

        try {
            userActivationService.completeActivation(activationCode);
        } catch (UserNotFoundException e) {
            return showAlertMessage(LOGIN_VIEW_NAME, LOGIN_FORM_VIEWMODEL_NAME, new LoginFormViewModel(),
                    "The activation link is not valid.", AlertMessageType.ERROR);
        }

        return showAlertMessage(LOGIN_VIEW_NAME, LOGIN_FORM_VIEWMODEL_NAME, new LoginFormViewModel(),
                "Your account has been successfully activated. You can now log in.", AlertMessageType.SUCCESS);
    }
}
