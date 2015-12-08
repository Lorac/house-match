package ca.ulaval.glo4003.housematch.web.viewmodels;

public class PropertyPhotoViewModel extends ViewModel {

    public static final String NAME = "propertyPhotoViewModel";

    private int photoHashCode;

    @Override
    public String getName() {
        return NAME;
    }

    public int getPhotoHashCode() {
        return photoHashCode;
    }

    public void setHashCode(int photoHashCode) {
        this.photoHashCode = photoHashCode;
    }

}
