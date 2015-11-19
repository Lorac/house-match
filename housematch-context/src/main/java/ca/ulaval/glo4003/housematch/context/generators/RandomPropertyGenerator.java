package ca.ulaval.glo4003.housematch.context.generators;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Random;

import ca.ulaval.glo4003.housematch.domain.CardinalDirection;
import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyFactory;
import ca.ulaval.glo4003.housematch.domain.property.PropertyOwnershipType;
import ca.ulaval.glo4003.housematch.domain.property.PropertyStyle;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.utils.RandomUtils;

public class RandomPropertyGenerator {

    private static final Integer MAX_PROPERTY_AGE_IN_DAYS = 365 * 5;

    private PropertyFactory propertyFactory;
    private RandomAddressGenerator randomAddressgGenerator;
    private Random random = new Random();

    public RandomPropertyGenerator(final PropertyFactory propertyFactory, final RandomAddressGenerator randomAddressgGenerator) {
        this.propertyFactory = propertyFactory;
        this.randomAddressgGenerator = randomAddressgGenerator;
    }

    // CHECKSTYLE:OFF

    public Property generateRandomProperty() {
        Address address = randomAddressgGenerator.generateRandomAddress();
        Property property = propertyFactory.createProperty(RandomUtils.getRandomElement(PropertyType.values()), address,
                BigDecimal.valueOf(random.nextInt(1000000)));

        property.setCreationDate(ZonedDateTime.now().minusDays(random.nextInt(MAX_PROPERTY_AGE_IN_DAYS)));
        property.setPropertyDetails(generateRandomPropertyDetails());
        property.setViewCount(random.nextInt(1000));

        return property;
    }

    private PropertyDetails generateRandomPropertyDetails() {
        PropertyDetails propertyDetails = new PropertyDetails();

        propertyDetails.setPropertyStyle(RandomUtils.getRandomElement(PropertyStyle.values()));
        propertyDetails.setOwnershipType(RandomUtils.getRandomElement(PropertyOwnershipType.values()));
        propertyDetails.setNumberOfExteriorParkingSpaces(random.nextInt(3) + 1);
        propertyDetails.setNumberOfInteriorParkingSpaces(random.nextInt(3));
        propertyDetails.setNumberOfLevels(random.nextInt(3) + 1);
        propertyDetails.setYearOfConstruction(random.ints(50, 1900, 2015).findAny().getAsInt());
        propertyDetails.setBackyardDirection(RandomUtils.getRandomElement(CardinalDirection.values()));
        propertyDetails.setTotalNumberOfRooms(random.nextInt(4) + 3);
        propertyDetails.setNumberOfBedrooms(random.nextInt(4) + 1);
        propertyDetails.setNumberOfBathrooms(random.nextInt(3) + 1);
        propertyDetails.setBathroomDetails("Needs fixing");
        propertyDetails.setBuildingDimensionsInSquareFeet(random.ints(500, 1000, 2000).findAny().getAsInt());
        propertyDetails.setLivingSpaceAreaInSquareFeet(random.ints(500, 1, 1000).findAny().getAsInt());
        propertyDetails.setMunicipalAssessment(random.ints(500, 50000, 500000).findAny().getAsInt());

        return propertyDetails;
    }
}
