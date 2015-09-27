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

import ca.ulaval.glo4003.housematch.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.email.MailSendException;
import ca.ulaval.glo4003.housematch.services.UserService;
import ca.ulaval.glo4003.housematch.spring.web.security.ResourceAccessValidator;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.LoginFormViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.MessageType;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.MessageViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.RegisterFormViewModel;

@Controller
public class RegistrationController extends WebController {

    @Autowired
    private UserService userService;

    protected RegistrationController() {
        // Required for Mockito
    }

    public RegistrationController(final ResourceAccessValidator resourceAccessValidator,
            final UserService userService) {
        this.resourceAccessValidator = resourceAccessValidator;
        this.userService = userService;
    }

    @ModelAttribute("publiclyRegistrableRoles")
    public List<UserRole> getPubliclyRegistrableRoles() {
        return userService.getPubliclyRegistrableUserRoles();
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public final ModelAndView displayRegister(ModelMap modelMap) {
        modelMap.put("registerForm", new RegisterFormViewModel());
        return new ModelAndView("register", modelMap);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public final ModelAndView doRegister(RegisterFormViewModel registerForm, ModelMap modelMap, HttpSession session) {
        try {
            userService.createUser(registerForm.getUsername(), registerForm.getEmail(), registerForm.getPassword(),
                    registerForm.getRole());
        } catch (MailSendException e) {
            return buildErrorMessageModelAndView(modelMap, "register", "registerForm", registerForm,
                    "Could not send activation mail. Please check that the email address you entered is valid.");
        } catch (UserAlreadyExistsException e) {
            return buildErrorMessageModelAndView(modelMap, "register", "registerForm", registerForm,
                    "A user with this user name already exists. Please choose another username.");
        }

        session.setAttribute("username", registerForm.getUsername());
        return new ModelAndView("activationNotice");
    }

    @RequestMapping(value = "/activation/{hashCode}", method = RequestMethod.GET)
    public final ModelAndView activate(@PathVariable int hashCode, ModelMap modelMap,
            RedirectAttributes redirectAttributes) {

        modelMap.put("loginForm", new LoginFormViewModel());

        try {
            userService.activateUser(hashCode);
        } catch (UserNotFoundException e) {
            modelMap.put("message", new MessageViewModel("The activation link is not valid.", MessageType.ERROR));
            return new ModelAndView("login", modelMap);
        }

        modelMap.put("message", new MessageViewModel(
                "Your account has been successfully activated. You can now log in.", MessageType.SUCCESS));
        return new ModelAndView("login", modelMap);
    }
}
