package ca.ulaval.glo4003.housematch.spring.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.services.user.UserActivationService;
import ca.ulaval.glo4003.housematch.services.user.UserActivationServiceException;
import ca.ulaval.glo4003.housematch.services.user.UserService;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.ProfileModificationFormViewModel;

@Controller
public class ProfileModificationController extends MvcController {

    @Autowired
    private UserService userService;

    protected ProfileModificationController() {
        // Required for Mockito
    }

    public ProfileModificationController(final UserService userService,
            final UserActivationService userActivationService) {
        this.userService = userService;
    }

    @RequestMapping(value = MODIFY_USER_URL, method = RequestMethod.GET)
    public final ModelAndView modifyUserProfile(Model model, HttpSession session) {
        return new ModelAndView(MODIFY_USER_VIEW_NAME, MODIFY_USER_FORM_VIEWMODEL_NAME,
                new ProfileModificationFormViewModel());
    }

    @RequestMapping(value = MODIFY_USER_URL, method = RequestMethod.POST)
    public final ModelAndView modifyUserProfile(ProfileModificationFormViewModel modificationForm, ModelMap modelMap,
            HttpSession session, RedirectAttributes redirectAttributes)
                    throws UserNotFoundException, UserActivationServiceException {
        User user = getUserFromHttpSession(session);
        userService.updateUserCoordinate(user.getUsername(), modificationForm.getAddress(),
                modificationForm.getPostCode(), modificationForm.getCity(), modificationForm.getRegion(),
                modificationForm.getCountry(), modificationForm.getEmail());
        return new ModelAndView(new RedirectView(MODIFIED_USER_SAVED_URL));
    }

    @RequestMapping(value = MODIFIED_USER_SAVED_URL, method = RequestMethod.GET)
    public final ModelAndView displayProfileSaveView(HttpSession session) {
        return new ModelAndView(MODIFIED_USER_SAVED_VIEW_NAME);
    }

}
