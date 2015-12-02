package ca.ulaval.glo4003.housematch.web.controllers;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.housematch.services.statistics.PropertyStatisticsService;
import ca.ulaval.glo4003.housematch.services.statistics.UserStatisticsService;
import ca.ulaval.glo4003.housematch.web.assemblers.StatisticsViewModelAssembler;
import ca.ulaval.glo4003.housematch.web.viewmodels.StatisticsViewModel;

@Controller
public class StatisticsController {

    static final String STATISTICS_URL = "/statistics";
    static final String STATISTICS_VIEW_NAME = "statistics/statistics";

    @Inject
    private PropertyStatisticsService propertyStatisticsService;
    @Inject
    private UserStatisticsService userStatisticsService;
    @Inject
    private StatisticsViewModelAssembler statisticsViewModelAssembler;

    protected StatisticsController() {
        // Required for Spring init
    }

    public StatisticsController(final PropertyStatisticsService propertyStatisticsService,
            final UserStatisticsService userStatisticsService, final StatisticsViewModelAssembler statisticsViewModelAssembler) {
        this.propertyStatisticsService = propertyStatisticsService;
        this.userStatisticsService = userStatisticsService;
        this.statisticsViewModelAssembler = statisticsViewModelAssembler;
    }

    @RequestMapping(value = STATISTICS_URL, method = RequestMethod.GET)
    private ModelAndView displayStatisticsView(HttpSession httpSession, ModelMap modelMap) {
        StatisticsViewModel viewModel = statisticsViewModelAssembler.assembleFromStatistics(propertyStatisticsService.getStatistics(),
                userStatisticsService.getStatistics());
        return new ModelAndView(STATISTICS_VIEW_NAME, StatisticsViewModel.NAME, viewModel);
    }
}
