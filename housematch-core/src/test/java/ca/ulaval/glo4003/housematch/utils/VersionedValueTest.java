package ca.ulaval.glo4003.housematch.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class VersionedValueTest {

    private static final Object SAMPLE_VALUE = new Object();
    private static final Object SAMPLE_NEW_VALUE = new Object();
    private static final Integer SAMPLE_VALUE_VERSION = 5;

    private VersionedValue<Object> versionedValue;

    @Before
    public void init() {
        versionedValue = new VersionedValue<>(SAMPLE_VALUE);
    }

    @Test
    public void settingTheValueSetsTheValue() {
        versionedValue.setValue(SAMPLE_NEW_VALUE, SAMPLE_VALUE_VERSION);
        assertEquals(SAMPLE_NEW_VALUE, versionedValue.getValue());
    }

    @Test
    public void settingTheValueWithVersionSetsTheVersion() {
        versionedValue.setValue(SAMPLE_NEW_VALUE, SAMPLE_VALUE_VERSION);
        assertEquals(SAMPLE_VALUE_VERSION, versionedValue.getVersion());
    }
}
