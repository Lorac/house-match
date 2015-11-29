package ca.ulaval.glo4003.housematch.utils;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class RandomUtilsTest {

    @Test
    public void gettingRandomAlphaCharGetsARandomAlphaChar() {
        char returnedChar = RandomUtils.generateRandomUpperCaseAlphaChar();
        assertTrue(returnedChar >= 'A' && returnedChar <= 'Z');
    }

    @Test
    public void gettingRandomListElementGetsARandomElement() {
        List<Object> objects = new ArrayList<>();
        objects.add(new Object());
        objects.add(new Object());

        Object returnedObject = RandomUtils.getRandomListElement(objects);

        assertThat(objects, hasItem(returnedObject));
    }

    @Test
    public void gettingRandomArrayElementGetsARandomElement() {
        Object[] objects = {new Object(), new Object()};
        Object returnedObject = RandomUtils.getRandomArrayElement(objects);
        assertThat(Arrays.asList(objects), hasItem(returnedObject));
    }
}
