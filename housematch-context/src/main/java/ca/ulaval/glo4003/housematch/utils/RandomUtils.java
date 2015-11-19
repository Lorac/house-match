package ca.ulaval.glo4003.housematch.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class RandomUtils {

    private static final Integer NUMBER_OF_LETTERS_IN_ALPHABET = 26;

    private static Random random = new Random();

    private RandomUtils() {

    };

    public static char getRandomAlphaChar() {
        return (char) (random.nextInt(NUMBER_OF_LETTERS_IN_ALPHABET) + 'A');
    }

    public static <T> T getRandomElement(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }

    public static <T> T getRandomElement(T[] array) {
        return array[(random.nextInt(array.length))];
    }

    public static <T> List<T> getRandomElements(T[] array, Integer count) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(getRandomElement(array));
        }
        return list;
    }
}
