package ca.ulaval.glo4003.housematch.spring.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import ca.ulaval.glo4003.housematch.domain.user.InvalidPasswordException;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserNotActivatedException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.services.UserService;
import ca.ulaval.glo4003.housematch.spring.web.security.ResourceAccessValidator;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.LoginFormViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.MessageType;

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

    @RequestMapping(value = LOGIN_REQUEST_MAPPING, method = RequestMethod.GET)
    public final ModelAndView displayLogin() {
        return new ModelAndView(LOGIN_VEW_NAME, LOGIN_FORM_VIEWMODEL_NAME, new LoginFormViewModel());
    }

    @RequestMapping(value = LOGIN_REQUEST_MAPPING, method = RequestMethod.POST)
    public final ModelAndView doLogin(LoginFormViewModel loginForm, ModelMap modelMap, HttpSession session,
            RedirectAttributes redirectAttributes) {

        try {
            userService.validateUserLogin(loginForm.getUsername(), loginForm.getPassword());
        } catch (UserNotFoundException | InvalidPasswordException e) {
            return buildMessageModelAndView(modelMap, LOGIN_VEW_NAME, LOGIN_FORM_VIEWMODEL_NAME, loginForm,
                    "Invalid username or password.", MessageType.ERROR);
        } catch (UserNotActivatedException e) {
            return buildMessageModelAndView(modelMap, LOGIN_VEW_NAME, LOGIN_FORM_VIEWMODEL_NAME, loginForm,
                    "Your account has not been activated yet. Please activate your account using the activation "
                            + "link that was sent to your email address",
                    MessageType.ERROR);
        }

        User user = userService.getUserByUsername(loginForm.getUsername());
        session.setAttribute(USER_ATTRIBUTE_NAME, user);
        return new ModelAndView(new RedirectView(HOME_REQUEST_MAPPING));
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public final ModelAndView logoutUser(HttpSession session) {
        session.invalidate();
        return new ModelAndView(LOGIN_VEW_NAME, LOGIN_FORM_VIEWMODEL_NAME, new LoginFormViewModel());
    }
}
