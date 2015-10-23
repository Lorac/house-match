package ca.ulaval.glo4003.housematch.spring.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import ca.ulaval.glo4003.housematch.domain.user.User;

@Controller
public class HomeController extends BaseController {

    public static final String HOME_URL = "/";
    static final String HOME_VIEW_NAME = "home";
    static final String ADMIN_HOME_URL = "/admin";
    static final String ADMIN_HOME_VIEW_NAME = "admin/home";
    static final String SELLER_HOME_URL = "/seller";
    static final String SELLER_HOME_VIEW_NAME = "seller/home";
    static final String BUYER_HOME_URL = "/buyer";
    static final String BUYER_HOME_VIEW_NAME = "buyer/home";

    protected HomeController() {
        // Required for Mockito
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
    private ModelAndView displayAdminHomeView(HttpSession httpSession) {
        return new ModelAndView(ADMIN_HOME_VIEW_NAME);
    }

    @RequestMapping(value = BUYER_HOME_URL, method = RequestMethod.GET)
    private ModelAndView displayBuyerHomeView(HttpSession httpSession) {
        return new ModelAndView(BUYER_HOME_VIEW_NAME);
    }

    @RequestMapping(value = SELLER_HOME_URL, method = RequestMethod.GET)
    private ModelAndView displaySellerHomeView(HttpSession httpSession) {
        return new ModelAndView(SELLER_HOME_VIEW_NAME);
    }
}
