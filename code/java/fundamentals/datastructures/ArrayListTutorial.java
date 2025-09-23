package datastructures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ArrayListTutorial {


    /**
     * 1. Empty ArrayList
     */
    @Test
    void emptyList() {
        ArrayList<Integer> ages = new ArrayList<>();
        Assertions.assertTrue(ages.isEmpty());
    }


    /**
     * 2. ArrayList with initial capacity
     * In this case, 10 is the initial capacity of the ArrayList.
     * It's worth noting that this doesn't limit the ArrayList to 10 elements – it can grow dynamically.
     */
    @Test
    void initialCapacity() {
        ArrayList<String> list = new ArrayList<>(10);
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
    }


}
