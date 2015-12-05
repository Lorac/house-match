package ca.ulaval.glo4003.housematch.web.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.services.property.PropertyService;
import ca.ulaval.glo4003.housematch.services.propertyphoto.PropertyPhotoService;
import ca.ulaval.glo4003.housematch.services.user.UserService;
import ca.ulaval.glo4003.housematch.web.assemblers.PropertyPhotoListViewModelAssembler;

public class PropertyPhotoControllerTest extends BaseControllerTest {

    private static final String FILE_CONTENT = "text/plain";
    private static final String SAMPLE_FILE_NAME = "fileName";
    private static final String SAMPLE_ORIGINAL_FILE_NAME = "filename.txt";
    private static final Integer SAMPLE_PHOTO_HASH_CODE = 345445;
    private static final byte[] SAMPLE_BYTES = new byte[1];

    private Property propertyMock;
    private PropertyService propertyServiceMock;
    private PropertyPhotoService propertyPhotoServiceMock;
    private UserService userServiceMock;
    private PropertyPhotoListViewModelAssembler propertyPhotoListViewModelAssemblerMock;
    private MockMultipartFile multipartFileMock;

    private PropertyPhotoController propertyPhotoController;
    private List<PropertyPhoto> propertyPhotos = new ArrayList<>();
    private String samplePhotoDeleteUrl;
    private String sampleThumbnailDownloadUrl;
    private String sampleApproveUrl;
    private String sampleRejectUrl;

    @Before
    public void init() throws Exception {
        super.init();
        initMocks();
        initStubs();
        initSampleUrls();
        propertyPhotoController = new PropertyPhotoController(propertyPhotoServiceMock, userServiceMock,
                propertyPhotoListViewModelAssemblerMock);
        mockMvc = MockMvcBuilders.standaloneSetup(propertyPhotoController).setViewResolvers(viewResolver).build();
    }

    private void initMocks() {
        propertyMock = mock(Property.class);
        propertyServiceMock = mock(PropertyService.class);
        propertyPhotoServiceMock = mock(PropertyPhotoService.class);
        userServiceMock = mock(UserService.class);
        propertyPhotoListViewModelAssemblerMock = mock(PropertyPhotoListViewModelAssembler.class);
        multipartFileMock = new MockMultipartFile(SAMPLE_FILE_NAME, SAMPLE_ORIGINAL_FILE_NAME, FILE_CONTENT, SAMPLE_BYTES);
    }

    private void initStubs() throws Exception {
        when(propertyServiceMock.createProperty(any(), any(), any(), any())).thenReturn(propertyMock);
        when(userServiceMock.getPropertyForSaleByHashCode(userMock, propertyMock.hashCode())).thenReturn(propertyMock);
        when(propertyPhotoServiceMock.addPhoto(propertyMock, SAMPLE_BYTES)).thenReturn(SAMPLE_PHOTO_HASH_CODE);
    }

    private void initSampleUrls() {
        samplePhotoDeleteUrl = PropertyPhotoController.PHOTO_DELETE_BASE_URL + propertyMock.hashCode() + "/" + SAMPLE_PHOTO_HASH_CODE;
        sampleThumbnailDownloadUrl = PropertyPhotoController.PHOTO_THUMBNAIL_BASE_DOWNLOAD_URL + SAMPLE_PHOTO_HASH_CODE;
        sampleApproveUrl = PropertyPhotoController.PHOTO_APPROVE_BASE_URL + SAMPLE_PHOTO_HASH_CODE;
        sampleRejectUrl = PropertyPhotoController.PHOTO_REJECT_BASE_URL + SAMPLE_PHOTO_HASH_CODE;
    }

    @Test
    public void propertyPhotoControllerRetrievesTheUserFromTheUserServiceDuringPhotoUploadRequest() throws Exception {
        propertyPhotoController.uploadPropertyPhoto(propertyMock.hashCode(), multipartFileMock, mockHttpSession);
        verify(userServiceMock).getPropertyForSaleByHashCode(userMock, propertyMock.hashCode());
    }

    @Test
    public void propertyPhotoControllerAddsThePhotoUsingThePropertyPhotoServiceDuringPhotoUploadRequest() throws Exception {
        propertyPhotoController.uploadPropertyPhoto(propertyMock.hashCode(), multipartFileMock, mockHttpSession);
        verify(propertyPhotoServiceMock).addPhoto(propertyMock, SAMPLE_BYTES);
    }

