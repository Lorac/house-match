package ca.ulaval.glo4003.housematch.spring.web.controllers;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.services.property.PropertyService;
import ca.ulaval.glo4003.housematch.services.property.PropertyServiceException;
import ca.ulaval.glo4003.housematch.spring.web.security.AuthorizationValidator;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyListingCreationFormViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyListingUpdateFormViewModel;

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

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @RequestMapping(value = RESOURCE_NOT_FOUND_URL)
    public final ModelAndView handleResourceNotFoundException() {
        return new ModelAndView(RESOURCE_NOT_FOUND_VIEW_NAME);
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
            int propertyId = propertyService.createPropertyListing(propertyListingCreationForm.getPropertyType(),
                    propertyListingCreationForm.getAddress(), propertyListingCreationForm.getSellingPrice(),
                    getUserFromHttpSession(httpSession));
            return new ModelAndView(new RedirectView(
                    PROPERTY_LISTING_UPDATE_URL.replace("{propertyId}", Integer.toString(propertyId))));
        } catch (PropertyServiceException e) {
            return showAlertMessage(PROPERTY_LISTING_CREATION_VIEW_NAME, propertyListingCreationForm, e.getMessage());
        }
    }

    @RequestMapping(value = PROPERTY_LISTING_UPDATE_URL, method = RequestMethod.GET)
    public final ModelAndView displayPropertyListingDetails(@PathVariable int propertyId, HttpSession httpSession) {
        ModelMap map = new ModelMap();
        map.put("propertyId", propertyId);
        map.addAttribute(PropertyListingUpdateFormViewModel.VIEWMODEL_NAME, new PropertyListingUpdateFormViewModel());

        try {
            propertyService.findProperty(propertyId);
        } catch (PropertyServiceException e) {
            throw new ResourceNotFoundException();
        }
        return new ModelAndView(PROPERTY_LISTING_UDPATE_VIEW_NAME, PropertyListingUpdateFormViewModel.VIEWMODEL_NAME,
                map.get(PropertyListingUpdateFormViewModel.VIEWMODEL_NAME));
    }

    @RequestMapping(value = PROPERTY_LISTING_UPDATE_URL, method = RequestMethod.POST)
    public final ModelAndView updatePropertyListingDetails(@PathVariable int propertyId, HttpSession httpSession,
            PropertyListingUpdateFormViewModel detailsForm) {
        User user = getUserFromHttpSession(httpSession);
        try {
            propertyService.updateProperty(propertyId, detailsForm.getDetails(), user);
        } catch (PropertyServiceException e) {
            return showAlertMessage(PROPERTY_LISTING_UDPATE_VIEW_NAME, detailsForm, e.getMessage());
        }
        return new ModelAndView(PROPERTY_LISTING_CONFIRMATION_VIEW_NAME);
    }
}
