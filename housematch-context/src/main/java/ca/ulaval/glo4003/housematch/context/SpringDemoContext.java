package ca.ulaval.glo4003.housematch.context;

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

import java.math.BigDecimal;
import java.util.Random;
import java.util.stream.IntStream;

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

        Address quebecAddress = createAddress("G1H 6Y7", Region.QC, "Charlesbourg", "1er Avenue", 4500);
        Address abitibiAddress = createAddress("J9X 5E5", Region.QC, "Rouyn-Noranda", "boulevard du Collège", 425);
        Address outaouaisAddress = createAddress("J0V 1R0", Region.QC, "Papineau", "rue Jeanne-d'Arc", 188);
        Address montrealAddress = createAddress("H2Y 1C6", Region.QC, "Montréal", "Rue Notre-Dame E", 275);
        Address primeMinisterAddress = createAddress("H2Y 1C6", Region.ON, "Ottawa", "Sussex Drive", 24);
        Address greatDivideLodgeAddress = createAddress("T0L 1E0", Region.BC, "Field", "Highway", 1);
        Address westEdmontonMallAddress = createAddress("T5T 4J2", Region.AB, "Edmonton", "170 St NW", 8882);
        Address cnTowerAddress = createAddress("M5V 2T6", Region.ON, "Toronto", "Front St W", 301);

        PropertyDetails quebecPropertyDetails = createRandomPropertyDetails();
        PropertyDetails abitibiPropertyDetails = createRandomPropertyDetails();
        PropertyDetails outaouaisPropertyDetails = createRandomPropertyDetails();
        PropertyDetails montrealPropertyDetails = createRandomPropertyDetails();
        PropertyDetails primeMinisterPropertyDetails = createRandomPropertyDetails();
        PropertyDetails greatDivideLodgeDetails = createRandomPropertyDetails();
        PropertyDetails westEdmontonMallDetails = createRandomPropertyDetails();
        PropertyDetails cnTowerDetails = createRandomPropertyDetails();

        Property quebecProperty = propertyFactory.createProperty(PropertyType.LOT, quebecAddress, BigDecimal.valueOf(100000));
        Property abitibiProperty = propertyFactory.createProperty(PropertyType.COTTAGE, abitibiAddress, BigDecimal.valueOf(500));
        Property outaouaisProperty = propertyFactory.createProperty(PropertyType.COMMERCIAL, outaouaisAddress, BigDecimal.valueOf(5000));
        Property montrealProperty = propertyFactory.createProperty(PropertyType.SINGLE_FAMILY_HOME, montrealAddress, BigDecimal.valueOf(350000));
        Property primeMinisterProperty = propertyFactory.createProperty(PropertyType.FARM, primeMinisterAddress, BigDecimal.valueOf(3500000));
        Property greatDivideLodgeProperty = propertyFactory.createProperty(PropertyType.COMMERCIAL, greatDivideLodgeAddress, BigDecimal.valueOf(5500000));
        Property westEdmontonMallProperty = propertyFactory.createProperty(PropertyType.COMMERCIAL, westEdmontonMallAddress, BigDecimal.valueOf(1000000000));
        Property cnTowerProperty = propertyFactory.createProperty(PropertyType.COMMERCIAL, cnTowerAddress, BigDecimal.valueOf(100000000));

        increaseViewCount(quebecProperty, 5);
        increaseViewCount(abitibiProperty, 4);
        increaseViewCount(outaouaisProperty, 3);
        increaseViewCount(montrealProperty, 2);
        increaseViewCount(primeMinisterProperty, 1);

        quebecProperty.setPropertyDetails(quebecPropertyDetails);
        abitibiProperty.setPropertyDetails(abitibiPropertyDetails);
        outaouaisProperty.setPropertyDetails(outaouaisPropertyDetails);
        montrealProperty.setPropertyDetails(montrealPropertyDetails);
        primeMinisterProperty.setPropertyDetails(primeMinisterPropertyDetails);
        greatDivideLodgeProperty.setPropertyDetails(greatDivideLodgeDetails);
        westEdmontonMallProperty.setPropertyDetails(westEdmontonMallDetails);
        cnTowerProperty.setPropertyDetails(cnTowerDetails);

        propertyRepository.persist(quebecProperty);
        propertyRepository.persist(abitibiProperty);
        propertyRepository.persist(outaouaisProperty);
        propertyRepository.persist(montrealProperty);
        propertyRepository.persist(primeMinisterProperty);
        propertyRepository.persist(greatDivideLodgeProperty);
        propertyRepository.persist(westEdmontonMallProperty);
        propertyRepository.persist(cnTowerProperty);

        seller.addPropertyForSale(quebecProperty);
        seller.addPropertyForSale(montrealProperty);
        seller2.addPropertyForSale(abitibiProperty);
        seller2.addPropertyForSale(outaouaisProperty);
        seller2.addPropertyForSale(primeMinisterProperty);
        seller2.addPropertyForSale(greatDivideLodgeProperty);
        seller2.addPropertyForSale(westEdmontonMallProperty);
        seller2.addPropertyForSale(cnTowerProperty);

        userRepository.persist(buyer);
        userRepository.persist(seller);
        userRepository.persist(seller2);
        userRepository.persist(admin);
    }
    // CHECKSTYLE:OFF

    private void increaseViewCount(Property property, int viewCount) {
        IntStream.range(0, viewCount).forEach(
                value -> property.incrementViewCount()
        );
    }


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
