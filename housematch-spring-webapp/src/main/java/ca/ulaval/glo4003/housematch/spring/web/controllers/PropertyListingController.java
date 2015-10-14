package ca.ulaval.glo4003.housematch.spring.web.controllers;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.services.property.PropertyService;
import ca.ulaval.glo4003.housematch.services.property.PropertyServiceException;
import ca.ulaval.glo4003.housematch.services.user.UserService;
import ca.ulaval.glo4003.housematch.spring.web.assemblers.PropertyListingUpdateFormViewModelAssembler;
import ca.ulaval.glo4003.housematch.spring.web.security.AuthorizationValidator;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyListingCreationFormViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyListingUpdateFormViewModel;

@Controller
public class PropertyListingController extends MvcController {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private UserService userService; // Will introduce a service locator after merge

    @Autowired
    private PropertyListingUpdateFormViewModelAssembler propertyListingUpdateFormViewModelAssembler;

    protected PropertyListingController() {
        // Required for Mockito
    }

    public PropertyListingController(final AuthorizationValidator authorizationValidator,
            final PropertyService propertyService, final UserService userService,
            final PropertyListingUpdateFormViewModelAssembler propertyListingUpdateFormViewModelAssembler) {
        this.authorizationValidator = authorizationValidator;
        this.propertyService = propertyService;
        this.userService = userService;
        this.propertyListingUpdateFormViewModelAssembler = propertyListingUpdateFormViewModelAssembler;
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
            int propertyHashCode = propertyService.createPropertyListing(propertyListingCreationForm.getPropertyType(),
                    propertyListingCreationForm.getAddress(), propertyListingCreationForm.getSellingPrice(),
                    getUserFromHttpSession(httpSession));
            return new ModelAndView(new RedirectView(
                    PROPERTY_LISTING_UPDATE_URL.replace("{propertyHashCode}", Integer.toString(propertyHashCode))));
        } catch (PropertyServiceException e) {
            return showAlertMessage(PROPERTY_LISTING_CREATION_VIEW_NAME, propertyListingCreationForm, e.getMessage());
        }
    }

    @RequestMapping(value = PROPERTY_LISTING_UPDATE_URL, method = RequestMethod.GET)
    public final ModelAndView displayPropertyListingDetails(@PathVariable int propertyHashCode, ModelMap modelMap,
            HttpSession httpSession) throws AuthenticationException {
        authorizationValidator.validateResourceAccess(PROPERTY_LISTING_UPDATE_VIEW_NAME, httpSession,
                USER_ATTRIBUTE_NAME);
        try {
            Property property = userService.getPropertyListingByHashCode(getUserFromHttpSession(httpSession),
                    propertyHashCode);
            modelMap.put(PropertyListingUpdateFormViewModel.VIEWMODEL_NAME,
                    propertyListingUpdateFormViewModelAssembler.assembleFromProperty(property));
            return new ModelAndView(PROPERTY_LISTING_UPDATE_VIEW_NAME);
        } catch (PropertyNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = PROPERTY_LISTING_UPDATE_URL, method = RequestMethod.POST)
    public final ModelAndView updatePropertyListingDetails(@PathVariable int propertyHashCode, HttpSession httpSession,
            PropertyListingUpdateFormViewModel formViewModel) throws AuthenticationException {
        authorizationValidator.validateResourceAccess(PROPERTY_LISTING_UPDATE_VIEW_NAME, httpSession,
                USER_ATTRIBUTE_NAME);
        try {
            Property property = userService.getPropertyListingByHashCode(getUserFromHttpSession(httpSession),
                    propertyHashCode);
            propertyService.updateProperty(property, formViewModel.getDetails());
            return new ModelAndView(PROPERTY_LISTING_CONFIRMATION_VIEW_NAME);
        } catch (PropertyNotFoundException | PropertyServiceException e) {
            return showAlertMessage(PROPERTY_LISTING_UPDATE_VIEW_NAME, formViewModel, e.getMessage());
        }
    }
}
