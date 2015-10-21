package ca.ulaval.glo4003.housematch.validators.property;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.validators.address.AddressValidationException;
import ca.ulaval.glo4003.housematch.validators.address.AddressValidator;

import java.math.BigDecimal;

public class PropertyCreationValidator {

    private AddressValidator addressValidator;

    public PropertyCreationValidator(final AddressValidator addressValidator) {
        this.addressValidator = addressValidator;
    }

    public void validatePropertyCreation(PropertyType propertyType, Address address, BigDecimal sellingPrice)
            throws PropertyCreationValidationException {

        if (address == null) {
            throw new PropertyCreationValidationException("Address must be specified.");
        } else if (propertyType == null) {
            throw new PropertyCreationValidationException("Property type must be specified.");
        } else if (sellingPrice == null) {
            throw new PropertyCreationValidationException("Selling price must be specified.");
        } else if (sellingPrice.signum() < 0) {
            throw new PropertyCreationValidationException("Selling price must be a positive number.");
        }

        validateAddress(address);
    }

    private void validateAddress(Address address) throws PropertyCreationValidationException {
        try {
            addressValidator.validateAddress(address);
        } catch (AddressValidationException e) {
            throw new PropertyCreationValidationException(e.getMessage(), e);
        }
    }
}
