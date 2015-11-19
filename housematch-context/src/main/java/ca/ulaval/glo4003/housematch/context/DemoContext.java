package ca.ulaval.glo4003.housematch.context;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4003.housematch.context.generators.RandomPropertyGenerator;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyRepository;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.user.UserFactory;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.utils.RandomUtils;

public class DemoContext extends ContextBase {
    private static final Integer PROPERTY_POOL_SIZE = 100;
    private static final Integer NUMBER_OF_PROPERTY_SALES = 7;

    private UserFactory userFactory;
    private UserRepository userRepository;
    private RandomPropertyGenerator randomPropertyGenerator;
    private PropertyRepository propertyRepository;

    public DemoContext(final UserFactory userFactory, final UserRepository userRepository,
            final RandomPropertyGenerator randomPropertyGenerator, final PropertyRepository propertyRepository) {
        this.userFactory = userFactory;
        this.userRepository = userRepository;
        this.randomPropertyGenerator = randomPropertyGenerator;
        this.propertyRepository = propertyRepository;
    }

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
            propertyPool.add(randomPropertyGenerator.generateRandomProperty());
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
        for (int i = 0; i < NUMBER_OF_PROPERTY_SALES; i++) {
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
}
