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

public class PropertyCreationValidatorTest {

    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.SINGLE_FAMILY_HOME;
    private static final BigDecimal SAMPLE_SELLING_PRICE = BigDecimal.valueOf(523.5);
    private static final BigDecimal SAMPLE_NEGATIVE_SELLING_PRICE = BigDecimal.valueOf(-523.5);

    private AddressValidator addressValidatorMock;
    private Address addressMock;

    private PropertyCreationValidator propertyCreationValidator;

    @Before
    public void init() throws Exception {
        addressValidatorMock = mock(AddressValidator.class);
        addressMock = mock(Address.class);
        propertyCreationValidator = new PropertyCreationValidator(addressValidatorMock);
    }

    @Test
    public void propertyCreationUsingValidValuesPassesValidation() throws Exception {
        try {
            propertyCreationValidator.validatePropertyCreation(SAMPLE_PROPERTY_TYPE, addressMock, SAMPLE_SELLING_PRICE);
        } catch (Exception e) {
            fail();
        }
    }

    @Test(expected = PropertyCreationValidationException.class)
    public void propertyCreationWithNoSteetAddressSpecifiedThrowsPropertyCreationValidationException()
            throws Exception {
        propertyCreationValidator.validatePropertyCreation(SAMPLE_PROPERTY_TYPE, null, SAMPLE_SELLING_PRICE);
    }

    @Test(expected = PropertyCreationValidationException.class)
    public void propertyCreationThrowsCreationValidationExceptionOnAddressValidationException() throws Exception {
        doThrow(new AddressValidationException()).when(addressValidatorMock).validateAddress(addressMock);
        propertyCreationValidator.validatePropertyCreation(SAMPLE_PROPERTY_TYPE, addressMock, SAMPLE_SELLING_PRICE);
    }

    @Test(expected = PropertyCreationValidationException.class)
    public void propertyCreationWithNoPropertyTypeSpecifiedThrowsPropertyCreationValidationException()
            throws Exception {
        propertyCreationValidator.validatePropertyCreation(null, addressMock, SAMPLE_SELLING_PRICE);
    }

    @Test(expected = PropertyCreationValidationException.class)
    public void propertyCreationWithNoSellingPriceSpecifiedThrowsPropertyCreationValidationException()
            throws Exception {
        propertyCreationValidator.validatePropertyCreation(SAMPLE_PROPERTY_TYPE, addressMock, null);
    }

    @Test(expected = PropertyCreationValidationException.class)
    public void propertyCreationWithNegativeSellingPriceSpecifiedThrowsPropertyCreationValidationException()
            throws Exception {
        propertyCreationValidator.validatePropertyCreation(SAMPLE_PROPERTY_TYPE, addressMock,
                SAMPLE_NEGATIVE_SELLING_PRICE);
    }
}
