package ca.ulaval.glo4003.housematch.spring.web.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ca.ulaval.glo4003.housematch.domain.user.InvalidPasswordException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotActivatedException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.services.UserService;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.LoginFormViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.MessageViewModel;

@Controller

public class LoginController {

    @Autowired
    private UserService userService;

    protected LoginController() {
        // Required for Mockito
    }

    public LoginController(final UserService userService) {
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
            RedirectAttributes redirectAttributes) {
        try {
            userService.validateUserLogin(loginForm.getUsername(), loginForm.getPassword());
        } catch (UserNotFoundException | InvalidPasswordException | UserNotActivatedException e) {
            model.put("message", new MessageViewModel("Invalid username or password."));
            model.put("loginForm", loginForm);
            return new ModelAndView("login");
        }

        session.setAttribute("username", loginForm.getUsername());
        return new ModelAndView("redirect:/");
    }

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public final ModelAndView logoutUser(SessionStatus status) {
        status.setComplete();
        return new ModelAndView("login", "loginForm", new LoginFormViewModel());
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public final ModelAndView adminRequest(HttpSession session, HttpServletResponse response, ModelMap model) {
        return validateRole(session, response, model, UserRole.ADMINISTRATOR, "adminPage");
    }

    @RequestMapping(value = "/buyer", method = RequestMethod.GET)
    public final ModelAndView buyerRequest(HttpSession session, HttpServletResponse response, ModelMap model) {
        return validateRole(session, response, model, UserRole.BUYER, "buyerPage");
    }

    @RequestMapping(value = "/seller", method = RequestMethod.GET)
    public final ModelAndView sellerRequest(HttpSession session, HttpServletResponse response, ModelMap model) {
        return validateRole(session, response, model, UserRole.SELLER, "sellerPage");
    }

    private ModelAndView handleHttpErrorView(HttpServletResponse response, ModelMap model, HttpStatus status,
            String message) {
        ModelAndView redirectViewModel = new ModelAndView("error");
        model.put("message", new MessageViewModel(message));
        response.setStatus(status.value());
        return redirectViewModel;
    }

    private ModelAndView validateRole(HttpSession session, HttpServletResponse response, ModelMap model, UserRole role,
            String returnPage) {
        try {
            if (session.getAttribute("username") == null) {
                userService.validateRole("", role);
            } else {
                userService.validateRole(session.getAttribute("username").toString(), role);
            }
        } catch (InvalidRoleException e) {
            return handleHttpErrorView(response, model, HttpStatus.FORBIDDEN,
                    "You do not have access to the specified resource.");
        } catch (UserNotFoundException e) {
            return handleHttpErrorView(response, model, HttpStatus.FORBIDDEN,
                    "You must be logged in to see this page.");
        }
        return new ModelAndView(returnPage, model);
    }
}
