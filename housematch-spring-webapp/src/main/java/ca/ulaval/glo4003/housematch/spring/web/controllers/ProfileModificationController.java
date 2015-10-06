package ca.ulaval.glo4003.housematch.spring.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.services.user.UserActivationServiceException;
import ca.ulaval.glo4003.housematch.services.user.UserService;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.ProfileModificationFormViewModel;
import ca.ulaval.glo4003.housematch.validators.address.AddressValidationException;

@Controller
public class ProfileModificationController extends MvcController {

    @Autowired
    private UserService userService;

    protected ProfileModificationController() {
        // Required for Mockito
    }

    public ProfileModificationController(final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = MODIFY_USER_URL, method = RequestMethod.GET)
    public final ModelAndView modifyUserProfile(HttpSession session) {
        return new ModelAndView(MODIFY_USER_VIEW_NAME, ProfileModificationFormViewModel.VIEWMODEL_NAME,
                new ProfileModificationFormViewModel());
    }

    @RequestMapping(value = MODIFY_USER_URL, method = RequestMethod.POST)
    public final ModelAndView modifyUserProfile(ProfileModificationFormViewModel modificationForm, HttpSession session)
            throws UserNotFoundException, UserActivationServiceException {
        User user = getUserFromHttpSession(session);
        try {
            userService.updateUserCoordinate(user.getUsername(), modificationForm.getAddress(),
                    modificationForm.getEmail());
            return new ModelAndView(MODIFIED_USER_SAVED_VIEW_NAME);
        } catch (AddressValidationException e) {
            return showAlertMessage(MODIFY_USER_VIEW_NAME, modificationForm, e.getMessage());
        }

    }

}
