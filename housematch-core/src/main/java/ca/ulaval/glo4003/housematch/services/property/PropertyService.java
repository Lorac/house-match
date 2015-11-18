package ca.ulaval.glo4003.housematch.services.property;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyFactory;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyRepository;
import ca.ulaval.glo4003.housematch.domain.property.PropertySorter;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
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
    private PropertySorter propertySorter;

    public PropertyService(final PropertyFactory propertyFactory, final PropertyRepository propertyRepository,
            final UserRepository userRepository, final PropertyCreationValidator propertyCreationValidator,
            final PropertyDetailsValidator propertyDetailsValidator, final PropertySorter propertySorter) {
        this.propertyFactory = propertyFactory;
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
        this.propertyCreationValidator = propertyCreationValidator;
        this.propertyDetailsValidator = propertyDetailsValidator;
        this.propertySorter = propertySorter;
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

    public void incrementViewCountOfProperty(Property property) {
        property.incrementViewCount();
        propertyRepository.update(property);
    }

    public List<Property> getProperties() {
        return propertyRepository.getAll();
    }

    public Property getPropertyByHashCode(int propertyHashCode) throws PropertyNotFoundException {
        return propertyRepository.getByHashCode(propertyHashCode);
    }

    public List<Property> getMostViewedPropertiesForCategory(int limit, PropertyType propertyType) {
        List<Property> all = propertyRepository.getAll();
        propertySorter.sortByHighestViewCount(all);

        Predicate<Property> propertyPredicate = p -> p.getPropertyType().equals(propertyType);
        List<Property> properties = all.stream().filter(propertyPredicate).limit(limit).collect(Collectors.toList());

        updateMostPopularProperties(all, properties);

        return properties;
    }

    private void updateMostPopularProperties(List<Property> allProperties, List<Property> mostViewedProperties) {
        allProperties.stream().filter(p -> !mostViewedProperties.contains(p)).forEach(p -> p.setMostPopular(false));
        allProperties.stream().filter(mostViewedProperties::contains).forEach(p -> p.setMostPopular(true));
        allProperties.stream().forEach(property -> propertyRepository.update(property));
    }
}
