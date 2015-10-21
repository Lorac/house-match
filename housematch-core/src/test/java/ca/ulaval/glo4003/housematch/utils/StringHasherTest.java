package ca.ulaval.glo4003.housematch.utils;

import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class StringHasherTest {

    private static final String SAMPLE_STRING = "samplestring";

    private StringHasher stringHasher;

    @Before
    public void init() {
        stringHasher = new StringHasher();
    }

    @Test
    public void stringHasherHashesTheSpecifiedString() {
        String hashedString = stringHasher.hash(SAMPLE_STRING);
        assertNotEquals(SAMPLE_STRING, hashedString);
    }
}
