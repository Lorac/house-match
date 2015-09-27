package ca.ulaval.glo4003.housematch.spring.web.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import ca.ulaval.glo4003.housematch.spring.web.security.AnonymousResourceAccessDeniedException;
import ca.ulaval.glo4003.housematch.spring.web.security.ResourceAccessDeniedException;
import ca.ulaval.glo4003.housematch.spring.web.security.ResourceAccessValidator;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.MessageType;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.MessageViewModel;

public class WebController {

    protected static final String HOME_VEW_NAME = "home";
    protected static final String HOME_REQUEST_MAPPING = "/";
    protected static final String ADMIN_HOME_VEW_NAME = "adminHome";
    protected static final String ADMIN_HOME_REQUEST_MAPPING = "/admin";
    protected static final String SELLER_HOME_VEW_NAME = "sellerHome";
    protected static final String SELLER_HOME_REQUEST_MAPPING = "/seller";
    protected static final String BUYER_HOME_VEW_NAME = "buyerHome";
    protected static final String BUYER_HOME_REQUEST_MAPPING = "/buyer";
    protected static final String LOGIN_VEW_NAME = "login";
    protected static final String LOGIN_FORM_VIEWMODEL_NAME = "loginForm";
    protected static final String LOGIN_REQUEST_MAPPING = "/login";
    protected static final String REGISTRATION_VEW_NAME = "register";
    protected static final String REGISTRATION_FORM_VIEWMODEL_NAME = "registrationForm";
    protected static final String REGISTRATION_REQUEST_MAPPING = "/register";
    protected static final String MESSAGE_VEW_NAME = "message";
    protected static final String ACTIVATION_REQUEST_MAPPING = "/activation/{hashCode}";
    protected static final String ACTIVATION_NOTICE_VEW_NAME = "activationNotice";

    protected static final String USER_ATTRIBUTE_NAME = "user";

    @Autowired
    protected ResourceAccessValidator resourceAccessValidator;

    protected ModelAndView buildMessageModelAndView(ModelMap modelMap, String viewName, String viewModelName,
            Object viewModel, String message, MessageType messageType) {
        modelMap.put(MESSAGE_VEW_NAME, new MessageViewModel(message, messageType));
        modelMap.put(viewModelName, viewModel);
        return new ModelAndView(viewName, modelMap);
    }

    @ExceptionHandler(AnonymousResourceAccessDeniedException.class)
    public ModelAndView anonymousResourceAccessDeniedExceptionHandler(HttpServletResponse response) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return new ModelAndView(new RedirectView(LOGIN_REQUEST_MAPPING));
    }

    @ExceptionHandler(ResourceAccessDeniedException.class)
    public void resourceAccessDeniedExceptionHandler(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.FORBIDDEN.value(), "You do not have access to the specified resource.");
    }
}
