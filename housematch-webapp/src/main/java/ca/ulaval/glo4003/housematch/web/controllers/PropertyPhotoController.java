package ca.ulaval.glo4003.housematch.web.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.services.propertyphoto.PropertyPhotoService;
import ca.ulaval.glo4003.housematch.services.user.UserService;
import ca.ulaval.glo4003.housematch.web.assemblers.PropertyPhotoListViewModelAssembler;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyPhotoListViewModel;

@Controller
public class PropertyPhotoController extends BaseController {

    private static final String PHOTO_DELETE_URL = "/seller/deletePropertyPhoto/{propertyHashCode}/{photoHashCode}";
    public static final String PHOTO_DELETE_BASE_URL = "/seller/deletePropertyPhoto/";
    private static final String PHOTO_UPLOAD_URL = "/seller/uploadPropertyPhoto/{propertyHashCode}";
    public static final String PHOTO_UPLOAD_BASE_URL = "/seller/uploadPropertyPhoto/";
    private static final String PHOTO_THUMBNAIL_DOWNLOAD_URL = "/user/downloadPropertyPhotoThumbnail/{photoHashCode}";
    private static final String PHOTO_REVIEW_VIEW_NAME = "admin/propertyPhotoReview";
    public static final String PHOTO_THUMBNAIL_BASE_DOWNLOAD_URL = "/user/downloadPropertyPhotoThumbnail/";
    public static final String PHOTO_REVIEW_URL = "/admin/propertyPhotoReview";
    private static final String PHOTO_APPROVE_URL = "/admin/approvePropertyPhoto/{photoHashCode}";
    public static final String PHOTO_APPROVE_BASE_URL = "/admin/approvePropertyPhoto/";
    private static final String PHOTO_REJECT_URL = "/admin/rejectPropertyPhoto/{photoHashCode}";
    public static final String PHOTO_REJECT_BASE_URL = "/admin/rejectPropertyPhoto/";

    @Inject
    private PropertyPhotoService propertyPhotoService;
    @Inject
    private UserService userService;
    @Inject
    private PropertyPhotoListViewModelAssembler propertyPhotoListViewModelAssembler;

    protected PropertyPhotoController() {
        // Required for Spring init
    }

    public PropertyPhotoController(final PropertyPhotoService propertyPhotoService, final UserService userService,
            final PropertyPhotoListViewModelAssembler propertyPhotoListViewModelAssembler) {
        this.propertyPhotoService = propertyPhotoService;
        this.userService = userService;
        this.propertyPhotoListViewModelAssembler = propertyPhotoListViewModelAssembler;
    }

    @RequestMapping(value = PHOTO_UPLOAD_URL, method = RequestMethod.POST)
    @ResponseBody
    public final String uploadPropertyPhoto(@PathVariable int propertyHashCode, MultipartFile file, HttpSession httpSession)
            throws Exception {
        try {
            Property property = userService.getPropertyForSaleByHashCode(getUserFromHttpSession(httpSession), propertyHashCode);
            return propertyPhotoService.addPhoto(property, file.getBytes(), file.getOriginalFilename()).toString();
        } catch (PropertyNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = PHOTO_THUMBNAIL_DOWNLOAD_URL, method = RequestMethod.GET)
    public final ResponseEntity<byte[]> downloadPropertyPhotoThumbnail(@PathVariable int photoHashCode, HttpSession httpSession)
            throws Exception {
        return new ResponseEntity<>(propertyPhotoService.getPhotoThumbnailData(photoHashCode), new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = PHOTO_DELETE_URL, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public final void deletePropertyPhotoThumbnail(@PathVariable int propertyHashCode, @PathVariable int photoHashCode,
            HttpSession httpSession) throws Exception {
        Property property = userService.getPropertyForSaleByHashCode(getUserFromHttpSession(httpSession), propertyHashCode);
        propertyPhotoService.removePhoto(property, photoHashCode);
    }

    @RequestMapping(value = PHOTO_REVIEW_URL, method = RequestMethod.GET)
    public final ModelAndView displayPhotoReviewView(ModelMap modelMap) throws Exception {
        List<PropertyPhoto> propertyPhotos = propertyPhotoService.getPhotosWaitingForApproval();
        modelMap.put(PropertyPhotoListViewModel.NAME, propertyPhotoListViewModelAssembler.assembleFromCollection(propertyPhotos));
        return new ModelAndView(PHOTO_REVIEW_VIEW_NAME, modelMap);
    }

    @RequestMapping(value = PHOTO_APPROVE_URL, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public final void approvePhoto(@PathVariable int photoHashCode) throws Exception {
        propertyPhotoService.approvePhoto(photoHashCode);
    }

    @RequestMapping(value = PHOTO_REJECT_URL, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public final void rejectPhoto(@PathVariable int photoHashCode) throws Exception {
        propertyPhotoService.rejectPhoto(photoHashCode);
    }

}
