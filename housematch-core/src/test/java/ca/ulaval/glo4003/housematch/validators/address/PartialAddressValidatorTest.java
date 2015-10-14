package ca.ulaval.glo4003.housematch.validators.address;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.address.Address;

public class PartialAddressValidatorTest {

    private Address addressMock;
    private PartialAddressValidator partialAddressValidator;

    @Before
    public void init() throws Exception {
        addressMock = mock(Address.class);
        partialAddressValidator = new PartialAddressValidator();
        when(addressMock.getStreetNumber()).thenReturn(null);
    }

    @Test
    public void addressValidationUsingBlankValuesPassesValidation() throws Exception {
        try {
            partialAddressValidator.validateAddress(addressMock);
        } catch (Exception e) {
            fail();
        }
    }
}
