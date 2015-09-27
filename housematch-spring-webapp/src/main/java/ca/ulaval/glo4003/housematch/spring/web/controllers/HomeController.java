package ca.ulaval.glo4003.housematch.spring.web.controllers;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import ca.ulaval.glo4003.housematch.domain.user.User;
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

    @RequestMapping(value = HOME_REQUEST_MAPPING, method = RequestMethod.GET)
    public final ModelAndView displayHomepage(HttpSession session) {
        User user = (User) session.getAttribute(USER_ATTRIBUTE_NAME);

        if (user != null) {
            switch (user.getRole()) {
            case ADMINISTRATOR:
                return new ModelAndView(new RedirectView(ADMIN_HOME_REQUEST_MAPPING));
            case SELLER:
                return new ModelAndView(new RedirectView(SELLER_HOME_REQUEST_MAPPING));
            case BUYER:
                return new ModelAndView(new RedirectView(BUYER_HOME_REQUEST_MAPPING));
            default:
                break;
            }
        }

        return new ModelAndView(HOME_VEW_NAME);
    }

    @RequestMapping(value = ADMIN_HOME_REQUEST_MAPPING, method = RequestMethod.GET)
    public final ModelAndView adminRequest(HttpSession session, HttpServletResponse response, ModelMap modelMap)
            throws AnonymousResourceAccessDeniedException {
        resourceAccessValidator.validateResourceAccess(ADMIN_HOME_VEW_NAME, session, USER_ATTRIBUTE_NAME);
        return new ModelAndView(ADMIN_HOME_VEW_NAME);
    }

    @RequestMapping(value = BUYER_HOME_REQUEST_MAPPING, method = RequestMethod.GET)
    public final ModelAndView buyerRequest(HttpSession session, HttpServletResponse response, ModelMap modelMap) {
        resourceAccessValidator.validateResourceAccess(BUYER_HOME_VEW_NAME, session, USER_ATTRIBUTE_NAME);
        return new ModelAndView(BUYER_HOME_VEW_NAME);
    }

    @RequestMapping(value = SELLER_HOME_REQUEST_MAPPING, method = RequestMethod.GET)
    public final ModelAndView sellerRequest(HttpSession session, HttpServletResponse response, ModelMap modelMap) {
        resourceAccessValidator.validateResourceAccess(SELLER_HOME_VEW_NAME, session, USER_ATTRIBUTE_NAME);
        return new ModelAndView(SELLER_HOME_VEW_NAME);
    }
}
