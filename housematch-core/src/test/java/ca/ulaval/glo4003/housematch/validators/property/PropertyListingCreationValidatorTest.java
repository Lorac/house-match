package ca.ulaval.glo4003.housematch.validators.property;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.validators.address.AddressValidationException;
import ca.ulaval.glo4003.housematch.validators.address.AddressValidator;

public class PropertyListingCreationValidatorTest {

    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.SINGLE_FAMILY_HOME;
    private static final BigDecimal SAMPLE_SELLING_PRICE = BigDecimal.valueOf(523.5);
    private static final BigDecimal SAMPLE_NEGATIVE_SELLING_PRICE = BigDecimal.valueOf(-523.5);

    private AddressValidator addressValidatorMock;
    private Address addressMock;

    private PropertyListingCreationValidator propertyListingCreationValidator;

    @Before
    public void init() throws Exception {
        addressValidatorMock = mock(AddressValidator.class);
        addressMock = mock(Address.class);
        propertyListingCreationValidator = new PropertyListingCreationValidator(addressValidatorMock);
    }

    @Test
    public void propertyListingCreationUsingValidValuesPassesValidation() throws Exception {
        try {
            propertyListingCreationValidator.validatePropertyListingCreation(SAMPLE_PROPERTY_TYPE, addressMock,
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
    public void propertyListingCreationThrowsListingCreationValidationExceptionOnAddressValidationException()
            throws Exception {
        doThrow(new AddressValidationException()).when(addressValidatorMock).validateAddress(addressMock);
        propertyListingCreationValidator.validatePropertyListingCreation(SAMPLE_PROPERTY_TYPE, addressMock,
                SAMPLE_SELLING_PRICE);
    }

    @Test(expected = PropertyListingCreationValidationException.class)
    public void propertyListingCreationWithNoPropertyTypeSpecifiedThrowsPropertyListingCreationValidationException()
            throws Exception {
        propertyListingCreationValidator.validatePropertyListingCreation(null, addressMock, SAMPLE_SELLING_PRICE);
    }

    @Test(expected = PropertyListingCreationValidationException.class)
    public void propertyListingCreationWithNoSellingPriceSpecifiedThrowsPropertyListingCreationValidationException()
            throws Exception {
        propertyListingCreationValidator.validatePropertyListingCreation(SAMPLE_PROPERTY_TYPE, addressMock, null);
    }

    @Test(expected = PropertyListingCreationValidationException.class)
    public void propertyListingCreationWithNegativeSellingPriceSpecifiedThrowsPropertyListingCreationValidationException()
            throws Exception {
        propertyListingCreationValidator.validatePropertyListingCreation(SAMPLE_PROPERTY_TYPE, addressMock,
                SAMPLE_NEGATIVE_SELLING_PRICE);
    }
}
