package ca.ulaval.glo4003.housematch.domain.propertyphoto;

public class PropertyPhoto {

    private int hashCode;
    private String originalFileName;

    public PropertyPhoto(final int hashCode, final String originalFileName) {
        this.hashCode = hashCode;
        this.originalFileName = originalFileName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
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
