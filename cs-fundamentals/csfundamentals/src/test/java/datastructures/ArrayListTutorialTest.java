package datastructures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

class ArrayListTutorialTest {


    @Test
    void immutableCollectionsFromSecureArray() {
        ArrayList<Integer> ages = new ArrayList<>(List.of(1, 2, null, 54));
        Assertions.assertTrue(ages.isEmpty());
    }


    /**
     * 1. Empty ArrayList
     */
    @Test
    void emptyList() {
        ArrayList<Integer> ages = new ArrayList<>();
        Assertions.assertTrue(ages.isEmpty());
    }


    @Test
    void emptyListFilled() {
        ArrayList<Integer> ages = new ArrayList<>(Collections.nCopies(5, 10));
        Assertions.assertFalse(ages.isEmpty());
    }


    /**
     * 2. ArrayList with initial capacity
     * In this case, 10 is the initial capacity of the ArrayList.
     * It's worth noting that this doesn't limit the ArrayList to 10 elements – it can grow dynamically.
     */
    @Test
    void initialCapacity() {
        ArrayList<String> list = new ArrayList<>(10);
        list.add(2, "");
    }

    /**
     * 3. ArrayList with predefined elements
     */
    @Test
    void predefinedElements() {
        ArrayList<String> list = new ArrayList<>(List.of("name", "yilmaz"));
    }


    /**
     * Adding Elements to List
     */
    @Test
    void addingElements() {
        ArrayList<String> list = new ArrayList<>();
        list.add("name");
        list.add("yilmaz");
        Assertions.assertEquals(2, list.size());


        ArrayList<Character> characterArrayList = new ArrayList<>();
        characterArrayList.add('a');
        characterArrayList.add('b');
        char[] resultString = new char[characterArrayList.size()];
        for (int i = 0; i < characterArrayList.size(); i++) {
            resultString[i] = characterArrayList.get(i);
        }

        String actualString = String.valueOf(resultString);
        Assertions.assertEquals("ab", actualString);
    }

    @Test
    void findMaxOfElements() {
        var ages = new ArrayList<>(List.of(1, 20, 5, 87, 96));
        Collections.max(ages);


        var pricesPerCar = new HashMap<String, Integer>();
        pricesPerCar.put("Audi", 12);
        pricesPerCar.put("bmw", 53);
        int maxValue = 0;
        String maxKey = "";
        for (var key : pricesPerCar.keySet()) {
            final var value = pricesPerCar.get(key);
            maxKey = (value > maxValue) ? key : maxKey;
            maxValue = (value > maxValue) ? value : maxValue;
        }
        System.out.println("Max value: " + maxValue + "maxKey: " + maxKey);


        var weights = new int[]{2, 4,};
        var maxValueOfWeights = Arrays.stream(weights).max().getAsInt();
    }


}
