package ca.ulaval.glo4003.housematch.spring.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.AuthenticationException;
import java.security.Principal;

@Controller
public class HomeController extends BaseController {

    protected HomeController() {
        // Required for Mockito
    }

    @RequestMapping(value = HOME_URL, method = RequestMethod.GET)
    public final ModelAndView displayHomeView(ModelMap model, Principal principal) throws AuthenticationException {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        return new ModelAndView(HOME_VIEW_NAME, model);
    }

    @RequestMapping(value = ADMIN_HOME_VIEW_NAME, method = RequestMethod.GET)
    private ModelAndView displayAdminHomeView(ModelMap model, Principal principal) throws AuthenticationException {
        model.addAttribute("username", principal.getName());
        return new ModelAndView(ADMIN_HOME_VIEW_NAME, model);
    }

    @RequestMapping(value = BUYER_HOME_VIEW_NAME, method = RequestMethod.GET)
    private ModelAndView displayBuyerHomeView(ModelMap model, Principal principal) throws AuthenticationException {
        model.addAttribute("username", principal.getName());
        return new ModelAndView(BUYER_HOME_VIEW_NAME, model);
    }

    @RequestMapping(value = SELLER_HOME_VIEW_NAME, method = RequestMethod.GET)
    private ModelAndView displaySellerHomeView(ModelMap model, Principal principal) throws AuthenticationException {
        model.addAttribute("username", principal.getName());
        return new ModelAndView(SELLER_HOME_VIEW_NAME, model);
    }
}
