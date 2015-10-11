package ca.ulaval.glo4003.housematch.services.property;

import java.math.BigDecimal;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyRepository;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserPropertyNotListedException;
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

    public int createPropertyListing(PropertyType propertyType, Address address, BigDecimal sellingPrice, User user)
            throws PropertyServiceException {
        Property property = createProperty(propertyType, address, sellingPrice);
        user.addPropertyListing(property);
        userRepository.update(user);
        return property.hashCode();
    }

    public void updateProperty(int propertyHashCode, PropertyDetails details, User user)
            throws PropertyServiceException {
        try {
            Property property = propertyRepository.getByHashCode(propertyHashCode);
            property.setPropertyDetails(details);
            propertyRepository.update(property);
            user.updateProperty(property);
        } catch (PropertyNotFoundException | UserPropertyNotListedException e) {
            throw new PropertyServiceException(e);
        }
    }

    public boolean propertyBelongsToSeller(int propertyId, User user) throws PropertyServiceException {
        try {
            if (user.propertyBelongsToUser(propertyRepository.getByHashCode(propertyId))) {
                return true;
            }
        } catch (PropertyNotFoundException e) {
            throw new PropertyServiceException(e);
        }
        return false;
    }

    public void findProperty(int propertyId) throws PropertyServiceException {
        try {
            propertyRepository.getByHashCode(propertyId);
        } catch (PropertyNotFoundException e) {
            throw new PropertyServiceException(e);
        }
    }

    private Property createProperty(PropertyType propertyType, Address address, BigDecimal sellingPrice)
            throws PropertyServiceException {
        Property property;

        try {
            propertyListingCreationValidator.validatePropertyListingCreation(propertyType, address, sellingPrice);
            property = new Property(propertyType, address, sellingPrice);
            propertyRepository.persist(property);
        } catch (PropertyListingCreationValidationException | PropertyAlreadyExistsException e) {
            throw new PropertyServiceException(e);
        }

        return property;
    }
}