    @Test
    public void propertyPhotoControllerReturnsThePhotoHashCodeUponSuccessfulPhotoUploadRequest() throws Exception {
        String result = propertyPhotoController.uploadPropertyPhoto(propertyMock.hashCode(), multipartFileMock, mockHttpSession);
        assertEquals(SAMPLE_PHOTO_HASH_CODE.toString(), result);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void propertyPhotoControllerReturnsHttpStatusNotFoundOnInvalidHashCodeDuringPhotoUploadRequest() throws Exception {
        doThrow(new PropertyNotFoundException()).when(userServiceMock).getPropertyForSaleByHashCode(userMock, propertyMock.hashCode());
        propertyPhotoController.uploadPropertyPhoto(propertyMock.hashCode(), multipartFileMock, mockHttpSession);
    }

    @Test
    public void propertyPhotoControllerRetrievesThePhotoThumbnailDataDuringPhotoThumbnailDownloadRequest() throws Exception {
        performGetRequest(sampleThumbnailDownloadUrl);
        verify(propertyPhotoServiceMock).getPhotoThumbnailData(SAMPLE_PHOTO_HASH_CODE);
    }

    @Test
    public void propertyPhotoControllerReturnsPhotoThumbnailDataDuringPhotoThumbnailDownloadRequest() throws Exception {
        when(propertyPhotoServiceMock.getPhotoThumbnailData(SAMPLE_PHOTO_HASH_CODE)).thenReturn(SAMPLE_BYTES);
        ResultActions results = performGetRequest(sampleThumbnailDownloadUrl);
        results.andExpect(content().bytes(SAMPLE_BYTES));
    }

    @Test
    public void propertyPhotoControllerRetrievesPropertyForSaleFromPropertyServiceDuringPhotoDeleteRequest() throws Exception {
        performPostRequest(samplePhotoDeleteUrl);
        verify(userServiceMock).getPropertyForSaleByHashCode(userMock, propertyMock.hashCode());
    }

    @Test
    public void propertyPhotoControllerRemovesPhotoUsingThePropertyPhotoServiceDuringPhotoDeleteRequest() throws Exception {
        performPostRequest(samplePhotoDeleteUrl);
        verify(propertyPhotoServiceMock).removePhoto(propertyMock, SAMPLE_PHOTO_HASH_CODE);
    }

    @Test
    public void propertyPhotoControllerReturnsHttpOkStatusUponSuccessfulPhotoDeleteRequest() throws Exception {
        ResultActions results = performPostRequest(samplePhotoDeleteUrl);
        results.andExpect(status().isOk());
    }

    @Test
    public void propertyPhotoControllerRendersPhotoReviewView() throws Exception {
        ResultActions results = performGetRequest(PropertyPhotoController.PHOTO_REVIEW_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(PropertyPhotoController.PHOTO_REVIEW_VIEW_NAME));
    }

    @Test
    public void propertyPhotoControllerRetrievesPhotosWaitingForApprivalFromThePropertyPhotoServiceDuringPhotoReviewRequest()
            throws Exception {
        performGetRequest(PropertyPhotoController.PHOTO_REVIEW_URL);
        verify(propertyPhotoServiceMock).getPhotosWaitingForApproval();
    }

    @Test
    public void propertyPhotoControllerAssemblesThePhotoListViewModelUsingTheAssemblerDuringPhotoReviewRequest() throws Exception {
        when(propertyPhotoServiceMock.getPhotosWaitingForApproval()).thenReturn(propertyPhotos);
        performGetRequest(PropertyPhotoController.PHOTO_REVIEW_URL);
        verify(propertyPhotoListViewModelAssemblerMock).assembleFromCollection(propertyPhotos);
    }

    @Test
    public void propertyPhotoControllerApprovesPhotoUsingThePropertyPhotoServiceDuringPhotoApprovalRequest() throws Exception {
        performPostRequest(sampleApproveUrl);
        verify(propertyPhotoServiceMock).approvePhoto(SAMPLE_PHOTO_HASH_CODE);
    }

    @Test
    public void propertyPhotoControllerReturnsHttpOkStatusUponSuccessfulPhotoApprovalRequest() throws Exception {
        ResultActions results = performPostRequest(sampleApproveUrl);
        results.andExpect(status().isOk());
    }

    @Test
    public void propertyPhotoControllerRejectsPhotoUsingThePropertyPhotoServiceDuringPhotoRejectionRequest() throws Exception {
        performPostRequest(sampleRejectUrl);
        verify(propertyPhotoServiceMock).rejectPhoto(SAMPLE_PHOTO_HASH_CODE);
    }

    @Test
    public void propertyPhotoControllerReturnsHttpOkStatusUponSuccessfulPhotoRejectionRequest() throws Exception {
        ResultActions results = performPostRequest(sampleRejectUrl);
        results.andExpect(status().isOk());
    }
}