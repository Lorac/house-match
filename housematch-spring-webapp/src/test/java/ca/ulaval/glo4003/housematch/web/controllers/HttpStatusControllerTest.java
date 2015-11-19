package ca.ulaval.glo4003.housematch.web.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.housematch.web.controllers.HttpStatusController;

public class HttpStatusControllerTest {

    private HttpStatusController httpStatusController;

    @Before
    public void init() {
        httpStatusController = new HttpStatusController();
    }

    @Test
    public void httpNotFoundStatusHandlerRendersHttpNotFoundStatusView() {
        ModelAndView modelAndView = httpStatusController.httpNotFoundStatusHandler();
        assertEquals(HttpStatusController.HTTP_NOT_FOUND_STATUS_VIEW_NAME, modelAndView.getViewName());
    }

    @Test
    public void httpForbiddenStatusHandlerRendersHttpForbiddenStatusView() {
        ModelAndView modelAndView = httpStatusController.httpForbiddenStatusHandler();
        assertEquals(HttpStatusController.HTTP_FORBIDDEN_STATUS_VIEW_NAME, modelAndView.getViewName());
    }
}
