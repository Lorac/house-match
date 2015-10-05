package ca.ulaval.glo4003.housematch.services.property;

import java.math.BigDecimal;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyRepository;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.domain.streetaddress.StreetAddress;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.validators.property.PropertyListingCreationValidationException;
import ca.ulaval.glo4003.housematch.validators.property.PropertyListingCreationValidator;

public class PropertyService {

    private PropertyRepository propertyRepository;
    private UserRepository userRepository;
    private PropertyListingCreationValidator propertyListingCreationValidator;

    public PropertyService(final PropertyRepository propertyRepository, final UserRepository userRepository,
            final PropertyListingCreationValidator propertyListingCreationValidator) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
        this.propertyListingCreationValidator = propertyListingCreationValidator;
    }

    public void createPropertyListing(PropertyType propertyType, StreetAddress streetAddress, BigDecimal sellingPrice,
            User user) throws PropertyServiceException {
        Property property = createProperty(propertyType, streetAddress, sellingPrice);
        user.addPropertyListing(property);
        userRepository.update(user);
    }

    private Property createProperty(PropertyType propertyType, StreetAddress streetAddress, BigDecimal sellingPrice)
            throws PropertyServiceException {
        Property property;

        try {
            propertyListingCreationValidator.validatePropertyListingCreation(propertyType, streetAddress, sellingPrice);
            property = new Property(propertyType, streetAddress, sellingPrice);
            propertyRepository.persist(property);
        } catch (PropertyListingCreationValidationException | PropertyAlreadyExistsException e) {
            throw new PropertyServiceException(e);
        }

        return property;
    }
}
