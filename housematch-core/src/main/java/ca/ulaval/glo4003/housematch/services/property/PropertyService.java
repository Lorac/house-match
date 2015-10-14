package ca.ulaval.glo4003.housematch.services.property;

import java.math.BigDecimal;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyRepository;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.validators.property.PropertyListingCreationValidationException;
import ca.ulaval.glo4003.housematch.validators.property.PropertyListingCreationValidator;
import ca.ulaval.glo4003.housematch.validators.property.PropertyListingUpdateValidationException;
import ca.ulaval.glo4003.housematch.validators.property.PropertyListingUpdateValidator;

public class PropertyService {

    private PropertyRepository propertyRepository;
    private UserRepository userRepository;
    private PropertyListingCreationValidator propertyListingCreationValidator;
    private PropertyListingUpdateValidator propertyListingUpdateValidator;

    public PropertyService(final PropertyRepository propertyRepository, final UserRepository userRepository,
            final PropertyListingCreationValidator propertyListingCreationValidator,
            final PropertyListingUpdateValidator propertyListingUpdateValidator) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
        this.propertyListingCreationValidator = propertyListingCreationValidator;
        this.propertyListingUpdateValidator = propertyListingUpdateValidator;
    }

    public Property createPropertyListing(PropertyType propertyType, Address address, BigDecimal sellingPrice,
            User user) throws PropertyServiceException {
        try {
            propertyListingCreationValidator.validatePropertyListingCreation(propertyType, address, sellingPrice);
            Property property = createProperty(propertyType, address, sellingPrice);
            user.addPropertyListing(property);
            userRepository.update(user);
            return property;
        } catch (PropertyListingCreationValidationException e) {
            throw new PropertyServiceException(e);
        }
    }

    private Property createProperty(PropertyType propertyType, Address address, BigDecimal sellingPrice)
            throws PropertyServiceException {
        try {
            Property property = new Property(propertyType, address, sellingPrice);
            propertyRepository.persist(property);
            return property;
        } catch (PropertyAlreadyExistsException e) {
            throw new PropertyServiceException(e);
        }
    }

    public void updateProperty(Property property, PropertyDetails propertyDetails) throws PropertyServiceException {
        try {
            propertyListingUpdateValidator.validatePropertyListingUpdate(propertyDetails);
            property.setPropertyDetails(propertyDetails);
            propertyRepository.update(property);
        } catch (PropertyListingUpdateValidationException e) {
            throw new PropertyServiceException(e);
        }
    }
}
