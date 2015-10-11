package ca.ulaval.glo4003.housematch.spring.web.controllers;

import ca.ulaval.glo4003.housematch.spring.web.viewmodels.LoginFormViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController extends MvcController {

    @RequestMapping(value = LOGIN_URL, method = RequestMethod.GET)
    public final ModelAndView displayLogin(HttpSession httpSession) {
        if (getUserFromHttpSession(httpSession) != null) {
            return new ModelAndView(new RedirectView(HOME_URL));
        }
        return new ModelAndView(LOGIN_VIEW_NAME, LoginFormViewModel.VIEWMODEL_NAME, new LoginFormViewModel());
    }

    @RequestMapping(value = LOGOUT_URL, method = RequestMethod.GET)
    public final ModelAndView logoutUser(HttpSession httpSession) {
        httpSession.invalidate();
        return new ModelAndView(LOGIN_VIEW_NAME, LoginFormViewModel.VIEWMODEL_NAME, new LoginFormViewModel());
    }
}
