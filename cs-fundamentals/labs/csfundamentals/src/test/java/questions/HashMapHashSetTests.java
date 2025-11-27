package questions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class HashMapHashSetTests {

    @Test
    void testContainsDuplicated() {

        int[] arr = {1, 2, 2, 4};
        Assertions.assertTrue(containsDuplicated(arr));
    }


    boolean containsDuplicated(int[] arr) {
        Set<Integer> uniqueResult = new HashSet<Integer>();
        for (int e : arr) {
            uniqueResult.add(e);

        }
        return uniqueResult.size() < arr.length;
    }


    @Test
    void testFindFirstNonRepeatingChar() {
        String input = "leeltcode";
        char expectedChar = 't';

        Assertions.assertEquals(expectedChar, findFirstNonRepeatingChar(input));
    }

    private char findFirstNonRepeatingChar(String s) {

        HashMap<Character, Integer> freq = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            freq.put(s.charAt(i), (1 + freq.getOrDefault(s.charAt(i), 0)));
        }

        for (int i = 0; i < s.length(); i++) {

            if (freq.get(s.charAt(i)) == 1) {
                return s.charAt(i);
            }

        }
        return 'c';

    }


    /**
     * ["eat", "tea", "tan", "ate", "nat", "bat"]
     * [
     * ["eat", "tea", "ate"],
     * ["tan", "nat"],
     * ["bat"]
     * ]
     *
     * @param strs
     * @return
     */
    private List<List<String>> groupAnagrams(String[] strs) {

        return List.of();
    }
}
