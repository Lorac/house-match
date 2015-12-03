package ca.ulaval.glo4003.housematch.domain.picture;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PictureTest {
    private final static String SAMPLE_PATH = "sample/path/to/some/pretty/picture.jpg";
    private final static String ANOTHER_SAMPLE_PATH = "this/is/another/pretty/path/teehee.png";
    private final static PictureStatus SAMPLE_STATUS = PictureStatus.WAITING_FOR_MODERATION;
    private final static PictureStatus ANOTHER_SAMPLE_STATUS = PictureStatus.APPROVED;
    
    private Picture picture;
    private Picture anotherPicture;
    
    @Before
    public void init() {
        picture = new Picture(SAMPLE_PATH, SAMPLE_STATUS);
        anotherPicture = new Picture(ANOTHER_SAMPLE_PATH, ANOTHER_SAMPLE_STATUS);
    }
    
    @Test
    public void changingTheStatusOfAPictureChangesTheStatusOfSaidPicture() {
        picture.changeStatus(ANOTHER_SAMPLE_STATUS);
        assertEquals(ANOTHER_SAMPLE_STATUS, picture.getStatus());
    }
    
    @Test
    public void doingTheEqualEqualOperationOnTwoPicturesWithDifferentPathsReturnsFalse() {
        assertFalse(picture == anotherPicture);
    }
    
    @Test
    public void twoPicturesWithTheSamePathAreConsideredEqual() {
        Picture anotherPropertyPicture = new Picture(SAMPLE_PATH, ANOTHER_SAMPLE_STATUS);
        assertEquals(picture, anotherPropertyPicture);
    }
}
