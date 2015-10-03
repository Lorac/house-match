package ca.ulaval.glo4003.housematch.spring.web.controllers;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.spring.web.security.AuthorizationValidator;

@Controller
public class HomeController extends MvcController {

    protected HomeController() {
        // Required for Mockito
    }

    public HomeController(final AuthorizationValidator authorizationValidator) {
        this.authorizationValidator = authorizationValidator;
    }

    @RequestMapping(value = HOME_URL, method = RequestMethod.GET)
    public final ModelAndView displayHomeView(HttpSession httpSession) {
        User user = getUserFromHttpSession(httpSession);

        if (user != null) {
            switch (user.getRole()) {
            case ADMINISTRATOR:
                return new ModelAndView(new RedirectView(ADMIN_HOME_URL));
            case SELLER:
                return new ModelAndView(new RedirectView(SELLER_HOME_URL));
            case BUYER:
                return new ModelAndView(new RedirectView(BUYER_HOME_URL));
            default:
                break;
            }
        }

        return new ModelAndView(HOME_VIEW_NAME);
    }

    @RequestMapping(value = ADMIN_HOME_URL, method = RequestMethod.GET)
    public final ModelAndView displayAdminHomeView(HttpSession httpSession) throws AuthenticationException {
        authorizationValidator.validateResourceAccess(ADMIN_HOME_VIEW_NAME, httpSession, USER_ATTRIBUTE_NAME);
        return new ModelAndView(ADMIN_HOME_VIEW_NAME);
    }

    @RequestMapping(value = BUYER_HOME_URL, method = RequestMethod.GET)
    public final ModelAndView displayBuyerHomeView(HttpSession httpSession) throws AuthenticationException {
        authorizationValidator.validateResourceAccess(BUYER_HOME_VIEW_NAME, httpSession, USER_ATTRIBUTE_NAME);
        return new ModelAndView(BUYER_HOME_VIEW_NAME);
    }

    @RequestMapping(value = SELLER_HOME_URL, method = RequestMethod.GET)
    public final ModelAndView displaySellerHomeView(HttpSession httpSession) throws AuthenticationException {
        authorizationValidator.validateResourceAccess(SELLER_HOME_VIEW_NAME, httpSession, USER_ATTRIBUTE_NAME);
        return new ModelAndView(SELLER_HOME_VIEW_NAME);
    }
}
