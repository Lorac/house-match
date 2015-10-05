package ca.ulaval.glo4003.housematch.validators.property;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.domain.streetaddress.StreetAddress;

public class PropertyListingCreationValidator {

    private static final String ZIP_CODE_VALIDATION_REGEX = "^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$";
    private Pattern zipCodeValidationRegExPattern;

    public PropertyListingCreationValidator() {
        zipCodeValidationRegExPattern = Pattern.compile(ZIP_CODE_VALIDATION_REGEX, Pattern.CASE_INSENSITIVE);
    }

    public void validatePropertyListingCreation(PropertyType propertyType, StreetAddress streetAddress,
            BigDecimal sellingPrice) throws PropertyListingCreationValidationException {

        validateStreetAddress(streetAddress);

        if (propertyType == null) {
            throw new PropertyListingCreationValidationException("Property type must be specified.");
        } else if (sellingPrice == null) {
            throw new PropertyListingCreationValidationException("Selling price must be specified.");
        } else if (sellingPrice.signum() < 0) {
            throw new PropertyListingCreationValidationException("Selling price must be a positive number.");
        }
    }

    private void validateStreetAddress(StreetAddress streetAddress) throws PropertyListingCreationValidationException {
        if (streetAddress == null) {
            throw new PropertyListingCreationValidationException("Street address must be specified.");
        } else if (streetAddress.getAddressNumber() == null) {
            throw new PropertyListingCreationValidationException("Address number must be specified.");
        } else if (streetAddress.getAddressNumber() <= 0) {
            throw new PropertyListingCreationValidationException("Address number must be greater than 0.");
        } else if (StringUtils.isBlank(streetAddress.getStreetName())) {
            throw new PropertyListingCreationValidationException("Street name cannot be blank.");
        } else if (StringUtils.isBlank(streetAddress.getZipCode())) {
            throw new PropertyListingCreationValidationException("Zip code cannot be blank.");
        } else if (!zipCodeValidationRegExPattern.matcher(streetAddress.getZipCode()).matches()) {
            throw new PropertyListingCreationValidationException("Zip code must be in the 'A8A 8A8' format.");
        } else if (streetAddress.getState() == null) {
            throw new PropertyListingCreationValidationException("State must be specified.");
        }
    }
}
