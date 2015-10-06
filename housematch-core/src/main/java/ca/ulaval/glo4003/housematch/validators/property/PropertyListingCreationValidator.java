package ca.ulaval.glo4003.housematch.validators.property;

import java.math.BigDecimal;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.validators.address.AddressValidationException;
import ca.ulaval.glo4003.housematch.validators.address.AddressValidator;

public class PropertyListingCreationValidator {

    private AddressValidator addressValidator;

    public PropertyListingCreationValidator(final AddressValidator addressValidator) {
        this.addressValidator = addressValidator;
    }

    public void validatePropertyListingCreation(PropertyType propertyType, Address address, BigDecimal sellingPrice)
            throws PropertyListingCreationValidationException {

        if (address == null) {
            throw new PropertyListingCreationValidationException("Address must be specified.");
        } else if (propertyType == null) {
            throw new PropertyListingCreationValidationException("Property type must be specified.");
        } else if (sellingPrice == null) {
            throw new PropertyListingCreationValidationException("Selling price must be specified.");
        } else if (sellingPrice.signum() < 0) {
            throw new PropertyListingCreationValidationException("Selling price must be a positive number.");
        }

        validateAddress(address);
    }

    private void validateAddress(Address address) throws PropertyListingCreationValidationException {
        try {
            addressValidator.validateAddress(address);
        } catch (AddressValidationException e) {
            throw new PropertyListingCreationValidationException(e.getMessage(), e);
        }
    }
}
