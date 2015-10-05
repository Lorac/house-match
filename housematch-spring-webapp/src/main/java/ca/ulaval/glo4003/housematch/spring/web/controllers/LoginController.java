package ca.ulaval.glo4003.housematch.spring.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.services.user.UserActivationService;
import ca.ulaval.glo4003.housematch.services.user.UserActivationServiceException;
import ca.ulaval.glo4003.housematch.services.user.UserService;
import ca.ulaval.glo4003.housematch.services.user.UserServiceException;
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
    public final ModelAndView displayLogin(HttpSession httpSession) {
        if (getUserFromHttpSession(httpSession) != null) {
            return new ModelAndView(new RedirectView(HOME_URL));
        }
        return new ModelAndView(LOGIN_VIEW_NAME, LoginFormViewModel.VIEWMODEL_NAME, new LoginFormViewModel());
    }

    @RequestMapping(value = LOGIN_URL, method = RequestMethod.POST)
    public final ModelAndView login(LoginFormViewModel loginForm, HttpSession httpSession,
            RedirectAttributes redirectAttributes) {

        try {
            User user = userService.getUserByLoginCredentials(loginForm.getUsername(), loginForm.getPassword());
            httpSession.setAttribute(USER_ATTRIBUTE_NAME, user);
            userActivationService.validateActivation(user);
            return new ModelAndView(new RedirectView(HOME_URL));
        } catch (UserServiceException e) {
            return showAlertMessage(LOGIN_VIEW_NAME, loginForm, e.getMessage());
        } catch (UserActivationServiceException e) {
            return new ModelAndView(new RedirectView(EMAIL_RECONFIRM_URL));
        }
    }

    @RequestMapping(value = LOGOUT_URL, method = RequestMethod.GET)
    public final ModelAndView logoutUser(HttpSession httpSession) {
        httpSession.invalidate();
        return new ModelAndView(LOGIN_VIEW_NAME, LoginFormViewModel.VIEWMODEL_NAME, new LoginFormViewModel());
    }
}
