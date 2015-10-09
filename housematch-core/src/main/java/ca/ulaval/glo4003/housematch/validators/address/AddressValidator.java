package ca.ulaval.glo4003.housematch.validators.address;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import ca.ulaval.glo4003.housematch.domain.address.Address;

public class AddressValidator {

    private static final String POST_CODE_VALIDATION_REGEX = "^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$";
    private Pattern postCodeValidationRegExPattern;

    public AddressValidator() {
        postCodeValidationRegExPattern = Pattern.compile(POST_CODE_VALIDATION_REGEX, Pattern.CASE_INSENSITIVE);
    }

    public void validateAddress(Address address) throws AddressValidationException {
        validateAddressNumber(address);
        validateStreetName(address);
        validateZipCode(address);
        validateRegion(address);
        validateCity(address);
    }

    private void validateCity(Address address) throws AddressValidationException {
        if (StringUtils.isBlank(address.getCity())) {
            throw new AddressValidationException("City must be specified.");
        }
    }

    private void validateRegion(Address address) throws AddressValidationException {
        if (address.getRegion() == null) {
            throw new AddressValidationException("State or province must be specified.");
        }
    }

    private void validateZipCode(Address address) throws AddressValidationException {
        if (StringUtils.isBlank(address.getPostCode())) {
            throw new AddressValidationException("Zip code cannot be blank.");
        } else if (!postCodeValidationRegExPattern.matcher(address.getPostCode()).matches()) {
            throw new AddressValidationException("Zip code must be in the 'A8A 8A8' format.");
        }
    }

    private void validateStreetName(Address address) throws AddressValidationException {
        if (StringUtils.isBlank(address.getStreetName())) {
            throw new AddressValidationException("Street name cannot be blank.");
        }
    }

    private void validateAddressNumber(Address address) throws AddressValidationException {
        if (address.getAddressNumber() == null) {
            throw new AddressValidationException("Address number must be specified.");
        } else if (address.getAddressNumber() <= 0) {
            throw new AddressValidationException("Address number must be greater than 0.");
        }
    }
}
