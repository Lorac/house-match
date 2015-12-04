package ca.ulaval.glo4003.housematch.web.controllers;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoNotFoundException;
import ca.ulaval.glo4003.housematch.services.property.PropertyPhotoService;
import ca.ulaval.glo4003.housematch.services.user.UserService;

@Controller
public class PropertyPhotoController extends BaseController {

    private static final String PHOTO_UPLOAD_URL = "/seller/uploadPropertyPhoto/{propertyHashCode}";
    private static final String PHOTO_DOWNLOAD_URL = "/seller/downloadPropertyPhoto/{photoHashCode}";
    private static final String PHOTO_DOWNLOAD_BASE_URL = "/seller/downloadPropertyPhoto";

    @Inject
    private PropertyPhotoService propertyPhotoService;
    @Inject
    private UserService userService;

    protected PropertyPhotoController() {
        // Required for Spring init
    }

    public PropertyPhotoController(PropertyPhotoService propertyPhotoService, UserService userService) {
        this.propertyPhotoService = propertyPhotoService;
        this.userService = userService;
    }

    @RequestMapping(value = PHOTO_UPLOAD_URL, method = RequestMethod.POST)
    public final @ResponseBody String uploadPropertyPhoto(@PathVariable int propertyHashCode, MultipartFile file, HttpSession httpSession)
            throws Exception {
        try {
            Property property = userService.getPropertyForSaleByHashCode(getUserFromHttpSession(httpSession), propertyHashCode);
            int hashCode = propertyPhotoService.addPropertyPhoto(property, file.getBytes(), file.getOriginalFilename());
            return String.format("%s/%d", PHOTO_DOWNLOAD_BASE_URL, hashCode);
        } catch (PropertyNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = PHOTO_DOWNLOAD_URL, method = RequestMethod.GET)
    public final ResponseEntity<byte[]> downloadPropertyPhoto(@PathVariable int photoHashCode) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<>(propertyPhotoService.getPropertyPhotoData(photoHashCode), headers, HttpStatus.OK);
        } catch (PropertyPhotoNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }

}
