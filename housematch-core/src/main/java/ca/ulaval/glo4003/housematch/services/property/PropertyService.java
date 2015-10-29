package ca.ulaval.glo4003.housematch.services.property;

import java.math.BigDecimal;
import java.util.List;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.*;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.validators.property.PropertyCreationValidationException;
import ca.ulaval.glo4003.housematch.validators.property.PropertyCreationValidator;
import ca.ulaval.glo4003.housematch.validators.property.PropertyDetailsValidationException;
import ca.ulaval.glo4003.housematch.validators.property.PropertyDetailsValidator;

public class PropertyService {

    private PropertyFactory propertyFactory;
    private PropertyRepository propertyRepository;
    private UserRepository userRepository;
    private PropertyCreationValidator propertyCreationValidator;
    private PropertyDetailsValidator propertyDetailsValidator;
    private PropertiesFilter propertiesFilter;

    public PropertyService(final PropertyFactory propertyFactory, final PropertyRepository propertyRepository,
            final UserRepository userRepository, final PropertyCreationValidator propertyCreationValidator,
            final PropertyDetailsValidator propertyDetailsValidator, final PropertiesFilter propertiesFilter) {
        this.propertyFactory = propertyFactory;
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
        this.propertyCreationValidator = propertyCreationValidator;
        this.propertyDetailsValidator = propertyDetailsValidator;
        this.propertiesFilter = propertiesFilter;
    }

    public Property createProperty(PropertyType propertyType, Address address, BigDecimal sellingPrice, User user)
            throws PropertyServiceException {
        try {
            propertyCreationValidator.validatePropertyCreation(propertyType, address, sellingPrice);
            Property property = propertyFactory.createProperty(propertyType, address, sellingPrice);
            propertyRepository.persist(property);
            user.addPropertyForSale(property);
            userRepository.update(user);
            return property;
        } catch (PropertyCreationValidationException | PropertyAlreadyExistsException e) {
            throw new PropertyServiceException(e);
        }
    }

    public void updatePropertyDetails(Property property, PropertyDetails propertyDetails) throws PropertyServiceException {
        try {
            propertyDetailsValidator.validatePropertyDetails(propertyDetails);
            property.setPropertyDetails(propertyDetails);
            propertyRepository.update(property);
        } catch (PropertyDetailsValidationException e) {
            throw new PropertyServiceException(e);
        }
    }

    public List<Property> getProperties() {
        return propertyRepository.getAll();
    }

    public Property getPropertyByHashCode(int propertyHashCode) throws PropertyNotFoundException {
        return propertyRepository.getByHashCode(propertyHashCode);
    }

    public List<Property> getPropertiesInChronologicalOrder() {
        List<Property> properties = propertyRepository.getAll();
        propertiesFilter.orderByAscendingDates(properties);

        return properties;
    }

    public List<Property> getPropertiesInReverseChronologicalOrder() {
        List<Property> properties = propertyRepository.getAll();
        propertiesFilter.orderByDescendingDates(properties);

        return properties;
    }
}
