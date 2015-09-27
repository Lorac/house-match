package ca.ulaval.glo4003.housematch.spring.web.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.housematch.spring.web.security.AnonymousResourceAccessDeniedException;
import ca.ulaval.glo4003.housematch.spring.web.security.ResourceAccessDeniedException;
import ca.ulaval.glo4003.housematch.spring.web.security.ResourceAccessValidator;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.MessageType;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.MessageViewModel;

public class WebController {

    @Autowired
    protected ResourceAccessValidator resourceAccessValidator;

    protected ModelAndView buildErrorMessageModelAndView(ModelMap modelMap, String viewName, String viewModelName,
            Object viewModel, String message) {
        modelMap.put("message", new MessageViewModel(message, MessageType.ERROR));
        modelMap.put(viewModelName, viewModel);
        return new ModelAndView(viewName, modelMap);
    }

    @ExceptionHandler(AnonymousResourceAccessDeniedException.class)
    public ModelAndView anonymousResourceAccessDeniedExceptionHandler(HttpServletResponse response) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return new ModelAndView("redirect:/login");
    }

    @ExceptionHandler(ResourceAccessDeniedException.class)
    public void resourceAccessDeniedExceptionHandler(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.FORBIDDEN.value(), "You do not have access to the specified resource.");
    }
}
