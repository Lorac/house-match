package ca.ulaval.glo4003.housematch.spring.web.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

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
import ca.ulaval.glo4003.housematch.spring.web.assemblers.PropertySearchResultsViewModelAssembler;
import ca.ulaval.glo4003.housematch.spring.web.assemblers.PropertyViewModelAssembler;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyDetailsFormViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertySearchFormViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertySearchResultsViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyViewModel;

@Controller
public class PropertyController extends BaseController {

    public static final String PROPERTY_CREATION_URL = "/seller/sellProperty";
    static final String PROPERTY_CREATION_VIEW_NAME = "seller/propertyCreation";
    static final String PROPERTY_DETAILS_UPDATE_URL = "/seller/updatePropertyDetails/{propertyHashCode}";
    public static final String PROPERTY_DETAILS_UPDATE_BASE_URL = "/seller/updatePropertyDetails/";
    static final String PROPERTY_DETAILS_UPDATE_VIEW_NAME = "seller/propertyDetailsUpdate";
    static final String PROPERTY_DETAILS_UPDATE_CONFIRMATION_VIEW_NAME = "seller/propertyDetailsUpdateConfirmation";
    public static final String PROPERTIES_FOR_SALE_LIST_URL = "/seller/propertyList";
    static final String PROPERTIES_FOR_SALE_LIST_VIEW_NAME = "seller/propertyList";
    public static final String PROPERTY_SEARCH_URL = "/buyer/searchProperties";
    public static final String PROPERTY_SEARCH_SORT_BY_DATE_ASC_URL = "/buyer/executePropertySearch/sortByAscendingDate";
    public static final String PROPERTY_SEARCH_SORT_BY_DATE_DESC_URL = "/buyer/executePropertySearch/sortByDescendingDate";
    public static final String PROPERTY_SEARCH_SORT_BY_PRICE_ASC_URL = "/buyer/executePropertySearch/sortByAscendingPrice";
    public static final String PROPERTY_SEARCH_SORT_BY_PRICE_DESC_URL = "/buyer/executePropertySearch/sortByDescendingPrice";
    public static final String PROPERTY_SEARCH_EXECUTE_URL = "/buyer/executePropertySearch";
    static final String PROPERTY_SEARCH_VIEW_NAME = "buyer/propertySearch";
    public static final String PROPERTY_VIEW_URL = "/buyer/propertyDetails/{propertyHashCode}";
    public static final String PROPERTY_VIEW_BASE_URL = "/buyer/propertyDetails/";
    static final String PROPERTY_VIEW_NAME = "buyer/propertyDetails";

    @Inject
    private PropertyService propertyService;
    @Inject
    private UserService userService;
    @Inject
    private PropertyViewModelAssembler propertyViewModelAssembler;
    @Inject
    private PropertyDetailsFormViewModelAssembler propertyDetailsFormViewModelAssembler;
    @Inject
    private PropertySearchResultsViewModelAssembler propertySearchResultsViewModelAssembler;

    protected PropertyController() {
        // Required for Mockito
    }

    public PropertyController(final PropertyService propertyService, final UserService userService,
            final PropertyViewModelAssembler propertyViewModelAssembler,
            final PropertyDetailsFormViewModelAssembler propertyDetailsFormViewModelAssembler,
            final PropertySearchResultsViewModelAssembler propertySearchResultsViewModelAssembler) {
        this.propertyService = propertyService;
        this.userService = userService;
        this.propertyViewModelAssembler = propertyViewModelAssembler;
        this.propertyDetailsFormViewModelAssembler = propertyDetailsFormViewModelAssembler;
        this.propertySearchResultsViewModelAssembler = propertySearchResultsViewModelAssembler;
    }

    @RequestMapping(value = PROPERTY_CREATION_URL, method = RequestMethod.GET)
    public final ModelAndView displayPropertyCreationView(HttpSession httpSession) {
        return new ModelAndView(PROPERTY_CREATION_VIEW_NAME, PropertyViewModel.NAME, new PropertyViewModel());
    }

    @RequestMapping(value = PROPERTY_CREATION_URL, method = RequestMethod.POST)
    public final ModelAndView createProperty(PropertyViewModel propertyViewModel, HttpSession httpSession) {
        try {
            Property property = propertyService.createProperty(propertyViewModel.getPropertyType(), propertyViewModel.getAddress(),
                    propertyViewModel.getSellingPrice(), getUserFromHttpSession(httpSession));
            return new ModelAndView(new RedirectView(PROPERTY_DETAILS_UPDATE_BASE_URL + property.hashCode()));
        } catch (PropertyServiceException e) {
            return showAlertMessage(PROPERTY_CREATION_VIEW_NAME, propertyViewModel, e.getMessage());
        }
    }

