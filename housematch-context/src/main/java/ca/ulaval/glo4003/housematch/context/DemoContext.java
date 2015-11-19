package ca.ulaval.glo4003.housematch.context;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import ca.ulaval.glo4003.housematch.domain.CardinalDirection;
import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.address.Region;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyFactory;
import ca.ulaval.glo4003.housematch.domain.property.PropertyOwnershipType;
import ca.ulaval.glo4003.housematch.domain.property.PropertyRepository;
import ca.ulaval.glo4003.housematch.domain.property.PropertyStyle;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.user.UserFactory;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.utils.RandomUtils;

public class DemoContext extends ContextBase {

    private static final String[] WORD_POOL = {"Red", "Lookout", "Challenge", "Emerald", "View", "Maze", "Paris", "Blue", "Falls",
            "Ireland", "Misty", "Apple", "View", "Loop", "Loop", "Rustic", "Gate", "Pysht", "Sunny", "Highway", "Wickchoupai", "Clear",
            "Creek", "Glade", "Mount", "Massive", "Lakes", "Golden", "Drive", "Mumper", "Corner", "Colonial", "Wynd", "Tomboy", "Noble",
            "Fox", "Townline", "Slickpoo", "Easy", "Glen", "Eggnog", "Green", "Park", "Cave-in-Rock", "Lazy", "Downs", "Chittyville",
            "High", "Stead", "Oblong", "Iron", "Mountain", "Turnabout", "Cranks", "Quaking", "Crescent", "Immaculata", "Pleasant",
            "By-pass", "Squealer", "Point", "Landing", "Rocky", "Common", "Wham", "Silver", "Wagon", "Impasse", "Splitlog", "Amber",
            "Plaza", "Circleback", "Umber", "Walk", "Veribest", "Stony", "Quay", "Birdland", "Dusty", "Elk", "Passage", "Chumuckla",
            "Bright", "Private", "Niceville", "Thunder", "Edge", "Tocktoethla", "Cotton", "Gate", "Woods", "Totstalahoeetska", "Crystal",
            "Branch", "Subdivision", "Mosquito", "Crossing", "Foggy", "Lagoon", "Green", "Thermopylae", "Honey", "Bear", "Farms", "Peas",
            "Eddy", "Fallen", "Anchor", "Canyon", "Perfection"};

    private static final Integer PROPERTY_POOL_SIZE = 100;
    private static final Integer DAYS_IN_YEAR = 365;

    private UserFactory userFactory;
    private UserRepository userRepository;
    private PropertyFactory propertyFactory;
    private PropertyRepository propertyRepository;

    private Random random = new Random();

    public DemoContext(final UserFactory userFactory, final UserRepository userRepository, final PropertyFactory propertyFactory,
            final PropertyRepository propertyRepository) {
        this.userFactory = userFactory;
        this.userRepository = userRepository;
        this.propertyFactory = propertyFactory;
        this.propertyRepository = propertyRepository;
    }

    // CHECKSTYLE:OFF

    @Override
    protected void applyFillers() throws Exception {
        User buyer1 = userFactory.createUser("buyer1", "buyer@gmail.com", "1234", UserRole.BUYER);
        User seller1 = userFactory.createUser("seller1", "seller@gmail.com", "1234", UserRole.SELLER);
        User seller2 = userFactory.createUser("seller2", "seller2@gmail.com", "1234", UserRole.SELLER);
        User admin1 = userFactory.createUser("admin1", "admin@gmail.com", "1234", UserRole.ADMINISTRATOR);

        List<User> userPool = new ArrayList<>();
        userPool.add(buyer1);
        userPool.add(seller1);
        userPool.add(seller2);
        userPool.add(admin1);

        activateUsers(userPool);

        List<Property> propertyPool = createPropertyPool();
        putPropertiesForSale(userPool, propertyPool);
        purchaseProperties(buyer1, propertyPool);

        persistProperties(propertyPool);
        persistUsers(userPool);
    }

    private void activateUsers(List<User> userPool) {
        for (User user : userPool) {
            user.setActivated(true);
        }
    }

    private List<Property> createPropertyPool() {
        List<Property> propertyPool = new ArrayList<>();
        for (int i = 0; i < PROPERTY_POOL_SIZE; i++) {
            propertyPool.add(createRandomProperty());
        }
        return propertyPool;
    }

    private void putPropertiesForSale(List<User> userPool, List<Property> propertyPool) {
        List<Property> propertiesForSale = new ArrayList<>(propertyPool);
        while (!propertiesForSale.isEmpty()) {
            Property property = RandomUtils.getRandomElement(propertiesForSale);
            propertiesForSale.remove(property);
            RandomUtils.getRandomElement(userPool).addPropertyForSale(property);
        }
    }

    private void purchaseProperties(User buyer1, List<Property> propertyPool) {
        for (int i = 0; i < 5; i++) {
            buyer1.purchaseProperty(propertyPool.get(i));
        }
    }

    private void persistProperties(List<Property> propertyPool) throws PropertyAlreadyExistsException {
        for (Property property : propertyPool) {
            propertyRepository.persist(property);
        }
    }

    private void persistUsers(List<User> userPool) throws UserAlreadyExistsException {
        for (User user : userPool) {
            userRepository.persist(user);
        }
    }

    private Property createRandomProperty() {
        Address address = createRandomAddress();
        Property property = propertyFactory.createProperty(RandomUtils.getRandomElement(PropertyType.values()), address,
                BigDecimal.valueOf(random.nextInt(1000000)));

        property.setCreationDate(ZonedDateTime.now().minusDays(random.nextInt(DAYS_IN_YEAR * 5)));
        property.setPropertyDetails(createRandomPropertyDetails());
        property.incrementViewCount(random.nextInt(1000));

        return property;
    }

    private Address createRandomAddress() {
        Address address = new Address();

        address.setRegion(RandomUtils.getRandomElement(Region.values()));
        address.setPostCode(createRandomPostCode());
        address.setTown(StringUtils.join(RandomUtils.getRandomElements(WORD_POOL, 1), " ") + " Town");
        address.setStreetName(StringUtils.join(RandomUtils.getRandomElements(WORD_POOL, 2), " ") + " Street");
        address.setStreetNumber(random.nextInt(9000));

        return address;
    }

    private String createRandomPostCode() {
        return String.format("%c%d%c %d%c%d", RandomUtils.getRandomAlphaChar(), random.nextInt(9), RandomUtils.getRandomAlphaChar(),
                random.nextInt(9), RandomUtils.getRandomAlphaChar(), random.nextInt(9));
    }

    private PropertyDetails createRandomPropertyDetails() {
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
