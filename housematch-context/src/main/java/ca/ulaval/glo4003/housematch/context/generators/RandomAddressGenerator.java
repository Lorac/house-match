package ca.ulaval.glo4003.housematch.context.generators;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.address.Region;
import ca.ulaval.glo4003.housematch.utils.RandomUtils;

public class RandomAddressGenerator {

    private Random random = new Random();

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

    // CHECKSTYLE:OFF

    public Address generateRandomAddress() {
        Address address = new Address();

        address.setRegion(RandomUtils.getRandomElement(Region.values()));
        address.setPostCode(generateRandomPostCode());
        address.setTown(StringUtils.join(RandomUtils.getRandomElements(WORD_POOL, 1), " ") + " Town");
        address.setStreetName(StringUtils.join(RandomUtils.getRandomElements(WORD_POOL, 2), " ") + " Street");
        address.setStreetNumber(random.nextInt(9000));

        return address;
    }

    private String generateRandomPostCode() {
        return String.format("%c%d%c %d%c%d", RandomUtils.getRandomAlphaChar(), random.nextInt(9), RandomUtils.getRandomAlphaChar(),
                random.nextInt(9), RandomUtils.getRandomAlphaChar(), random.nextInt(9));
    }
}
