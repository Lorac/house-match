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
        validateAddressNumber(address);
        validateStreetName(address);
        validateCity(address);
        validateZipCode(address);
        validateRegion(address);
        validateCity(address);
    }

    private void validateAddressNumber(Address address) throws AddressValidationException {
        if (address.getAddressNumber() == null) {
            if (!allowPartialAddress) {
                throw new AddressValidationException("Address number must be specified.");
            }
        } else if (address.getAddressNumber() <= 0) {
            throw new AddressValidationException("Address number must be greater than 0.");
        }
    }

    private void validateStreetName(Address address) throws AddressValidationException {
        if (StringUtils.isBlank(address.getStreetName()) && !allowPartialAddress) {
            throw new AddressValidationException("Street name cannot be blank.");
        }
    }

    private void validateCity(Address address) throws AddressValidationException {
        if (StringUtils.isBlank(address.getCity()) && !allowPartialAddress) {
            throw new AddressValidationException("City cannot be blank.");
        }
    }

    private void validateZipCode(Address address) throws AddressValidationException {
        if (StringUtils.isBlank(address.getPostCode())) {
            if (!allowPartialAddress) {
                throw new AddressValidationException("Zip code cannot be blank.");
            }
        } else if (!postCodeValidationRegExPattern.matcher(address.getPostCode()).matches()) {
            throw new AddressValidationException("Zip code must be in the 'A8A 8A8' format.");
        }
    }

    private void validateRegion(Address address) throws AddressValidationException {
        if (address.getRegion() == null && !allowPartialAddress) {
            throw new AddressValidationException("State or province must be specified.");
        }
    }
}
