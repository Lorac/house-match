package ca.ulaval.glo4003.housematch.validators.address;

public class PartialAddressValidator extends AddressValidator {

    public PartialAddressValidator() {
        super();
        allowPartialAddress = true;
    }

}
