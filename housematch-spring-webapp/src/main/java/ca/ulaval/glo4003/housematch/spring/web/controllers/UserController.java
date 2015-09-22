package ca.ulaval.glo4003.housematch.spring.web.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ca.ulaval.glo4003.housematch.domain.DomainException;
import ca.ulaval.glo4003.housematch.domain.user.InvalidPasswordException;
import ca.ulaval.glo4003.housematch.domain.user.InvalidRoleException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.services.UserService;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.LoginFormViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.MessageViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.RegisterFormViewModel;

@Controller
@SessionAttributes({ "username" })
@RequestMapping(value = "/")
public class UserController {

    private UserService userService;

    protected UserController() {
        // Required for Mockito
    }

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("publiclyRegistrableRoles")
    public List<UserRole> getPubliclyRegistrableRoles() {
        return userService.getPubliclyRegistrableUserRoles();
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public final ModelAndView displayLogin() {
        return new ModelAndView("login", "loginForm", new LoginFormViewModel());
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public final ModelAndView doLogin(LoginFormViewModel loginForm, ModelMap model, HttpSession session,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            userService.validateUserCredentials(loginForm.getUsername(), loginForm.getPassword());
        } catch (UserNotFoundException | InvalidPasswordException e) {
            model.put("message", new MessageViewModel("Invalid username or password."));
            model.put("loginForm", loginForm);
            return new ModelAndView("login");
        }

        session.setAttribute("username", loginForm.getUsername());
        return new ModelAndView("redirect:/");
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
        } catch (DomainException e) {
            model.put("registerForm", registerForm);
            model.put("message", new MessageViewModel(e.getMessage()));
            return new ModelAndView("register", model);
        }

        session.setAttribute("username", registerForm.getUsername());
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public final ModelAndView logoutUser(SessionStatus status) {
        status.setComplete();
        return new ModelAndView("login", "loginForm", new LoginFormViewModel());
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public final ModelAndView adminRequest(HttpSession session, HttpServletResponse response, ModelMap model) {
        try {
            validateUserRole("Administrator", session);
        } catch (DomainException | NullPointerException e) {
            model.put("message", new MessageViewModel("User does not have access to this role"));
            return handleHttpErrorView(response, model, HttpStatus.FORBIDDEN, "User does not have access to this role");
        }
        return new ModelAndView("adminPage", model);
    }

    @RequestMapping(value = "/buyer", method = RequestMethod.GET)
    public final ModelAndView buyerRequest(HttpSession session, HttpServletResponse response, ModelMap model) {
        try {
            validateUserRole("Buyer", session);
        } catch (DomainException | NullPointerException e) {
            model.put("message", new MessageViewModel("User does not have access to this role"));
            return handleHttpErrorView(response, model, HttpStatus.FORBIDDEN, "User does not have access to this role");
        }
        return new ModelAndView("buyerPage", model);
    }

    @RequestMapping(value = "/seller", method = RequestMethod.GET)
    public final ModelAndView sellerRequest(HttpSession session, HttpServletResponse response, ModelMap model) {
        try {
            validateUserRole("Seller", session);
        } catch (DomainException | NullPointerException e) {
            model.put("message", new MessageViewModel("User does not have access to this role"));
            return handleHttpErrorView(response, model, HttpStatus.FORBIDDEN, "User does not have access to this role");
        }
        return new ModelAndView("sellerPage", model);
    }

    private ModelAndView handleHttpErrorView(HttpServletResponse response, ModelMap model, HttpStatus status,
            String message) {
        ModelAndView redirectViewModel = new ModelAndView("error");
        model.put("message", new MessageViewModel(message));
        response.setStatus(status.value());
        return redirectViewModel;
    }

    private void validateUserRole(String role, HttpSession session) {
        if (session.getAttribute("username") == null) {
            throw new InvalidRoleException("Anonymous user cannot see restricted pages.");
        } else {
            userService.validateRole(session.getAttribute("username").toString(), role);
        }
    }
}
