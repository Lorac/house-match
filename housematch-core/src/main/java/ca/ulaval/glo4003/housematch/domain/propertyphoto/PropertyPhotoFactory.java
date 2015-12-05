package ca.ulaval.glo4003.housematch.domain.propertyphoto;

import java.util.Arrays;

public class PropertyPhotoFactory {

    public PropertyPhoto createPropertyPhoto(int hashCode) {
        return new PropertyPhoto(hashCode);
    }

    public PropertyPhoto createPropertyPhoto(byte[] fileBytes) {
        return new PropertyPhoto(Math.abs(Arrays.hashCode(fileBytes)));
    }

}
