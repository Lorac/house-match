package ca.ulaval.glo4003.housematch.spring.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ca.ulaval.glo4003.housematch.email.CannotSendEmailException;
import ca.ulaval.glo4003.housematch.services.UserService;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.MessageViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.RegisterFormViewModel;

@Controller
@RequestMapping(value = "/")
public class RegisterController {

    private UserService userService;

    protected RegisterController() {
        // Required for Mockito
    }

    public RegisterController(final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public final ModelAndView displayRegister(ModelMap model) {
        model.put("registerForm", new RegisterFormViewModel());
        return new ModelAndView("register", model);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public final ModelAndView doRegister(RegisterFormViewModel registerForm, ModelMap model, HttpSession session,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            userService.createUser(registerForm.getUsername(), registerForm.getEmail(), registerForm.getPassword(),
                    registerForm.getRole());
        } catch (CannotSendEmailException e) {
            model.put("registerForm", registerForm);
            model.put("message", new MessageViewModel(e.getMessage()));
        }

        session.setAttribute("username", registerForm.getUsername());
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/activation/{hash}", method = RequestMethod.GET)
    public final ModelAndView activate(@PathVariable int hash, ModelMap model) {
        try {
            userService.activateUser(hash);
        } catch (Exception e) {

        }
        return new ModelAndView("activation", model);
    }
}
