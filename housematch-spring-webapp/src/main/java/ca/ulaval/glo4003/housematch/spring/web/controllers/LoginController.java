package ca.ulaval.glo4003.housematch.spring.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import ca.ulaval.glo4003.housematch.spring.web.viewmodels.LoginFormViewModel;

@Controller
public class LoginController extends BaseController {

    @RequestMapping(value = LOGIN_URL, method = RequestMethod.GET)
    public final ModelAndView displayLogin(HttpSession httpSession) {
        if (getUserFromHttpSession(httpSession) != null) {
            return new ModelAndView(new RedirectView(HOME_URL));
        }
        return new ModelAndView(LOGIN_VIEW_NAME, LoginFormViewModel.NAME, new LoginFormViewModel());
    }
}
