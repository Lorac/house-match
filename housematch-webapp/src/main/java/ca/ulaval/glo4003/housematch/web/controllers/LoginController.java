package ca.ulaval.glo4003.housematch.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import ca.ulaval.glo4003.housematch.web.viewmodels.LoginFormViewModel;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController extends BaseController {

    public static final String LOGIN_URL = "/login";
    static final String LOGIN_VIEW_NAME = "login/login";

    public LoginController() {
    }

    @RequestMapping(value = LOGIN_URL, method = RequestMethod.GET)
    public final ModelAndView displayLogin(HttpSession httpSession) {
        if (getUserFromHttpSession(httpSession) != null) {
            return new ModelAndView(new RedirectView(HomeController.HOME_URL));
        }
        return new ModelAndView(LOGIN_VIEW_NAME, LoginFormViewModel.NAME, new LoginFormViewModel());
    }
}
