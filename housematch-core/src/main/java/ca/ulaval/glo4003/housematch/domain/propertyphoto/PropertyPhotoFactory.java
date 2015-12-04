package ca.ulaval.glo4003.housematch.domain.propertyphoto;

import java.util.Arrays;

public class PropertyPhotoFactory {

    public PropertyPhoto createPropertyPhoto(int hashCode, String originalFileName) {
        return new PropertyPhoto(hashCode, originalFileName);
    }

    public PropertyPhoto createPropertyPhoto(byte[] fileBytes, String originalFileName) {
        return new PropertyPhoto(Math.abs(Arrays.hashCode(fileBytes)), originalFileName);
    }

}
