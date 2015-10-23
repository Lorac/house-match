package ca.ulaval.glo4003.housematch.domain.address;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class AddressTest {

    private static final Integer SAMPLE_STREET_NUMBER = 28;
    private static final String SAMPLE_STREET_NAME = "abc";
    private static final String SAMPLE_TOWN = "abc";
    private static final String SAMPLE_POST_CODE = "abc";
    private static final String SAMPLE_ADDITIONAL_ADDRESS_INFO = "abc";
    private static final Region SAMPLE_REGION = Region.BC;

    Address address;

    @Before
    public void init() {
        address = new Address();
    }

    @Test
    public void settingTheStreetNumberSetsTheStreetNumber() {
        address.setStreetNumber(SAMPLE_STREET_NUMBER);
        assertEquals(SAMPLE_STREET_NUMBER, address.getStreetNumber());
    }

    @Test
    public void settingTheStreetNameSetsTheStreetName() {
        address.setStreetName(SAMPLE_STREET_NAME);
        assertEquals(SAMPLE_STREET_NAME, address.getStreetName());
    }

    @Test
    public void settingTheTownSetsTheTown() {
        address.setTown(SAMPLE_TOWN);
        assertEquals(SAMPLE_TOWN, address.getTown());
    }

    @Test
    public void settingTheAdditionalAddressInfoSetsTheAdditionalAddressInfo() {
        address.setAdditionalAddressInfo(SAMPLE_ADDITIONAL_ADDRESS_INFO);
        assertEquals(SAMPLE_ADDITIONAL_ADDRESS_INFO, address.getAdditionalAddressInfo());
    }

    @Test
    public void settingThePostCodeSetsThePostCode() {
        address.setPostCode(SAMPLE_POST_CODE);
        assertEquals(SAMPLE_POST_CODE, address.getPostCode());
    }

    @Test
    public void settingTheRegionSetsTheRegion() {
        address.setRegion(SAMPLE_REGION);
        assertEquals(SAMPLE_REGION, address.getRegion());
    }
}
