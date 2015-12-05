package ca.ulaval.glo4003.housematch.domain.propertyphoto;

public interface PropertyPhotoObserver {

    void propertyPhotoStatusChanged(PropertyPhoto propertyPhoto, PropertyPhotoStatus newStatus);

}
