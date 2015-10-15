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
import ca.ulaval.glo4003.housematch.spring.web.assemblers.PropertyDetailsFormViewModelAssembler;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyCreationFormViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyDetailsFormViewModel;

@Controller
public class PropertyController extends BaseController {

    public static final String PROPERTY_CREATION_URL = "/seller/sellProperty";
    static final String PROPERTY_CREATION_VIEW_NAME = "seller/propertyCreation";
    static final String PROPERTY_DETAILS_UPDATE_URL = "/seller/updatePropertyDetails/{propertyHashCode}";
    public static final String PROPERTY_DETAILS_UPDATE_BASE_URL = "/seller/updatePropertyDetails/";
    static final String PROPERTY_DETAILS_UPDATE_VIEW_NAME = "seller/propertyDetailsUpdate";
    static final String PROPERTY_DETAILS_UPDATE_CONFIRMATION_VIEW_NAME = "seller/propertyDetailsUpdateConfirmation";

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private UserService userService;

    @Autowired
    private PropertyDetailsFormViewModelAssembler propertyDetailsFormViewModelAssembler;

    protected PropertyController() {
        // Required for Mockito
    }

    public PropertyController(final PropertyService propertyService, final UserService userService,
            final PropertyDetailsFormViewModelAssembler propertyDetailsFormViewModelAssembler) {
        this.propertyService = propertyService;
        this.userService = userService;
        this.propertyDetailsFormViewModelAssembler = propertyDetailsFormViewModelAssembler;
    }

    @RequestMapping(value = PROPERTY_CREATION_URL, method = RequestMethod.GET)
    public final ModelAndView displayPropertyCreationPage(HttpSession httpSession) throws AuthenticationException {
        return new ModelAndView(PROPERTY_CREATION_VIEW_NAME, PropertyCreationFormViewModel.NAME, new PropertyCreationFormViewModel());
    }

    @RequestMapping(value = PROPERTY_CREATION_URL, method = RequestMethod.POST)
    public final ModelAndView createProperty(PropertyCreationFormViewModel propertyCreationForm, HttpSession httpSession)
            throws AuthenticationException {
        try {
            Property property = propertyService.createProperty(propertyCreationForm.getPropertyType(), propertyCreationForm.getAddress(),
                    propertyCreationForm.getSellingPrice(), getUserFromHttpSession(httpSession));
            return new ModelAndView(new RedirectView(PROPERTY_DETAILS_UPDATE_BASE_URL + property.hashCode()));
        } catch (PropertyServiceException e) {
            return showAlertMessage(PROPERTY_CREATION_VIEW_NAME, propertyCreationForm, e.getMessage());
        }
    }

    @RequestMapping(value = PROPERTY_DETAILS_UPDATE_URL, method = RequestMethod.GET)
    public final ModelAndView displayPropertyDetails(@PathVariable int propertyHashCode, ModelMap modelMap, HttpSession httpSession)
            throws AuthenticationException {
        try {
            Property property = userService.getPropertyByHashCode(getUserFromHttpSession(httpSession), propertyHashCode);
            modelMap.put(PropertyDetailsFormViewModel.NAME, propertyDetailsFormViewModelAssembler.assembleFromProperty(property));
            return new ModelAndView(PROPERTY_DETAILS_UPDATE_VIEW_NAME);
        } catch (PropertyNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = PROPERTY_DETAILS_UPDATE_URL, method = RequestMethod.POST)
    public final ModelAndView updatePropertyDetails(@PathVariable int propertyHashCode, HttpSession httpSession,
            PropertyDetailsFormViewModel propertyDetailsForm) throws AuthenticationException {
        try {
            Property property = userService.getPropertyByHashCode(getUserFromHttpSession(httpSession), propertyHashCode);
            propertyService.updateProperty(property, propertyDetailsForm.getDetails());
            return new ModelAndView(PROPERTY_DETAILS_UPDATE_CONFIRMATION_VIEW_NAME);
        } catch (PropertyNotFoundException | PropertyServiceException e) {
            return showAlertMessage(PROPERTY_DETAILS_UPDATE_VIEW_NAME, propertyDetailsForm, e.getMessage());
        }
    }
}
