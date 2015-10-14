package ca.ulaval.glo4003.housematch.validators.address;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import ca.ulaval.glo4003.housematch.domain.address.Address;

public class AddressValidator {

    private static final String POST_CODE_VALIDATION_REGEX = "^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$";
    private Pattern postCodeValidationRegExPattern;
    protected Boolean allowPartialAddress = false;

    public AddressValidator() {
        postCodeValidationRegExPattern = Pattern.compile(POST_CODE_VALIDATION_REGEX, Pattern.CASE_INSENSITIVE);
    }

    public void validateAddress(Address address) throws AddressValidationException {
        validateStreetNumber(address);
        validateStreetName(address);
        validateTown(address);
        validateZipCode(address);
        validateRegion(address);
    }

    private void validateStreetNumber(Address address) throws AddressValidationException {
        if (address.getStreetNumber() == null) {
            if (!allowPartialAddress) {
                throw new AddressValidationException("Street number must be specified.");
            }
        } else if (address.getStreetNumber() <= 0) {
            throw new AddressValidationException("Street number must be greater than 0.");
        }
    }

    private void validateStreetName(Address address) throws AddressValidationException {
        if (StringUtils.isBlank(address.getStreetName()) && !allowPartialAddress) {
            throw new AddressValidationException("Street name cannot be blank.");
        }
    }

    private void validateTown(Address address) throws AddressValidationException {
        if (StringUtils.isBlank(address.getTown()) && !allowPartialAddress) {
            throw new AddressValidationException("City / Town cannot be blank.");
        }
    }

    private void validateZipCode(Address address) throws AddressValidationException {
        if (StringUtils.isBlank(address.getPostCode())) {
            if (!allowPartialAddress) {
                throw new AddressValidationException("Zip / Postal code cannot be blank.");
            }
        } else if (!postCodeValidationRegExPattern.matcher(address.getPostCode()).matches()) {
            throw new AddressValidationException("Zip / Postal code must be in the 'A8A 8A8' format.");
        }
    }

    private void validateRegion(Address address) throws AddressValidationException {
        if (address.getRegion() == null && !allowPartialAddress) {
            throw new AddressValidationException("State / Province must be specified.");
        }
    }
}
