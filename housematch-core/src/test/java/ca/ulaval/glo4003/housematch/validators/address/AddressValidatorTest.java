package ca.ulaval.glo4003.housematch.validators.address;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.address.Region;

public class AddressValidatorTest {

    private static final Integer SAMPLE_ADDRESS_NUMBER = 890;
    private static final Integer SAMPLE_NEGATIVE_ADDRESS_NUMBER = -890;
    private static final String SAMPLE_STREET_NAME = "street";
    private static final String SAMPLE_ADDITIONNAL_ADDRESS_INFO = "Apt #8";
    private static final String SAMPLE_POSTCODE = "G1V 3X4";
    private static final String SAMPLE_INVALID_POSTCODE = "3433";
    private static final Region SAMPLE_REGION = Region.QC;
    private static final String SAMPLE_BLANK_EXPRESSION = "  ";
    private static final String SAMPLE_CITY = "Quebec";

    private Address addressMock;

    private AddressValidator addressValidator;

    @Before
    public void init() throws Exception {
        addressMock = mock(Address.class);
        stubMethods();
        addressValidator = new AddressValidator();
    }

    private void stubMethods() {
        when(addressMock.getAddressNumber()).thenReturn(SAMPLE_ADDRESS_NUMBER);
        when(addressMock.getStreetName()).thenReturn(SAMPLE_STREET_NAME);
        when(addressMock.getAdditionalAddressInfo()).thenReturn(SAMPLE_ADDITIONNAL_ADDRESS_INFO);
        when(addressMock.getPostCode()).thenReturn(SAMPLE_POSTCODE);
        when(addressMock.getRegion()).thenReturn(SAMPLE_REGION);
        when(addressMock.getCity()).thenReturn(SAMPLE_CITY);
    }

    @Test
    public void addressValidationUsingValidValuesPassesValidation() throws Exception {
        try {
            addressValidator.validateAddress(addressMock);
        } catch (Exception e) {
            fail();
        }
    }

    @Test(expected = AddressValidationException.class)
    public void addressValidationWithNoAddressNumberSpecifiedThrowsPropertyCreationValidationException()
            throws Exception {
        when(addressMock.getAddressNumber()).thenReturn(null);
        addressValidator.validateAddress(addressMock);
    }

    @Test(expected = AddressValidationException.class)
    public void addressValidationWithNegativeAddressNumberThrowsPropertyCreationValidationException() throws Exception {
        when(addressMock.getAddressNumber()).thenReturn(SAMPLE_NEGATIVE_ADDRESS_NUMBER);
        addressValidator.validateAddress(addressMock);
    }

    @Test(expected = AddressValidationException.class)
    public void addressValidationWithBlankStreetNameThrowsPropertyCreationValidationException() throws Exception {
        when(addressMock.getStreetName()).thenReturn(SAMPLE_BLANK_EXPRESSION);
        addressValidator.validateAddress(addressMock);
    }

    @Test(expected = AddressValidationException.class)
    public void addressValidationWithBlankPostCodeThrowsPropertyCreationValidationException() throws Exception {
        when(addressMock.getPostCode()).thenReturn(SAMPLE_BLANK_EXPRESSION);
        addressValidator.validateAddress(addressMock);
    }

    @Test(expected = AddressValidationException.class)
    public void paddressValidationWithInvalidPostCodeFormatThrowsPropertyCreationValidationException()
            throws Exception {
        when(addressMock.getPostCode()).thenReturn(SAMPLE_INVALID_POSTCODE);
        addressValidator.validateAddress(addressMock);
    }

    @Test(expected = AddressValidationException.class)
    public void addressValidationWithNoRegionSpecifiedThrowsPropertyCreationValidationException() throws Exception {
        when(addressMock.getRegion()).thenReturn(null);
        addressValidator.validateAddress(addressMock);
    }
}
