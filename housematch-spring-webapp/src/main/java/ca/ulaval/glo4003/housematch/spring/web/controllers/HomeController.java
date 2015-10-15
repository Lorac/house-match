package ca.ulaval.glo4003.housematch.spring.web.controllers;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
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
    public final ModelAndView displayHomeView(HttpSession httpSession) throws AuthenticationException {
        User user = getUserFromHttpSession(httpSession);

        if (user != null) {
            switch (user.getRole()) {
            case ADMINISTRATOR:
                return displayAdminHomeView(httpSession);
            case SELLER:
                return displaySellerHomeView(httpSession);
            case BUYER:
                return displayBuyerHomeView(httpSession);
            default:
                break;
            }
        }

        return new ModelAndView(HOME_VIEW_NAME);
    }

    @RequestMapping(value = ADMIN_HOME_URL, method = RequestMethod.GET)
    private ModelAndView displayAdminHomeView(HttpSession httpSession) throws AuthenticationException {
        return new ModelAndView(ADMIN_HOME_VIEW_NAME);
    }

    @RequestMapping(value = BUYER_HOME_URL, method = RequestMethod.GET)
    private ModelAndView displayBuyerHomeView(HttpSession httpSession) throws AuthenticationException {
        return new ModelAndView(BUYER_HOME_VIEW_NAME);
    }

    @RequestMapping(value = SELLER_HOME_URL, method = RequestMethod.GET)
    private ModelAndView displaySellerHomeView(HttpSession httpSession) throws AuthenticationException {
        return new ModelAndView(SELLER_HOME_VIEW_NAME);
    }
}
