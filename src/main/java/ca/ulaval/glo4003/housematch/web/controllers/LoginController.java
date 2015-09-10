package ca.ulaval.glo4003.housematch.web.controllers;

import ca.ulaval.glo4003.housematch.web.viewmodels.LoginFormViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @RequestMapping(method = RequestMethod.GET)
    public final ModelAndView displayLogin() {
        return new ModelAndView("login", "loginFormViewModel", new LoginFormViewModel());
    }

    @RequestMapping(method = RequestMethod.POST)
    public final ModelAndView executeLogin(@ModelAttribute("loginForm") final LoginFormViewModel loginFormViewModel) {
        ModelAndView model = new ModelAndView("home");
        model.addObject("loginForm", loginFormViewModel.getUsername());
        return model;
    }

}
