package ca.ulaval.glo4003.housematch.services.property;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4003.housematch.domain.SortOrder;
import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyFactory;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyRepository;
import ca.ulaval.glo4003.housematch.domain.property.PropertySortColumn;
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

    public Property getPropertyByHashCode(int propertyHashCode) throws PropertyNotFoundException {
        return propertyRepository.getByHashCode(propertyHashCode);
    }

    public void incrementPropertyViewCount(Property property) {
        property.incrementViewCount();
        propertyRepository.update(property);
    }

    public List<Property> getProperties(PropertySortColumn sortColumn, SortOrder sortOrder) {
        List<Property> properties = propertyRepository.getAll();
        return propertySorter.sort(properties, sortColumn, sortOrder);
    }

    public List<Property> getMostPopularProperties(PropertyType propertyType, Integer limit) {
        List<Property> properties = propertyRepository.getByType(propertyType);
        propertySorter.sort(properties, PropertySortColumn.VIEW_COUNT, SortOrder.DESCENDING);
        return properties.stream().limit(limit).collect(Collectors.toList());
    }
}
