package ca.ulaval.glo4003.housematch.web.controllers;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.services.statistics.PropertyStatisticsService;
import ca.ulaval.glo4003.housematch.services.statistics.UserStatisticsService;
import ca.ulaval.glo4003.housematch.web.assemblers.StatisticsViewModelAssembler;
import ca.ulaval.glo4003.housematch.web.viewmodels.StatisticsViewModel;

@Controller
public class HomeController extends BaseController {

    public static final String HOME_URL = "/";
    static final String HOME_VIEW_NAME = "home/home";
    static final String ADMIN_HOME_URL = "/admin";
    static final String ADMIN_HOME_VIEW_NAME = "admin/home";
    static final String SELLER_HOME_URL = "/seller";
    static final String SELLER_HOME_VIEW_NAME = "seller/home";
    static final String BUYER_HOME_URL = "/buyer";
    static final String BUYER_HOME_VIEW_NAME = "buyer/home";
    static final String STATISTICS_URL = "/statistics";
    static final String STATISTICS_VIEW_NAME = "home/statistics";

    @Inject
    private PropertyStatisticsService propertyStatisticsService;
    @Inject
    private UserStatisticsService userStatisticsService;
    @Inject
    private StatisticsViewModelAssembler statisticsViewModelAssembler;

    protected HomeController() {
        // Required for Mockito
    }

    public HomeController(final PropertyStatisticsService propertyStatisticsService,
            final UserStatisticsService userStatisticsService, final StatisticsViewModelAssembler statisticsViewModelAssembler) {
        this.propertyStatisticsService = propertyStatisticsService;
        this.userStatisticsService = userStatisticsService;
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

        return new ModelAndView(HOME_VIEW_NAME, modelMap);
    }

    @RequestMapping(value = ADMIN_HOME_URL, method = RequestMethod.GET)
    private ModelAndView displayAdminHomeView(HttpSession httpSession, ModelMap modelMap) {
        return new ModelAndView(ADMIN_HOME_VIEW_NAME, modelMap);
    }

    @RequestMapping(value = BUYER_HOME_URL, method = RequestMethod.GET)
    private ModelAndView displayBuyerHomeView(HttpSession httpSession) {
        return new ModelAndView(BUYER_HOME_VIEW_NAME);
    }

    @RequestMapping(value = SELLER_HOME_URL, method = RequestMethod.GET)
    private ModelAndView displaySellerHomeView(HttpSession httpSession) {
        return new ModelAndView(SELLER_HOME_VIEW_NAME);
    }

    @RequestMapping(value = STATISTICS_URL, method = RequestMethod.GET)
    private ModelAndView displayStatisticsView(HttpSession httpSession, ModelMap modelMap) {
        StatisticsViewModel viewModel = statisticsViewModelAssembler.assembleFromStatistics(propertyStatisticsService.getStatistics(),
                userStatisticsService.getStatistics());
        return new ModelAndView(STATISTICS_VIEW_NAME, StatisticsViewModel.NAME, viewModel);
    }
}
