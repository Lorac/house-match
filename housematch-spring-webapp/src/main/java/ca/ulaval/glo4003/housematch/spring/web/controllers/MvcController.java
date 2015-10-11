package ca.ulaval.glo4003.housematch.spring.web.controllers;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.AlertMessageType;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.AlertMessageViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.ViewModel;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

public class MvcController {

    public static final String ADMIN_HOME_VIEW_NAME = "adminHome";
    public static final String SELLER_HOME_VIEW_NAME = "sellerHome";
    public static final String BUYER_HOME_VIEW_NAME = "buyerHome";
    public static final String PROPERTY_LISTING_CREATION_VIEW_NAME = "listingCreation";
    protected static final String HOME_VIEW_NAME = "home";
    protected static final String HOME_URL = "/";
    protected static final String LOGIN_VIEW_NAME = "login";
    protected static final String LOGIN_URL = "/login";
    protected static final String LOGOUT_URL = "/logout";
    protected static final String REGISTRATION_VIEW_NAME = "registration";
    protected static final String REGISTRATION_URL = "/register";
    protected static final String ACTIVATION_BASE_URL = "/activation/";
    protected static final String ACTIVATION_URL = "/activation/{activationCode}";
    protected static final String ACTIVATION_NOTICE_VIEW_NAME = "activationNotice";
    protected static final String EMAIL_RECONFIRM_URL = "/emailReconfirm";
    protected static final String EMAIL_RECONFIRM_VIEW_NAME = "emailReconfirm";
    protected static final String PROPERTY_LISTING_CREATION_URL = "/sell";
    protected static final String PROPERTY_LISTING_UPDATE_URL = "/updateListing/{propertyId}";
    protected static final String PROPERTY_LISTING_UDPATE_VIEW_NAME = "updateListing";
    protected static final String PROPERTY_LISTING_CONFIRMATION_VIEW_NAME = "listingSave";
    protected static final String USER_ATTRIBUTE_NAME = "user";
    protected static final String RESOURCE_NOT_FOUND_VIEW_NAME = "404";
    protected static final String RESOURCE_NOT_FOUND_URL = "/404";
    protected static final String RESOURCE_FORBIDDEN_VIEW_NAME = "403";
    protected static final String RESOURCE_FORBIDDEN_URL = "/403";

    protected User getUserFromHttpSession(HttpSession httpSession) {
        return (User) httpSession.getAttribute(USER_ATTRIBUTE_NAME);
    }

    protected ModelAndView showAlertMessage(String viewName, ViewModel viewModel, String message) {
        return showAlertMessage(viewName, viewModel, message, AlertMessageType.ERROR);
    }

    protected ModelAndView showAlertMessage(String viewName, ViewModel viewModel, String message,
                                            AlertMessageType messageType) {
        ModelMap modelMap = new ModelMap();
        modelMap.put(AlertMessageViewModel.VIEWMODEL_NAME, new AlertMessageViewModel(message, messageType));
        modelMap.put(viewModel.getViewModelName(), viewModel);
        return new ModelAndView(viewName, modelMap);
    }
}