    @RequestMapping(value = PROPERTY_DETAILS_UPDATE_URL, method = RequestMethod.GET)
    public final ModelAndView displayPropertyDetailsUpdateVIew(@PathVariable int propertyHashCode, ModelMap modelMap,
            HttpSession httpSession) {
        try {
            Property property = userService.getPropertyForSaleByHashCode(getUserFromHttpSession(httpSession), propertyHashCode);
            modelMap.put(PropertyDetailsFormViewModel.NAME, propertyDetailsFormViewModelAssembler.assembleFromProperty(property));
            return new ModelAndView(PROPERTY_DETAILS_UPDATE_VIEW_NAME);
        } catch (PropertyNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = PROPERTY_DETAILS_UPDATE_URL, method = RequestMethod.POST)
    public final ModelAndView updatePropertyDetails(@PathVariable int propertyHashCode, HttpSession httpSession,
            PropertyDetailsFormViewModel propertyDetailsFormViewModel) {
        try {
            Property property = userService.getPropertyForSaleByHashCode(getUserFromHttpSession(httpSession), propertyHashCode);
            propertyService.updatePropertyDetails(property, propertyDetailsFormViewModel.getDetails());
            return new ModelAndView(PROPERTY_DETAILS_UPDATE_CONFIRMATION_VIEW_NAME);
        } catch (PropertyNotFoundException | PropertyServiceException e) {
            return showAlertMessage(PROPERTY_DETAILS_UPDATE_VIEW_NAME, propertyDetailsFormViewModel, e.getMessage());
        }
    }

    @RequestMapping(value = PROPERTIES_FOR_SALE_LIST_URL, method = RequestMethod.GET)
    public final ModelAndView listPropertiesForSale() {
        return new ModelAndView(PROPERTIES_FOR_SALE_LIST_VIEW_NAME);
    }

    @RequestMapping(value = PROPERTY_SEARCH_URL, method = RequestMethod.GET)
    public final ModelAndView displayPropertySearchView() {
        return new ModelAndView(PROPERTY_SEARCH_VIEW_NAME, PropertySearchFormViewModel.NAME, new PropertySearchFormViewModel());
    }

    @RequestMapping(value = PROPERTY_SEARCH_EXECUTE_URL, method = RequestMethod.GET)
    public final ModelAndView executePropertySearch(ModelMap modelMap, PropertySearchFormViewModel propertySearchFormViewModel) {
        List<Property> properties = propertyService.getProperties();
        modelMap.put(PropertySearchFormViewModel.NAME, propertySearchFormViewModel);
        modelMap.put(PropertySearchResultsViewModel.NAME, propertySearchResultsViewModelAssembler.assembleFromPropertyList(properties));
        return new ModelAndView(PROPERTY_SEARCH_VIEW_NAME, modelMap);
    }

    @RequestMapping(value = PROPERTY_SEARCH_SORT_BY_DATE_ASC_URL, method = RequestMethod.GET)
    public final ModelAndView sortByAscendingDate(ModelMap modelMap, PropertySearchFormViewModel propertySearchFormViewModel) {
        List<Property> properties = propertyService.getPropertiesInChronologicalOrder();
        modelMap.put(PropertySearchFormViewModel.NAME, propertySearchFormViewModel);
        modelMap.put(PropertySearchResultsViewModel.NAME, propertySearchResultsViewModelAssembler.assembleFromPropertyList(properties));
        return new ModelAndView(PROPERTY_SEARCH_VIEW_NAME, modelMap);
    }

    @RequestMapping(value = PROPERTY_SEARCH_SORT_BY_DATE_DESC_URL, method = RequestMethod.GET)
    public final ModelAndView sortByDescendingDate(ModelMap modelMap, PropertySearchFormViewModel propertySearchFormViewModel) {
        List<Property> properties = propertyService.getPropertiesInReverseChronologicalOrder();
        modelMap.put(PropertySearchFormViewModel.NAME, propertySearchFormViewModel);
        modelMap.put(PropertySearchResultsViewModel.NAME, propertySearchResultsViewModelAssembler.assembleFromPropertyList(properties));
        return new ModelAndView(PROPERTY_SEARCH_VIEW_NAME, modelMap);
    }

    @RequestMapping(value = PROPERTY_SEARCH_SORT_BY_PRICE_ASC_URL, method = RequestMethod.GET)
    public final ModelAndView sortByAscendingPrice(ModelMap modelMap, PropertySearchFormViewModel propertySearchFormViewModel) {
        List<Property> properties = propertyService.getPropertiesInAscendingOrderByPrice();
        modelMap.put(PropertySearchFormViewModel.NAME, propertySearchFormViewModel);
        modelMap.put(PropertySearchResultsViewModel.NAME, propertySearchResultsViewModelAssembler.assembleFromPropertyList(properties));
        return new ModelAndView(PROPERTY_SEARCH_VIEW_NAME, modelMap);
    }

    @RequestMapping(value = PROPERTY_SEARCH_SORT_BY_PRICE_DESC_URL, method = RequestMethod.GET)
    public final ModelAndView sortByDescendingPrice(ModelMap modelMap, PropertySearchFormViewModel propertySearchFormViewModel) {
        List<Property> properties = propertyService.getPropertiesInDescendingOrderByPrice();
        modelMap.put(PropertySearchFormViewModel.NAME, propertySearchFormViewModel);
        modelMap.put(PropertySearchResultsViewModel.NAME, propertySearchResultsViewModelAssembler.assembleFromPropertyList(properties));
        return new ModelAndView(PROPERTY_SEARCH_VIEW_NAME, modelMap);
    }

    @RequestMapping(value = PROPERTY_VIEW_URL, method = RequestMethod.GET)
    public final ModelAndView displayPropertyView(@PathVariable int propertyHashCode, ModelMap modelMap) {
        try {
            Property property = propertyService.getPropertyByHashCode(propertyHashCode);
            modelMap.put(PropertyViewModel.NAME, propertyViewModelAssembler.assembleFromProperty(property));
            return new ModelAndView(PROPERTY_VIEW_NAME);
        } catch (PropertyNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }
}
