package ca.ulaval.glo4003.housematch.spring.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.housematch.services.user.UserService;
import ca.ulaval.glo4003.housematch.services.user.UserServiceException;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.ContactInformationFormViewModel;

@Controller
public class ContactInformationController extends BaseController {

    static final String CONTACT_INFO_UPDATE_URL = "/updateContactInformation";
    static final String CONTACT_INFO_UPDATE_VIEW_NAME = "contactInformationUpdate";
    static final String CONTACT_INFO_UPDATE_CONFIRMATION_VIEW_NAME = "contactInformationUpdateConfirmation";

    @Autowired
    private UserService userService;

    protected ContactInformationController() {
        // Required for Mockito
    }

    public ContactInformationController(final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = CONTACT_INFO_UPDATE_URL, method = RequestMethod.GET)
    public final ModelAndView displayContactInformation(HttpSession httpSession) {
        return new ModelAndView(CONTACT_INFO_UPDATE_VIEW_NAME, ContactInformationFormViewModel.NAME, new ContactInformationFormViewModel());
    }

    @RequestMapping(value = CONTACT_INFO_UPDATE_URL, method = RequestMethod.POST)
    public final ModelAndView submitContactInformation(ContactInformationFormViewModel contactInformationForm, HttpSession httpSession) {
        try {
            userService.updateUserContactInformation(getUserFromHttpSession(httpSession), contactInformationForm.getAddress(),
                    contactInformationForm.getEmail());
            return new ModelAndView(CONTACT_INFO_UPDATE_CONFIRMATION_VIEW_NAME);
        } catch (UserServiceException e) {
            return showAlertMessage(CONTACT_INFO_UPDATE_VIEW_NAME, contactInformationForm, e.getMessage());
        }
    }
}
