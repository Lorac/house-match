package ca.ulaval.glo4003.housematch.spring.web.controllers;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.housematch.services.property.PropertyService;
import ca.ulaval.glo4003.housematch.services.property.PropertyServiceException;
import ca.ulaval.glo4003.housematch.spring.web.security.AuthorizationValidator;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyListingCreationFormViewModel;

@Controller
public class PropertyListingController extends MvcController {

    @Autowired
    private PropertyService propertyService;

    protected PropertyListingController() {
        // Required for Mockito
    }

    public PropertyListingController(final AuthorizationValidator authorizationValidator,
            final PropertyService propertyService) {
        this.authorizationValidator = authorizationValidator;
        this.propertyService = propertyService;
    }

    @RequestMapping(value = PROPERTY_LISTING_CREATION_URL, method = RequestMethod.GET)
    public final ModelAndView displayPropertyListingCreationPage(HttpSession httpSession)
            throws AuthenticationException {
        authorizationValidator.validateResourceAccess(PROPERTY_LISTING_CREATION_VIEW_NAME, httpSession,
                USER_ATTRIBUTE_NAME);
        return new ModelAndView(PROPERTY_LISTING_CREATION_VIEW_NAME,
                PropertyListingCreationFormViewModel.VIEWMODEL_NAME, new PropertyListingCreationFormViewModel());
    }

    @RequestMapping(value = PROPERTY_LISTING_CREATION_URL, method = RequestMethod.POST)
    public final ModelAndView createPropertyListing(PropertyListingCreationFormViewModel propertyListingCreationForm,
            HttpSession httpSession) throws AuthenticationException {
        authorizationValidator.validateResourceAccess(PROPERTY_LISTING_CREATION_VIEW_NAME, httpSession,
                USER_ATTRIBUTE_NAME);
        try {
            propertyService.createPropertyListing(propertyListingCreationForm.getPropertyType(),
                    propertyListingCreationForm.getAddress(), propertyListingCreationForm.getSellingPrice(),
                    getUserFromHttpSession(httpSession));
            return new ModelAndView(PROPERTY_LISTING_UDPATE_VIEW_NAME,
                    PropertyListingCreationFormViewModel.VIEWMODEL_NAME, propertyListingCreationForm);
        } catch (PropertyServiceException e) {
            return showAlertMessage(PROPERTY_LISTING_CREATION_VIEW_NAME, propertyListingCreationForm, e.getMessage());
        }
    }

    @RequestMapping(value = PROPERTY_LISTING_UPDATE_URL, method = RequestMethod.POST)
    public final ModelAndView editPropertyListingPage(HttpSession httpSession, PropertyListingCreationFormViewModel propertyListingCreationForm) {
        try {
            throw new PropertyServiceException();
        } catch (PropertyServiceException e) {
            return showAlertMessage(PROPERTY_LISTING_CREATION_VIEW_NAME, propertyListingCreationForm, "Not implemented");
        }
    }
}
