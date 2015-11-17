package ca.ulaval.glo4003.housematch.spring.web.controllers;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.services.property.PropertyService;
import ca.ulaval.glo4003.housematch.services.user.UserService;
import ca.ulaval.glo4003.housematch.spring.web.assemblers.StatisticsViewModelAssembler;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.StatisticsViewModel;

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

    @Inject
    private PropertyService propertyService;
    @Inject
    private UserService userService;
    @Inject
    private StatisticsViewModelAssembler statisticsViewModelAssembler;

    protected HomeController() {
        // Required for Mockito
    }

    public HomeController(final PropertyService propertyService, final UserService userService,
            final StatisticsViewModelAssembler statisticsViewModelAssembler) {
        this.propertyService = propertyService;
        this.userService = userService;
        this.statisticsViewModelAssembler = statisticsViewModelAssembler;
    }

    @RequestMapping(value = HOME_URL, method = RequestMethod.GET)
    public final ModelAndView displayHomeView(HttpSession httpSession, ModelMap modelMap) {
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

        modelMap.put(StatisticsViewModel.NAME,
                statisticsViewModelAssembler.assembleFromStatistics(propertyService.getStatistics(), userService.getStatistics()));
        return new ModelAndView(HOME_VIEW_NAME, modelMap);
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
