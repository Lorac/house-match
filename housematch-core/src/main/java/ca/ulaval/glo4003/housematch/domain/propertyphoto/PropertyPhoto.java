package ca.ulaval.glo4003.housematch.domain.propertyphoto;

import java.util.Arrays;

public class PropertyPhoto extends PropertyPhotoObservable {

    private int hashCode;
    private PropertyPhotoStatus status = PropertyPhotoStatus.PENDING_APPROVAL;
    private String originalFileName;

    public PropertyPhoto(final byte[] fileBytes, final String originalFileName) {
        this.hashCode = Math.abs(Arrays.hashCode(fileBytes));
        this.originalFileName = originalFileName;
    }

    public PropertyPhoto(final int hashCode, final String originalFileName) {
        this.hashCode = hashCode;
        this.originalFileName = originalFileName;
    }

    public PropertyPhotoStatus getStatus() {
        return status;
    }

    public void setStatus(PropertyPhotoStatus status) {
        this.status = status;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
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

}
