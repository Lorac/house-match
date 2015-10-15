package ca.ulaval.glo4003.housematch.spring.web.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.AlertMessageType;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.AlertMessageViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.ViewModel;

public class BaseController {

    public static final String ADMIN_HOME_VIEW_NAME = "adminHome";
    public static final String ADMIN_HOME_URL = "/adminHome";
    public static final String SELLER_HOME_VIEW_NAME = "sellerHome";
    public static final String SELLER_HOME_URL = "/sellerHome";
    public static final String BUYER_HOME_VIEW_NAME = "buyerHome";
    public static final String BUYER_HOME_URL = "/buyerHome";
    public static final String PROPERTY_CREATION_VIEW_NAME = "propertyCreation";
    public static final String PROPERTY_DETAILS_UPDATE_VIEW_NAME = "propertyDetailsUpdate";
    public static final String CONTACT_INFO_UPDATE_VIEW_NAME = "contactInformationUpdate";
    protected static final String HOME_VIEW_NAME = "home";
    public static final String HOME_URL = "/";
    protected static final String LOGIN_VIEW_NAME = "login";
    protected static final String LOGIN_URL = "/login";
    protected static final String REGISTRATION_VIEW_NAME = "registration";
    protected static final String REGISTRATION_URL = "/register";
    protected static final String ACTIVATION_BASE_URL = "/activation/";
    protected static final String ACTIVATION_URL = "/activation/{activationCode}";
    protected static final String ACTIVATION_NOTICE_VIEW_NAME = "activationNotice";
    public static final String EMAIL_RECONFIRM_URL = "/emailReconfirm";
    protected static final String EMAIL_RECONFIRM_VIEW_NAME = "emailReconfirm";
    protected static final String PROPERTY_CREATION_URL = "/sell";
    protected static final String PROPERTY_DETAILS_UPDATE_URL = "/updatePropertyDetails/{propertyHashCode}";
    protected static final String PROPERTY_DETAILS_UPDATE_URL_FORMAT = "/updatePropertyDetails/%s";
    protected static final String PROPERTY_DETAILS_UPDATE_CONFIRMATION_VIEW_NAME = "propertyDetailsUpdateConfirmation";
    public static final String USER_ATTRIBUTE_NAME = "user";
    protected static final String HTTP_NOT_FOUND_STATUS_VIEW_NAME = "customErrorPages/404";
    protected static final String HTTP_NOT_FOUND_STATUS_URL = "/customErrorPages/404";
    protected static final String HTTP_FORBIDDEN_STATUS_VIEW_NAME = "customErrorPages/403";
    protected static final String HTTP_FORBIDDEN_STATUS_URL = "/customErrorPages/403";
    protected static final String CONTACT_INFO_UPDATE_URL = "/updateContactInformation";
    protected static final String CONTACT_INFO_UPDATE_CONFIRMATION_URL = "/contactInformationUpdateConfirmation";
    protected static final String CONTACT_INFO_UPDATE_CONFIRMATION_VIEW_NAME = "contactInformationUpdateConfirmation";

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
    public void resourceNotFoundExceptionHandler(HttpServletResponse response) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
    }
}
