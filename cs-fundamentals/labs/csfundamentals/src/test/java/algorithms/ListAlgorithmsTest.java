package algorithms;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ListAlgorithmsTest {


    /**
     * Sum of simple array(list)
     */
    @Test
    void emptyList() {
        List<Integer> arr = new ArrayList<>(List.of(1, 2, null, 4));

        Integer expected = 7;
        Integer actual = sumOfArray(arr);
        Assertions.assertEquals(expected, actual);

    }

    private int sumOfArray(List<Integer> arr) {
        // list.stream().mapToInt(Integer::intValue).min();
        // list.stream().mapToInt(Integer::intValue).max();
        // list.stream().mapToInt(Integer::intValue).average();
        return arr.stream().mapToInt(Integer::intValue).sum();
    }

    private int reduceListByCustomCalculation(List<Integer> list) {
        return list.stream().reduce(0, (acc, b) -> acc * b + 10);
    }

}


