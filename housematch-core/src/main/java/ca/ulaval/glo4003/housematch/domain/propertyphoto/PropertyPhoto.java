package ca.ulaval.glo4003.housematch.domain.propertyphoto;

public class PropertyPhoto extends PropertyPhotoObservable {

    private int hashCode;
    private PropertyPhotoStatus status = PropertyPhotoStatus.WAITING_FOR_APPROVAL;

    public PropertyPhoto(final int hashCode) {
        this.hashCode = hashCode;
    }

    public PropertyPhotoStatus getStatus() {
        return status;
    }

    public void setStatus(PropertyPhotoStatus status) {
        this.status = status;
    }

    public Boolean isApproved() {
        return status == PropertyPhotoStatus.APPROVED;
    }

    public void updateStatus(PropertyPhotoStatus status) {
        this.status = status;
        propertyPhotoStatusChanged(this, status);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PropertyPhoto)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        PropertyPhoto propertyPhoto = (PropertyPhoto) obj;
        return propertyPhoto.hashCode() == this.hashCode();
    }

    @Override
    public String toString() {
        return String.valueOf(hashCode);
    }

}
