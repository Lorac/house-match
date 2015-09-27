package ca.ulaval.glo4003.housematch.spring.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ca.ulaval.glo4003.housematch.domain.user.InvalidPasswordException;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserNotActivatedException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.services.UserService;
import ca.ulaval.glo4003.housematch.spring.web.security.ResourceAccessValidator;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.LoginFormViewModel;

@Controller
public class LoginController extends WebController {

    @Autowired
    private UserService userService;

    protected LoginController() {
        // Required for Mockito
    }

    public LoginController(final ResourceAccessValidator resourceAccessValidator, final UserService userService) {
        this.resourceAccessValidator = resourceAccessValidator;
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public final ModelAndView displayLogin() {
        return new ModelAndView("login", "loginForm", new LoginFormViewModel());
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public final ModelAndView doLogin(LoginFormViewModel loginForm, ModelMap modelMap, HttpSession session,
            RedirectAttributes redirectAttributes) {

        try {
            userService.validateUserLogin(loginForm.getUsername(), loginForm.getPassword());
        } catch (UserNotFoundException | InvalidPasswordException e) {
            return buildErrorMessageModelAndView(modelMap, "login", "loginForm", loginForm,
                    "Invalid username or password.");
        } catch (UserNotActivatedException e) {
            return buildErrorMessageModelAndView(modelMap, "login", "loginForm", loginForm,
                    "Your account has not been activated yet. Please activate your account using the activation link that was sent to your email address");
        }

        User user = userService.getUserByUsername(loginForm.getUsername());
        session.setAttribute("user", user);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public final ModelAndView logoutUser(HttpSession session) {
        session.invalidate();
        return new ModelAndView("login", "loginForm", new LoginFormViewModel());
    }
}
