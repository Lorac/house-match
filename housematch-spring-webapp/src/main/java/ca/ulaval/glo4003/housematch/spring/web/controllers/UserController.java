package ca.ulaval.glo4003.housematch.spring.web.controllers;

import ca.ulaval.glo4003.housematch.domain.user.InvalidPasswordException;
import ca.ulaval.glo4003.housematch.persistence.InMemoryUserRepository;
import ca.ulaval.glo4003.housematch.services.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.services.UserNotFoundException;
import ca.ulaval.glo4003.housematch.services.UserService;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.LoginFormViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.MessageViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.RegisterFormViewModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class UserController {

    private UserService userService;

    public UserController() {
        this.userService = new UserService(new InMemoryUserRepository());
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public final ModelAndView displayLogin() {
        return new ModelAndView("login", "loginForm", new LoginFormViewModel());
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public final ModelAndView doLogin(LoginFormViewModel loginForm, ModelMap model, HttpSession session,
            HttpServletRequest request) {
        try {
            userService.validateUserCredentials(loginForm.getUsername(), loginForm.getPassword());
        } catch (UserNotFoundException | InvalidPasswordException e) {
            model.put("message", new MessageViewModel("Invalid username or password."));
            model.put("loginForm", loginForm);
            return new ModelAndView("login");
        }

        session.setAttribute("username", loginForm.getUsername());
        return new ModelAndView("home", model);
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public final ModelAndView displayRegister() {
        RegisterFormViewModel register = new RegisterFormViewModel(userService.getRegisterableUserRoles());
        return new ModelAndView("register", "registerForm",
                new RegisterFormViewModel(userService.getRegisterableUserRoles()));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public final ModelAndView doRegister(RegisterFormViewModel registerForm, ModelMap model, HttpSession session,
            HttpServletRequest request) {
        try {
            userService.createUser(registerForm.getUsername(), registerForm.getEmail(), registerForm.getPassword(),
                    registerForm.getRole());
        } catch (UserAlreadyExistsException e) {
            model.put("message", new MessageViewModel(e.getMessage()));
            model.put("registerForm", registerForm);
            return new ModelAndView("register", model);
        }

        session.setAttribute("username", registerForm.getUsername());
        return new ModelAndView("home", model);
    }
}
