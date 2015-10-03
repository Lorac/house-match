package ca.ulaval.glo4003.housematch.spring.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import ca.ulaval.glo4003.housematch.domain.user.InvalidPasswordException;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserNotActivatedException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.services.UserActivationService;
import ca.ulaval.glo4003.housematch.services.UserService;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.AlertMessageType;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.LoginFormViewModel;

@Controller
public class LoginController extends MvcController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserActivationService userActivationService;

    protected LoginController() {
        // Required for Mockito
    }

    public LoginController(final UserService userService, final UserActivationService userActivationService) {
        this.userService = userService;
        this.userActivationService = userActivationService;
    }

    @RequestMapping(value = LOGIN_URL, method = RequestMethod.GET)
    public final ModelAndView displayLogin(HttpSession session) {
        if (getUserFromHttpSession(session) != null) {
            return new ModelAndView(new RedirectView(HOME_URL));
        }
        return new ModelAndView(LOGIN_VIEW_NAME, LOGIN_FORM_VIEWMODEL_NAME, new LoginFormViewModel());
    }

    @RequestMapping(value = LOGIN_URL, method = RequestMethod.POST)
    public final ModelAndView login(LoginFormViewModel loginForm, HttpSession session,
            RedirectAttributes redirectAttributes) {

        try {
            User user = userService.getUserByLoginCredentials(loginForm.getUsername(), loginForm.getPassword());
            session.setAttribute(USER_ATTRIBUTE_NAME, user);
            userActivationService.validateActivation(user);
        } catch (UserNotFoundException | InvalidPasswordException e) {
            return showAlertMessage(LOGIN_VIEW_NAME, LOGIN_FORM_VIEWMODEL_NAME, loginForm,
                    "Invalid username or password.", AlertMessageType.ERROR);
        } catch (UserNotActivatedException e) {
            return new ModelAndView(new RedirectView(EMAIL_RECONFIRM_URL));
        }

        return new ModelAndView(new RedirectView(HOME_URL));
    }

    @RequestMapping(value = LOGOUT_URL, method = RequestMethod.GET)
    public final ModelAndView logoutUser(HttpSession session) {
        session.invalidate();
        return new ModelAndView(LOGIN_VIEW_NAME, LOGIN_FORM_VIEWMODEL_NAME, new LoginFormViewModel());
    }
}
