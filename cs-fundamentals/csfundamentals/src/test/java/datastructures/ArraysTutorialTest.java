package datastructures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;


/**
 * Arrays: An array is a container object that holds a fixed number of values of a single type.
 * The length of an array is established when the array is created. After creation, its length is fixed.
 */
class ArraysTutorialTest {


    @Test
    void creation_and_initialization() {
        int[] ages = new int[5];
        int len = ages.length;
        ages[0] = 1;
        Arrays.stream(ages).forEach(as -> System.out.println(as + 5));

        int[] rates = {1, 2, 3, 5};
        Arrays.stream(rates).peek(System.out::println);


        for (int i : ages) {
            System.out.println("This is:" + i);
        }


        int[] copiedArray = ages;

        copiedArray[0] = 2;
        Assertions.assertEquals(ages[0], 2);

    }

    /**
     * Array Manipulations: sorting
     */
    @Test
    void arrays_utils_sorting() {
        int[] ages = {5, 6, 2, 1, 9};
        Arrays.stream(ages).forEach(System.out::println);
        Arrays.sort(ages);
        Arrays.stream(ages).forEach(System.out::println);
    }

    /**
     * Array Manipulations: searching
     */
    @Test
    void arrays_utils() {
        int[] ages = {5, 6, 2, 1, 9};

        // Wrong answer
        int value = Arrays.binarySearch(ages, 1);
        System.out.println(value);

        // first sort
        Arrays.sort(ages);
        value = Arrays.binarySearch(ages, 5);
        System.out.println(value);
        Assertions.assertEquals(value, 2);
    }
}
