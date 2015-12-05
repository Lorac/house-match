package ca.ulaval.glo4003.housematch.web.viewmodels;

public class PropertyPhotoViewModel extends ViewModel {

    public static final String NAME = "propertyPhoto";

    private int hashCode;

    @Override
    public String getName() {
        return NAME;
    }

    public int getHashCode() {
        return hashCode;
    }

    public void setHashCode(int hashCode) {
        this.hashCode = hashCode;
    }

}
