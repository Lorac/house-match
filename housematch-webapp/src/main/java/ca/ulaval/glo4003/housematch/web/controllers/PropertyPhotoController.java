package ca.ulaval.glo4003.housematch.web.controllers;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.services.property.PropertyPhotoService;
import ca.ulaval.glo4003.housematch.services.user.UserService;

@Controller
public class PropertyPhotoController extends BaseController {

    private static final String PHOTO_DELETE_URL = "/seller/deletePropertyPhoto/{propertyHashCode}/{photoHashCode}";
    private static final String PHOTO_UPLOAD_URL = "/seller/uploadPropertyPhoto/{propertyHashCode}";
    private static final String PHOTO_DOWNLOAD_URL = "/user/downloadPropertyPhoto/{propertyHashCode}/{photoHashCode}";
    private static final String PHOTO_THUMBNAIL_DOWNLOAD_URL = "/user/downloadPropertyPhotoThumbnail/{propertyHashCode}/{photoHashCode}";
    private static final String PHOTO_REVIEW_VIEW_NAME = "admin/propertyPhotoReview";
    public static final String PHOTO_THUMBNAIL_BASE_DOWNLOAD_URL = "/user/downloadPropertyPhotoThumbnail/";
    public static final String PHOTO_REVIEW_URL = "/admin/propertyPhotoReview";

    @Inject
    private PropertyPhotoService propertyPhotoService;
    @Inject
    private UserService userService;

    protected PropertyPhotoController() {
        // Required for Spring init
    }

    public PropertyPhotoController(final PropertyPhotoService propertyPhotoService, final UserService userService) {
        this.propertyPhotoService = propertyPhotoService;
        this.userService = userService;
    }

    @RequestMapping(value = PHOTO_UPLOAD_URL, method = RequestMethod.POST)
    @ResponseBody
    public final String uploadPropertyPhoto(@PathVariable int propertyHashCode, MultipartFile file, HttpSession httpSession)
            throws Exception {
        try {
            Property property = userService.getPropertyForSaleByHashCode(getUserFromHttpSession(httpSession), propertyHashCode);
            return propertyPhotoService.addPropertyPhoto(property, file.getBytes(), file.getOriginalFilename()).toString();
        } catch (PropertyNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = PHOTO_DOWNLOAD_URL, method = RequestMethod.GET)
    public final ResponseEntity<byte[]> downloadPropertyPhoto(@PathVariable int photoHashCode, HttpSession httpSession) throws Exception {
        return new ResponseEntity<>(propertyPhotoService.getPropertyPhotoData(photoHashCode), new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = PHOTO_THUMBNAIL_DOWNLOAD_URL, method = RequestMethod.GET)
    public final ResponseEntity<byte[]> downloadPropertyPhotoThumbnail(@PathVariable int propertyHashCode, @PathVariable int photoHashCode,
            HttpSession httpSession) throws Exception {
        return new ResponseEntity<>(propertyPhotoService.getPropertyPhotoThumbnailData(photoHashCode), new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = PHOTO_DELETE_URL, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public final void deletePropertyPhotoThumbnail(@PathVariable int propertyHashCode, @PathVariable int photoHashCode,
            HttpSession httpSession) throws Exception {
        Property property = userService.getPropertyForSaleByHashCode(getUserFromHttpSession(httpSession), propertyHashCode);
        propertyPhotoService.deletePropertyPhoto(property, photoHashCode);
    }

    @RequestMapping(value = PHOTO_REVIEW_URL, method = RequestMethod.GET)
    public final ModelAndView deletePropertyPhotoThumbnail() throws Exception {
        return new ModelAndView(PHOTO_REVIEW_VIEW_NAME);
    }

}
