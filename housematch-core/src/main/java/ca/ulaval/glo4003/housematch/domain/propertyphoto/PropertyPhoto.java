package ca.ulaval.glo4003.housematch.domain.propertyphoto;

public class PropertyPhoto {

    private int hashCode;
    private String originalFileName;

    public PropertyPhoto(int hashCode, String originalFileName) {
        this.hashCode = hashCode;
        this.originalFileName = originalFileName;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

}
