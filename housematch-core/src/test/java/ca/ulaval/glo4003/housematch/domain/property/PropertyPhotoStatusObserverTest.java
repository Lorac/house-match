package ca.ulaval.glo4003.housematch.domain.property;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoStatus;

public class PropertyPhotoStatusObserverTest {

    private Property propertyMock;
    private PropertyPhoto propertyPhotoMock;

    private PropertyPhotoStatusObserver propertyPhotoStatusObserver;

    @Before
    public void init() {
        propertyMock = mock(Property.class);
        propertyPhotoMock = mock(PropertyPhoto.class);
        propertyPhotoStatusObserver = new PropertyPhotoStatusObserver(propertyMock);
    }

    @Test
    public void propertyPhotoStatusChangeToRejectedCallsPhotoRejectionOnProperty() {
        propertyPhotoStatusObserver.propertyPhotoStatusChanged(propertyPhotoMock, PropertyPhotoStatus.REJECTED);
        verify(propertyMock).rejectPhoto(propertyPhotoMock);
    }

    @Test
    public void propertyPhotoStatusChangeToStatusOtherThanRejectedDoesNotCallPhotoRejectionOnProperty() {
        propertyPhotoStatusObserver.propertyPhotoStatusChanged(propertyPhotoMock, PropertyPhotoStatus.APPROVED);
        verify(propertyMock, never()).rejectPhoto(propertyPhotoMock);
    }

}
