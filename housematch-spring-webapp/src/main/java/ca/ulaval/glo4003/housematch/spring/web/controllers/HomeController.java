package ca.ulaval.glo4003.housematch.spring.web.controllers;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.spring.web.security.AnonymousResourceAccessDeniedException;
import ca.ulaval.glo4003.housematch.spring.web.security.ResourceAccessValidator;

@Controller
public class HomeController extends WebController {

    protected HomeController() {
        // Required for Mockito
    }

    public HomeController(final ResourceAccessValidator resourceAccessValidator) {
        this.resourceAccessValidator = resourceAccessValidator;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public final ModelAndView displayHomepage(HttpSession session) {
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            if (user.getRole() == UserRole.ADMINISTRATOR) {
                return new ModelAndView("redirect:/admin");
            } else if (user.getRole() == UserRole.SELLER) {
                return new ModelAndView("redirect:/seller");
            } else if (user.getRole() == UserRole.BUYER) {
                return new ModelAndView("redirect:/buyer");
            }
        }

        return new ModelAndView("home");
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public final ModelAndView adminRequest(HttpSession session, HttpServletResponse response, ModelMap modelMap)
            throws AnonymousResourceAccessDeniedException {
        resourceAccessValidator.validateResourceAccess("adminHome", session);
        return new ModelAndView("adminHome");
    }

    @RequestMapping(value = "/buyer", method = RequestMethod.GET)
    public final ModelAndView buyerRequest(HttpSession session, HttpServletResponse response, ModelMap modelMap) {
        resourceAccessValidator.validateResourceAccess("buyerHome", session);
        return new ModelAndView("buyerHome");
    }

    @RequestMapping(value = "/seller", method = RequestMethod.GET)
    public final ModelAndView sellerRequest(HttpSession session, HttpServletResponse response, ModelMap modelMap) {
        resourceAccessValidator.validateResourceAccess("sellerHome", session);
        return new ModelAndView("sellerHome");
    }
}
