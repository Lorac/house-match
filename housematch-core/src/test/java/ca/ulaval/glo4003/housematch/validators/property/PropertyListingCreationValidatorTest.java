package ca.ulaval.glo4003.housematch.validators.property;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.domain.streetaddress.State;
import ca.ulaval.glo4003.housematch.domain.streetaddress.StreetAddress;

public class PropertyListingCreationValidatorTest {

    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.SINGLE_FAMILY_HOME;
    private static final BigDecimal SAMPLE_SELLING_PRICE = BigDecimal.valueOf(523.5);
    private static final BigDecimal SAMPLE_NEGATIVE_SELLING_PRICE = BigDecimal.valueOf(-523.5);
    private static final Integer SAMPLE_ADDRESS_NUMBER = 890;
    private static final Integer SAMPLE_NEGATIVE_ADDRESS_NUMBER = -890;
    private static final String SAMPLE_STREET_NAME = "street";
    private static final String SAMPLE_ADDITIONNAL_ADDRESS_INFO = "Apt #8";
    private static final String SAMPLE_ZIPCODE = "G1V 3X4";
    private static final String SAMPLE_INVALID_ZIPCODE = "3433";
    private static final State SAMPLE_STATE = State.QC;
    private static final String SAMPLE_BLANK_EXPRESSION = "  ";

    private StreetAddress streetAddressMock;

    private PropertyListingCreationValidator propertyListingCreationValidator;

    @Before
    public void init() throws Exception {
        streetAddressMock = mock(StreetAddress.class);
        stubMethods();
        propertyListingCreationValidator = new PropertyListingCreationValidator();
    }

    private void stubMethods() {
        when(streetAddressMock.getAddressNumber()).thenReturn(SAMPLE_ADDRESS_NUMBER);
        when(streetAddressMock.getStreetName()).thenReturn(SAMPLE_STREET_NAME);
        when(streetAddressMock.getAdditionalAddressInfo()).thenReturn(SAMPLE_ADDITIONNAL_ADDRESS_INFO);
        when(streetAddressMock.getZipCode()).thenReturn(SAMPLE_ZIPCODE);
        when(streetAddressMock.getState()).thenReturn(SAMPLE_STATE);
    }

    @Test
    public void propertyListingCreationUsingValidValuesPassesValidation() throws Exception {
        try {
            propertyListingCreationValidator.validatePropertyListingCreation(SAMPLE_PROPERTY_TYPE, streetAddressMock,
                    SAMPLE_SELLING_PRICE);
        } catch (Exception e) {
            fail();
        }
    }

    @Test(expected = PropertyListingCreationValidationException.class)
    public void propertyListingCreationWithNoSteetAddressSpecifiedThrowsPropertyListingCreationValidationException()
            throws Exception {
        propertyListingCreationValidator.validatePropertyListingCreation(SAMPLE_PROPERTY_TYPE, null,
                SAMPLE_SELLING_PRICE);
    }

    @Test(expected = PropertyListingCreationValidationException.class)
    public void propertyListingCreationWithNoAddressNumberSpecifiedThrowsPropertyListingCreationValidationException()
            throws Exception {
        when(streetAddressMock.getAddressNumber()).thenReturn(null);
        propertyListingCreationValidator.validatePropertyListingCreation(SAMPLE_PROPERTY_TYPE, streetAddressMock,
                SAMPLE_SELLING_PRICE);
    }

    @Test(expected = PropertyListingCreationValidationException.class)
    public void propertyListingCreationWithNegativeAddressNumberThrowsPropertyListingCreationValidationException()
            throws Exception {
        when(streetAddressMock.getAddressNumber()).thenReturn(SAMPLE_NEGATIVE_ADDRESS_NUMBER);
        propertyListingCreationValidator.validatePropertyListingCreation(SAMPLE_PROPERTY_TYPE, streetAddressMock,
                SAMPLE_SELLING_PRICE);
    }

    @Test(expected = PropertyListingCreationValidationException.class)
    public void propertyListingCreationWithBlankStreetNameThrowsPropertyListingCreationValidationException()
            throws Exception {
        when(streetAddressMock.getStreetName()).thenReturn(SAMPLE_BLANK_EXPRESSION);
        propertyListingCreationValidator.validatePropertyListingCreation(SAMPLE_PROPERTY_TYPE, streetAddressMock,
                SAMPLE_SELLING_PRICE);
    }

    @Test(expected = PropertyListingCreationValidationException.class)
    public void propertyListingCreationWithBlankZipCodeThrowsPropertyListingCreationValidationException()
            throws Exception {
        when(streetAddressMock.getZipCode()).thenReturn(SAMPLE_BLANK_EXPRESSION);
        propertyListingCreationValidator.validatePropertyListingCreation(SAMPLE_PROPERTY_TYPE, streetAddressMock,
                SAMPLE_SELLING_PRICE);
    }

    @Test(expected = PropertyListingCreationValidationException.class)
    public void propertyListingCreationWithInvalidZipCodeFormatThrowsPropertyListingCreationValidationException()
            throws Exception {
        when(streetAddressMock.getZipCode()).thenReturn(SAMPLE_INVALID_ZIPCODE);
        propertyListingCreationValidator.validatePropertyListingCreation(SAMPLE_PROPERTY_TYPE, streetAddressMock,
                SAMPLE_SELLING_PRICE);
    }

    @Test(expected = PropertyListingCreationValidationException.class)
    public void propertyListingCreationWithNoStateSpecifiedThrowsPropertyListingCreationValidationException()
            throws Exception {
        when(streetAddressMock.getState()).thenReturn(null);
        propertyListingCreationValidator.validatePropertyListingCreation(SAMPLE_PROPERTY_TYPE, streetAddressMock,
                SAMPLE_SELLING_PRICE);
    }

    @Test(expected = PropertyListingCreationValidationException.class)
    public void propertyListingCreationWithNoPropertyTypeSpecifiedThrowsPropertyListingCreationValidationException()
            throws Exception {
        propertyListingCreationValidator.validatePropertyListingCreation(null, streetAddressMock, SAMPLE_SELLING_PRICE);
    }

    @Test(expected = PropertyListingCreationValidationException.class)
    public void propertyListingCreationWithNoSellingPriceSpecifiedThrowsPropertyListingCreationValidationException()
            throws Exception {
        propertyListingCreationValidator.validatePropertyListingCreation(SAMPLE_PROPERTY_TYPE, streetAddressMock, null);
    }

    @Test(expected = PropertyListingCreationValidationException.class)
    public void propertyListingCreationWithNegativeSellingPriceSpecifiedThrowsPropertyListingCreationValidationException()
            throws Exception {
        propertyListingCreationValidator.validatePropertyListingCreation(SAMPLE_PROPERTY_TYPE, streetAddressMock,
                SAMPLE_NEGATIVE_SELLING_PRICE);
    }
}
