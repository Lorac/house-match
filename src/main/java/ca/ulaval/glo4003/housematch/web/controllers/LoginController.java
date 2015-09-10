package ca.ulaval.glo4003.housematch.web.controllers;

import ca.ulaval.glo4003.housematch.web.viewmodels.LoginFormViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView displayLogin() {
        ModelAndView model = new ModelAndView("login");
        return model;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView executeLogin(@ModelAttribute("loginForm") LoginFormViewModel loginFormViewModel) {
        ModelAndView model = new ModelAndView("home");
        model.addObject("loginForm", loginFormViewModel.username);
        return model;
    }

}
