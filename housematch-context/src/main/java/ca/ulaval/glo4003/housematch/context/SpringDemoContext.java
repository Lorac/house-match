package ca.ulaval.glo4003.housematch.context;

import ca.ulaval.glo4003.housematch.domain.CardinalDirection;
import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.address.Region;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyFactory;
import ca.ulaval.glo4003.housematch.domain.property.PropertyRepository;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserFactory;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;

import java.math.BigDecimal;


public class SpringDemoContext extends ContextBase {
    private UserFactory userFactory;
    private UserRepository userRepository;
    private PropertyFactory propertyFactory;
    private PropertyRepository propertyRepository;

    public SpringDemoContext(final UserFactory userFactory, final UserRepository userRepository,
                             final PropertyFactory propertyFactory, final PropertyRepository propertyRepository) {
        this.userFactory = userFactory;
        this.userRepository = userRepository;
        this.propertyFactory = propertyFactory;
        this.propertyRepository = propertyRepository;
    }

    //CHECKSTYLE:OFF
    @Override
    protected void applyFillers() throws Exception {
        User buyer = userFactory.createUser("buyer", "Doe", "123", UserRole.BUYER);
        User seller = userFactory.createUser("seller", "Doe", "123", UserRole.SELLER);
        User seller2 = userFactory.createUser("seller2", "Doe", "123", UserRole.SELLER);
        User admin = userFactory.createUser("admin", "Doe", "123", UserRole.ADMINISTRATOR);

        Address quebec = new Address();
        quebec.setRegion(Region.QC);
        quebec.setPostCode("G1H6Y7");
        quebec.setTown("Charlesbourg");
        quebec.setStreetName("1er Avenue");
        quebec.setStreetNumber(4500);

        PropertyDetails quebecPropertyDetails = new PropertyDetails();
        quebecPropertyDetails.setBackyardDirection(CardinalDirection.EAST);
        quebecPropertyDetails.setYearOfConstruction(1952);

        Address abitibi = new Address();
        abitibi.setRegion(Region.QC);
        abitibi.setPostCode("J9X5E5");
        abitibi.setTown("Rouyn-Noranda");
        abitibi.setStreetName("boulevard du Collège");
        abitibi.setStreetNumber(425);

        PropertyDetails abitibiPropertyDetails = new PropertyDetails();
        abitibiPropertyDetails.setNumberOfExteriorParkingSpaces(1);
        abitibiPropertyDetails.setYearOfConstruction(1850);
        abitibiPropertyDetails.setBackyardDirection(CardinalDirection.WEST);
        abitibiPropertyDetails.setNumberOfBedrooms(1);
        abitibiPropertyDetails.setNumberOfBathrooms(1);
        abitibiPropertyDetails.setBathroomDetails("Needs fixing");
        abitibiPropertyDetails.setBuildingDimensionsInSquareFeet(1200);
        abitibiPropertyDetails.setFloorNumber(1);
        abitibiPropertyDetails.setLivingSpaceAreaInSquareFeet(800);
        abitibiPropertyDetails.setNumberOfExteriorParkingSpaces(1);
        abitibiPropertyDetails.setMunicipalAssessment(200);

        Property quebecProprety = propertyFactory.createProperty(PropertyType.LOT, quebec, BigDecimal.valueOf(100000));
        Property abitibiProperty = propertyFactory.createProperty(PropertyType.COTTAGE, abitibi, BigDecimal.valueOf(500));

        abitibiProperty.setPropertyDetails(abitibiPropertyDetails);
        quebecProprety.setPropertyDetails(quebecPropertyDetails);

        propertyRepository.persist(quebecProprety);
        propertyRepository.persist(abitibiProperty);

        seller.addPropertyForSale(quebecProprety);
        seller2.addPropertyForSale(abitibiProperty);
        seller.setActivated(true);
        seller2.setActivated(true);
        buyer.setActivated(true);
        admin.setActivated(true);
        userRepository.persist(buyer);
        userRepository.persist(seller);
        userRepository.persist(seller2);
        userRepository.persist(admin);
    }
    //CHECKSTYLE:OFF

    @Override
    protected void registerServices() {

    }


}
