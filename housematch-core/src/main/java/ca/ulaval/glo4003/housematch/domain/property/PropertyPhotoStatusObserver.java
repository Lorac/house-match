package ca.ulaval.glo4003.housematch.domain.property;

import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoObserver;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoStatus;

public class PropertyPhotoStatusObserver implements PropertyPhotoObserver {

    private Property property;

    public PropertyPhotoStatusObserver(final Property property) {
        this.property = property;
    }

    @Override
    public void propertyPhotoStatusChanged(PropertyPhoto propertyPhoto, PropertyPhotoStatus newStatus) {
        if (newStatus == PropertyPhotoStatus.REJECTED) {
            property.rejectPhoto(propertyPhoto);
        }
    }

}
