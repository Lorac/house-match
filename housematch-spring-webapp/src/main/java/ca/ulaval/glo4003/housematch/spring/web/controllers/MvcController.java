package ca.ulaval.glo4003.housematch.spring.web.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import ca.ulaval.glo4003.housematch.spring.web.security.AccessDeniedException;
import ca.ulaval.glo4003.housematch.spring.web.security.AnonymousAccessDeniedException;
import ca.ulaval.glo4003.housematch.spring.web.security.AuthorizationValidator;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.AlertMessageType;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.AlertMessageViewModel;

public class MvcController {

    protected static final String HOME_VIEW_NAME = "home";
    protected static final String HOME_URL = "/";
    public static final String ADMIN_HOME_VIEW_NAME = "adminHome";
    protected static final String ADMIN_HOME_URL = "/admin";
    public static final String SELLER_HOME_VIEW_NAME = "sellerHome";
    protected static final String SELLER_HOME_URL = "/seller";
    public static final String BUYER_HOME_VIEW_NAME = "buyerHome";
    protected static final String BUYER_HOME_URL = "/buyer";
    protected static final String LOGIN_VIEW_NAME = "login";
    protected static final String LOGIN_FORM_VIEWMODEL_NAME = "loginForm";
    protected static final String LOGIN_URL = "/login";
    protected static final String LOGOUT_URL = "/logout";
    protected static final String REGISTRATION_VIEW_NAME = "register";
    protected static final String REGISTRATION_FORM_VIEWMODEL_NAME = "registrationForm";
    protected static final String REGISTRATION_URL = "/register";
    protected static final String ALERT_MESSAGE_VIEW_MODEL_NAME = "alertMessage";
    protected static final String ACTIVATION_BASE_URL = "/activation/";
    protected static final String ACTIVATION_URL = "/activation/{hashCode}";
    protected static final String ACTIVATION_NOTICE_VIEW_NAME = "activationNotice";
    protected static final String EMAIL_RECONFIRM_URL = "/emailReconfirm";
    protected static final String EMAIL_RECONFIRM_VIEW_NAME = "emailReconfirm";
    protected static final String EMAIL_RECONFIRM_FORM_VIEWMODEL_NAME = "emailReconfirmForm";
    protected static final String USER_ATTRIBUTE_NAME = "user";

    @Autowired
    protected AuthorizationValidator authorizationValidator;

    protected ModelAndView showAlertMessage(String viewName, String viewModelName, Object viewModel, String message,
            AlertMessageType messageType) {

        ModelMap modelMap = new ModelMap();
        modelMap.put(ALERT_MESSAGE_VIEW_MODEL_NAME, new AlertMessageViewModel(message, messageType));
        modelMap.put(viewModelName, viewModel);
        return new ModelAndView(viewName, modelMap);
    }

    @ExceptionHandler(AnonymousAccessDeniedException.class)
    public ModelAndView anonymousAccessDeniedExceptionHandler(HttpServletResponse response, HttpSession session) {
        session.invalidate();
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return new ModelAndView(new RedirectView(LOGIN_URL));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void accessDeniedExceptionHandler(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.FORBIDDEN.value(), "You do not have access to the specified resource.");
    }
}
