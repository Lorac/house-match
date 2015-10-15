package ca.ulaval.glo4003.housematch.spring.web.controllers;

import java.security.Principal;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.housematch.domain.user.User;

@Controller
public class HomeController extends BaseController {

    protected HomeController() {
        // Required for Mockito
    }

    @RequestMapping(value = HOME_URL, method = RequestMethod.GET)
    public final ModelAndView displayHomeView(ModelMap model, HttpSession httpSession, Principal principal)
            throws AuthenticationException {
        if (principal != null) {
            addUserToHttpSession(httpSession, principal);
        }
        return new ModelAndView(HOME_VIEW_NAME, model);
    }

    @RequestMapping(value = ADMIN_HOME_VIEW_NAME, method = RequestMethod.GET)
    private ModelAndView displayAdminHomeView(ModelMap model, HttpSession httpSession, Principal principal)
            throws AuthenticationException {
        addUserToHttpSession(httpSession, principal);
        return new ModelAndView(ADMIN_HOME_VIEW_NAME, model);
    }

    @RequestMapping(value = BUYER_HOME_VIEW_NAME, method = RequestMethod.GET)
    private ModelAndView displayBuyerHomeView(ModelMap model, HttpSession httpSession, Principal principal)
            throws AuthenticationException {
        addUserToHttpSession(httpSession, principal);
        return new ModelAndView(BUYER_HOME_VIEW_NAME, model);
    }

    @RequestMapping(value = SELLER_HOME_VIEW_NAME, method = RequestMethod.GET)
    private ModelAndView displaySellerHomeView(ModelMap model, HttpSession httpSession, Principal principal)
            throws AuthenticationException {
        addUserToHttpSession(httpSession, principal);
        return new ModelAndView(SELLER_HOME_VIEW_NAME, model);
    }

    private void addUserToHttpSession(HttpSession httpSession, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        httpSession.setAttribute(USER_ATTRIBUTE_NAME, user);
    }
}
