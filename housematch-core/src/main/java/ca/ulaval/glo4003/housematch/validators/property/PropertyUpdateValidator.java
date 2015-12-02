package ca.ulaval.glo4003.housematch.validators.property;

import java.math.BigDecimal;

import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;

public class PropertyUpdateValidator {

    private PropertyDetailsValidator propertyDetailsValidator;

    public PropertyUpdateValidator(final PropertyDetailsValidator propertyDetailsValidator) {
        this.propertyDetailsValidator = propertyDetailsValidator;
    }

    public void validatePropertyUpdate(PropertyDetails propertyDetails, BigDecimal sellingPrice) throws PropertyUpdateValidationException {

        if (sellingPrice == null) {
            throw new PropertyUpdateValidationException("Selling price must be specified.");
        } else if (sellingPrice.signum() < 0) {
            throw new PropertyUpdateValidationException("Selling price must be a positive number.");
        }

        validatePropertyDetails(propertyDetails);
    }

    private void validatePropertyDetails(PropertyDetails propertyDetails) throws PropertyUpdateValidationException {
        try {
            propertyDetailsValidator.validatePropertyDetails(propertyDetails);
        } catch (PropertyDetailsValidationException e) {
            throw new PropertyUpdateValidationException(e.getMessage(), e);
        }
    }
}
