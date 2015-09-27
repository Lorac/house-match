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

import ca.ulaval.glo4003.housematch.domain.InvalidValueException;
import ca.ulaval.glo4003.housematch.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.email.MailSendException;
import ca.ulaval.glo4003.housematch.services.UserService;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.LoginFormViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.MessageType;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.RegisterFormViewModel;

@Controller
public class RegistrationController extends MvcController {

    @Autowired
    private UserService userService;

    protected RegistrationController() {
        // Required for Mockito
    }

    public RegistrationController(final UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("publiclyRegistrableRoles")
    public List<UserRole> getPubliclyRegistrableRoles() {
        return userService.getPubliclyRegistrableUserRoles();
    }

    @RequestMapping(value = REGISTRATION_REQUEST_MAPPING, method = RequestMethod.GET)
    public final ModelAndView displayRegister(ModelMap modelMap) {
        modelMap.put(REGISTRATION_FORM_VIEWMODEL_NAME, new RegisterFormViewModel());
        return new ModelAndView(REGISTRATION_VIEW_NAME, modelMap);
    }

    @RequestMapping(value = REGISTRATION_REQUEST_MAPPING, method = RequestMethod.POST)
    public final ModelAndView doRegister(RegisterFormViewModel registerForm, ModelMap modelMap, HttpSession session) {
        try {
            userService.createUser(registerForm.getUsername(), registerForm.getEmail(), registerForm.getPassword(),
                    registerForm.getRole());
        } catch (MailSendException e) {
            return showMessage(modelMap, REGISTRATION_VIEW_NAME, REGISTRATION_FORM_VIEWMODEL_NAME, registerForm,
                    "Could not send activation mail. Please check that the email address you entered is valid.",
                    MessageType.ERROR);
        } catch (UserAlreadyExistsException e) {
            return showMessage(modelMap, REGISTRATION_VIEW_NAME, REGISTRATION_FORM_VIEWMODEL_NAME, registerForm,
                    "A user with this user name already exists. Please choose another username.", MessageType.ERROR);
        } catch (InvalidValueException e) {
            return showMessage(modelMap, REGISTRATION_VIEW_NAME, REGISTRATION_FORM_VIEWMODEL_NAME, registerForm,
                    e.getMessage(), MessageType.ERROR);
        }

        return new ModelAndView(ACTIVATION_NOTICE_VIEW_NAME);
    }

    @RequestMapping(value = ACTIVATION_REQUEST_MAPPING, method = RequestMethod.GET)
    public final ModelAndView activate(@PathVariable int hashCode, ModelMap modelMap,
            RedirectAttributes redirectAttributes) {

        try {
            userService.activateUser(hashCode);
        } catch (UserNotFoundException e) {
            return showMessage(modelMap, LOGIN_VIEW_NAME, LOGIN_FORM_VIEWMODEL_NAME, new LoginFormViewModel(),
                    "The activation link is not valid.", MessageType.ERROR);
        }

        return showMessage(modelMap, LOGIN_VIEW_NAME, LOGIN_FORM_VIEWMODEL_NAME, new LoginFormViewModel(),
                "Your account has been successfully activated. You can now log in.", MessageType.SUCCESS);
    }
}
