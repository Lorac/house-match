package ca.ulaval.glo4003.housematch.context;

import java.math.BigDecimal;
import java.util.Random;

import ca.ulaval.glo4003.housematch.domain.CardinalDirection;
import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.address.Region;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyFactory;
import ca.ulaval.glo4003.housematch.domain.property.PropertyOwnershipType;
import ca.ulaval.glo4003.housematch.domain.property.PropertyRepository;
import ca.ulaval.glo4003.housematch.domain.property.PropertyStyle;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserFactory;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;

public class SpringDemoContext extends ContextBase {
    private UserFactory userFactory;
    private UserRepository userRepository;
    private PropertyFactory propertyFactory;
    private PropertyRepository propertyRepository;

    public SpringDemoContext(final UserFactory userFactory, final UserRepository userRepository, final PropertyFactory propertyFactory,
            final PropertyRepository propertyRepository) {
        this.userFactory = userFactory;
        this.userRepository = userRepository;
        this.propertyFactory = propertyFactory;
        this.propertyRepository = propertyRepository;
    }

    // CHECKSTYLE:OFF
    @Override
    protected void applyFillers() throws Exception {
        User buyer = userFactory.createUser("buyer", "buyer@gmail.com", "123", UserRole.BUYER);
        User seller = userFactory.createUser("seller", "seller@gmail.com", "123", UserRole.SELLER);
        User seller2 = userFactory.createUser("seller2", "seller2@gmail.com", "123", UserRole.SELLER);
        User admin = userFactory.createUser("admin", "admin@gmail.com", "123", UserRole.ADMINISTRATOR);

        buyer.setActivated(true);
        seller.setActivated(true);
        seller2.setActivated(true);
        admin.setActivated(true);

        Address quebecAddress = createAddress("G1H6Y7", Region.QC, "Charlesbourg", "1er Avenue", 4500);
        Address abitibiAddress = createAddress("J9X5E5", Region.QC, "Rouyn-Noranda", "boulevard du Collège", 425);
        Address outaouaisAddress = createAddress("J0V 1R0", Region.QC, "Papineau", "rue Jeanne-d'Arc", 188);

        PropertyDetails quebecPropertyDetails = createRandomPropertyDetails();
        PropertyDetails abitibiPropertyDetails = createRandomPropertyDetails();
        PropertyDetails outaouaisPropertyDetails = createRandomPropertyDetails();

        Property quebecProperty = propertyFactory.createProperty(PropertyType.LOT, quebecAddress, BigDecimal.valueOf(100000));
        Property abitibiProperty = propertyFactory.createProperty(PropertyType.COTTAGE, abitibiAddress, BigDecimal.valueOf(500));
        Property outaouaisProperty = propertyFactory.createProperty(PropertyType.COMMERCIAL, outaouaisAddress, BigDecimal.valueOf(5000));

        quebecProperty.setPropertyDetails(quebecPropertyDetails);
        abitibiProperty.setPropertyDetails(abitibiPropertyDetails);
        outaouaisProperty.setPropertyDetails(outaouaisPropertyDetails);

        propertyRepository.persist(quebecProperty);
        propertyRepository.persist(abitibiProperty);
        propertyRepository.persist(outaouaisProperty);

        seller.addPropertyForSale(quebecProperty);
        seller2.addPropertyForSale(abitibiProperty);
        seller2.addPropertyForSale(outaouaisProperty);

        userRepository.persist(buyer);
        userRepository.persist(seller);
        userRepository.persist(seller2);
        userRepository.persist(admin);
    }
    // CHECKSTYLE:OFF

    private PropertyDetails createRandomPropertyDetails() {
        PropertyDetails propertyDetails = new PropertyDetails();
        Random random = new Random();
        propertyDetails.setPropertyStyle(PropertyStyle.values()[random.nextInt(PropertyStyle.values().length)]);
        propertyDetails.setOwnershipType(PropertyOwnershipType.values()[random.nextInt(PropertyOwnershipType.values().length)]);
        propertyDetails.setNumberOfExteriorParkingSpaces(random.nextInt(3) + 1);
        propertyDetails.setNumberOfInteriorParkingSpaces(random.nextInt(3));
        propertyDetails.setNumberOfLevels(random.nextInt(3) + 1);
        propertyDetails.setYearOfConstruction(random.ints(50, 1900, 2015).findAny().getAsInt());


        propertyDetails.setBackyardDirection(CardinalDirection.values()[random.nextInt(CardinalDirection.values().length)]);
        propertyDetails.setTotalNumberOfRooms(random.nextInt(4) + 3);
        propertyDetails.setNumberOfBedrooms(random.nextInt(4) + 1);
        propertyDetails.setNumberOfBathrooms(random.nextInt(3) + 1);
        propertyDetails.setBathroomDetails("Needs fixing");
        propertyDetails.setBuildingDimensionsInSquareFeet(random.ints(500, 1000, 2000).findAny().getAsInt());
        propertyDetails.setLivingSpaceAreaInSquareFeet(random.ints(500, 1, 1000).findAny().getAsInt());
        propertyDetails.setMunicipalAssessment(random.ints(500, 50000, 500000).findAny().getAsInt());

        return propertyDetails;
    }

    private Address createAddress(String postCode, Region region, String town, String streetName, int streetNumber) {
        Address address = new Address();
        address.setRegion(region);
        address.setPostCode(postCode);
        address.setTown(town);
        address.setStreetName(streetName);
        address.setStreetNumber(streetNumber);
        return address;
    }
}
