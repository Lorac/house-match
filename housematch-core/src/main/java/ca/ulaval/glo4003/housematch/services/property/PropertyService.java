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
import ca.ulaval.glo4003.housematch.validators.property.PropertyCreationValidationException;
import ca.ulaval.glo4003.housematch.validators.property.PropertyCreationValidator;
import ca.ulaval.glo4003.housematch.validators.property.PropertyDetailsValidationException;
import ca.ulaval.glo4003.housematch.validators.property.PropertyDetailsValidator;

public class PropertyService {

    private PropertyRepository propertyRepository;
    private UserRepository userRepository;
    private PropertyCreationValidator propertyCreationValidator;
    private PropertyDetailsValidator propertyDetailsValidator;

    public PropertyService(final PropertyRepository propertyRepository, final UserRepository userRepository,
            final PropertyCreationValidator propertyCreationValidator,
            final PropertyDetailsValidator propertyDetailsValidator) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
        this.propertyCreationValidator = propertyCreationValidator;
        this.propertyDetailsValidator = propertyDetailsValidator;
    }

    public Property createProperty(PropertyType propertyType, Address address, BigDecimal sellingPrice,
            User user) throws PropertyServiceException {
        try {
            propertyCreationValidator.validatePropertyCreation(propertyType, address, sellingPrice);
            Property property = new Property(propertyType, address, sellingPrice);
            user.addProperty(property);
            userRepository.update(user);
            propertyRepository.persist(property);
            return property;
        } catch (PropertyCreationValidationException | PropertyAlreadyExistsException e) {
            throw new PropertyServiceException(e);
        }
    }

    public void updateProperty(Property property, PropertyDetails propertyDetails) throws PropertyServiceException {
        try {
            propertyDetailsValidator.validatePropertyDetails(propertyDetails);
            property.setPropertyDetails(propertyDetails);
            propertyRepository.update(property);
        } catch (PropertyDetailsValidationException e) {
            throw new PropertyServiceException(e);
        }
    }
}
