package ca.ulaval.glo4003.housematch.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HttpStatusController extends BaseController {

    static final String HTTP_NOT_FOUND_STATUS_URL = "/customErrorPages/404";
    static final String HTTP_NOT_FOUND_STATUS_VIEW_NAME = "customErrorPages/404";
    static final String HTTP_FORBIDDEN_STATUS_URL = "/customErrorPages/403";
    static final String HTTP_FORBIDDEN_STATUS_VIEW_NAME = "customErrorPages/403";

    public HttpStatusController() {
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @RequestMapping(value = HTTP_NOT_FOUND_STATUS_URL)
    public ModelAndView httpNotFoundStatusHandler() {
        return new ModelAndView(HTTP_NOT_FOUND_STATUS_VIEW_NAME);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @RequestMapping(value = HTTP_FORBIDDEN_STATUS_URL)
    public ModelAndView httpForbiddenStatusHandler() {
        return new ModelAndView(HTTP_FORBIDDEN_STATUS_VIEW_NAME);
    }
}
