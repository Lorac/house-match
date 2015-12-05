package ca.ulaval.glo4003.housematch.domain.propertyphoto;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class PropertyPhotoFactoryTest {

    private static final int SAMPLE_HASH_CODE = 2342;

    private byte[] fileBytes;
    private PropertyPhotoFactory propertyPhotoFactory;

    @Before
    public void init() {
        propertyPhotoFactory = new PropertyPhotoFactory();
    }

    @Test
    public void propertyPhotoFactoryCreatesThePropertyPhotoWithTheSpecifiedHashCode() {
        PropertyPhoto propertyPhoto = propertyPhotoFactory.createPropertyPhoto(SAMPLE_HASH_CODE);
        assertEquals(SAMPLE_HASH_CODE, propertyPhoto.hashCode());
    }

    @Test
    public void propertyPhotoFactoryCreatesThePropertyPhotoWithHashCodeCalculatedFromTheSpecifiedFileBytes() {
        PropertyPhoto propertyPhoto = propertyPhotoFactory.createPropertyPhoto(fileBytes);
        assertEquals(Math.abs(Arrays.hashCode(fileBytes)), propertyPhoto.hashCode());
    }
}
