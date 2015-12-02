package ca.ulaval.glo4003.housematch.validators.property;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;

public class PropertyUpdateValidatorTest {

    private static final BigDecimal SAMPLE_SELLING_PRICE = BigDecimal.valueOf(523.5);
    private static final BigDecimal SAMPLE_NEGATIVE_SELLING_PRICE = BigDecimal.valueOf(-523.5);

    private PropertyDetailsValidator propertyDetailsValidatorMock;
    private PropertyDetails propertyDetailsMock;

    private PropertyUpdateValidator propertyUpdateValidator;

    @Before
    public void init() throws Exception {
        propertyDetailsValidatorMock = mock(PropertyDetailsValidator.class);
        propertyDetailsMock = mock(PropertyDetails.class);
        propertyUpdateValidator = new PropertyUpdateValidator(propertyDetailsValidatorMock);
    }

    @Test
    public void propertyUpdateUsingValidValuesPassesValidation() throws Exception {
        try {
            propertyUpdateValidator.validatePropertyUpdate(propertyDetailsMock, SAMPLE_SELLING_PRICE);
        } catch (Exception e) {
            fail();
        }
    }

    @Test(expected = PropertyUpdateValidationException.class)
    public void propertyUpdateThrowsCreationValidationExceptionOnPropertyDetailsValidationException() throws Exception {
        doThrow(new PropertyDetailsValidationException()).when(propertyDetailsValidatorMock).validatePropertyDetails(propertyDetailsMock);
        propertyUpdateValidator.validatePropertyUpdate(propertyDetailsMock, SAMPLE_SELLING_PRICE);
    }

    @Test(expected = PropertyUpdateValidationException.class)
    public void propertyUpdateWithNoSellingPriceSpecifiedThrowsPropertyUpdateValidationException() throws Exception {
        propertyUpdateValidator.validatePropertyUpdate(propertyDetailsMock, null);
    }

    @Test(expected = PropertyUpdateValidationException.class)
    public void propertyUpdateWithNegativeSellingPriceSpecifiedThrowsPropertyUpdateValidationException() throws Exception {
        propertyUpdateValidator.validatePropertyUpdate(propertyDetailsMock, SAMPLE_NEGATIVE_SELLING_PRICE);
    }
}
