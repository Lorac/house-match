package ca.ulaval.glo4003.housematch.spring.web.controllers;

import ca.ulaval.glo4003.housematch.spring.web.viewmodels.LoginFormViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @RequestMapping(method = RequestMethod.GET)
    public final ModelAndView displayLogin() {
        return new ModelAndView("login", "loginFormViewModel", new LoginFormViewModel());
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public final ModelAndView executeLogin(
            @ModelAttribute("loginFormViewModel") final LoginFormViewModel loginForm,
            RedirectAttributes redirectAttributes) {

        ModelAndView model = new ModelAndView("redirect:/");
        redirectAttributes.addFlashAttribute("username", loginForm.getUsername());


        return model;
    }

}
