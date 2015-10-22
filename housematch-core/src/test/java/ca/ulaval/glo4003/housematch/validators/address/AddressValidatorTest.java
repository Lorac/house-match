package ca.ulaval.glo4003.housematch.validators.address;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.address.Region;

public class AddressValidatorTest {

    private static final Integer SAMPLE_ADDRESS_NUMBER = 890;
    private static final Integer SAMPLE_NEGATIVE_STREET_NUMBER = -890;
    private static final String SAMPLE_STREET_NAME = "street";
    private static final String SAMPLE_ADDITIONNAL_ADDRESS_INFO = "Apt #4";
    private static final String SAMPLE_TOWN = "Quebec";
    private static final String SAMPLE_POSTCODE = "G1V 3X4";
    private static final String SAMPLE_INVALID_POSTCODE = "3433";
    private static final Region SAMPLE_REGION = Region.QC;
    private static final String SAMPLE_BLANK_EXPRESSION = "  ";

    private Address address;

    private AddressValidator addressValidator;

    @Before
    public void init() throws Exception {
        address = new Address();
        stubMethods();
        addressValidator = new AddressValidator();
    }

    private void stubMethods() {
        address.setStreetNumber(SAMPLE_ADDRESS_NUMBER);
        address.setStreetName(SAMPLE_STREET_NAME);
        address.setAdditionalAddressInfo(SAMPLE_ADDITIONNAL_ADDRESS_INFO);
        address.setTown(SAMPLE_TOWN);
        address.setPostCode(SAMPLE_POSTCODE);
        address.setRegion(SAMPLE_REGION);
        address.setTown(SAMPLE_TOWN);
    }

    @Test
    public void addressValidationUsingValidValuesPassesValidation() throws Exception {
        try {
            addressValidator.validateAddress(address);
        } catch (Exception e) {
            fail();
        }
    }

    @Test(expected = AddressValidationException.class)
    public void addressValidationWithNoStreetNumberSpecifiedThrowsPropertyCreationValidationException() throws Exception {
        address.setStreetNumber(null);
        addressValidator.validateAddress(address);
    }

    @Test(expected = AddressValidationException.class)
    public void addressValidationWithNegativeStreetNumberThrowsPropertyCreationValidationException() throws Exception {
        address.setStreetNumber(SAMPLE_NEGATIVE_STREET_NUMBER);
        addressValidator.validateAddress(address);
    }

    @Test(expected = AddressValidationException.class)
    public void addressValidationWithBlankStreetNameThrowsPropertyCreationValidationException() throws Exception {
        address.setStreetName(SAMPLE_BLANK_EXPRESSION);
        addressValidator.validateAddress(address);
    }

    @Test(expected = AddressValidationException.class)
    public void addressValidationWithBlankPostCodeThrowsPropertyCreationValidationException() throws Exception {
        address.setPostCode(SAMPLE_BLANK_EXPRESSION);
        addressValidator.validateAddress(address);
    }

    @Test(expected = AddressValidationException.class)
    public void addressValidationWithInvalidPostCodeFormatThrowsPropertyCreationValidationException() throws Exception {
        address.setPostCode(SAMPLE_INVALID_POSTCODE);
        addressValidator.validateAddress(address);
    }

    @Test(expected = AddressValidationException.class)
    public void addressValidationWithBlankTownThrowsPropertyCreationValidationException() throws Exception {
        address.setTown(SAMPLE_BLANK_EXPRESSION);
        addressValidator.validateAddress(address);
    }

    @Test(expected = AddressValidationException.class)
    public void addressValidationWithBlankRegionThrowsPropertyCreationValidationException() throws Exception {
        address.setRegion(null);
        addressValidator.validateAddress(address);
    }
}
