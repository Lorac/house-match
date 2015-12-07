package ca.ulaval.glo4003.housematch.web.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.web.viewmodels.AlertMessageType;
import ca.ulaval.glo4003.housematch.web.viewmodels.AlertMessageViewModel;
import ca.ulaval.glo4003.housematch.web.viewmodels.ViewModel;

public class BaseController {

    public static final String USER_ATTRIBUTE_NAME = "user";

    public BaseController() {

    }

    protected User getUserFromHttpSession(HttpSession httpSession) {
        return (User) httpSession.getAttribute(USER_ATTRIBUTE_NAME);
    }

    protected ModelAndView showAlertMessage(String viewName, ViewModel viewModel, String message) {
        return showAlertMessage(viewName, viewModel, message, AlertMessageType.ERROR);
    }

    protected ModelAndView showAlertMessage(String viewName, ViewModel viewModel, String message, AlertMessageType messageType) {
        ModelMap modelMap = new ModelMap();
        modelMap.put(AlertMessageViewModel.NAME, new AlertMessageViewModel(message, messageType));
        modelMap.put(viewModel.getName(), viewModel);
        return new ModelAndView(viewName, modelMap);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void accessDeniedExceptionHandler(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.FORBIDDEN.value(), "You do not have access to the specified resource.");
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public void resourceNotFoundExceptionHandler(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), "The specified resource could not be found.");
    }

    @ExceptionHandler(ResourceConflictException.class)
    public void resourceConflictExceptionHandler(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value(), "The specified resource already exists.");
    }
}
