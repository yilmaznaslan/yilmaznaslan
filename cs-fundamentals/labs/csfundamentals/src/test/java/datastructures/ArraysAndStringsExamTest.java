package datastructures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

class ArraysAndStringsExamTest {


    @Test
    @DisplayName("Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target")
    void testGetMatchingIndexes() {
        // GIVEN
        int[] inputArray = {2, 11, 4, 9};
        final var target = 11;
        int[] expectedResult = {0, 3};

        // WHEN
        int[] actualResult1 = testGetMatchingIndexes(inputArray, target);
        int[] actualResult2 = testGetMatchingIndexesAlt(inputArray, target);

        // THEN
        Assertions.assertArrayEquals(expectedResult, actualResult1);
        Assertions.assertArrayEquals(expectedResult, actualResult2);

    }

    public int[] testGetMatchingIndexes(int[] nums, int target) {

        int[] indexes = new int[2];

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    indexes[0] = i;
                    indexes[1] = j;
                }
            }
        }

        return indexes;
    }

    public int[] testGetMatchingIndexesAlt(int[] nums, int target) {
        HashMap<Integer, Integer> result = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int requiredPair = target - nums[i];
            if (result.containsKey(requiredPair)) {

                return new int[]{result.get(requiredPair).intValue(), i};
            }
            result.put(nums[i], i);
        }

        throw new RuntimeException();

    }


    //
    @Test
    void testLongestSubStringLength() {
        // GIVEN
        String input = " ";
        int expectedResult = 1;


        // GIVEN
        int actualResult = longestSubstringLengthUsingSet(input);

        // THEN
        Assertions.assertEquals(expectedResult, actualResult);


    }

    private int longestSubstringLengthUsingSet(String s) {

        // Input Validation
        if (s == null || s.isEmpty()) {
            return 0;
        }


        int stringLength = s.length();
        int searchWindowSizes = stringLength;

        HashMap<String, Integer> searchStringByResult = new HashMap<>();

        for (int i = 0; i < searchWindowSizes; i++) {

            for (int j = 1; j < stringLength; j++) {

                if ((j + i) <= stringLength) {
                    String searchString = s.substring(j, j + i);
                    char[] searchStringAsArray = searchString.toCharArray();
                    Set<Character> uniqueChar = new HashSet<>();
                    for (char c : searchStringAsArray) {
                        uniqueChar.add(c);
                    }

                    if (searchStringAsArray.length == uniqueChar.size()) {
                        System.out.println(searchStringAsArray);
                        System.out.println(uniqueChar.size());
                        searchStringByResult.put(searchString, uniqueChar.size());
                    }

                }

            }
        }


        return Collections.max(searchStringByResult.values());

    }

    // String input = "abcadbcea";
    // 5
    private int longestSubstringLength(String input) {

        if (input == null || input.equals("")) {
            return 0;
        }
        int stringLength = input.length();
        int searchWindowSizes = stringLength;
        int longestSubstringLength = 1;


        HashMap<String, Integer> searchStringByResult = new HashMap<>();


        for (int i = 0; i < searchWindowSizes; i++) {

            for (int j = 0; j < stringLength; j++) {

                if ((j + i) < stringLength) {
                    String searchString = input.substring(j, j + i);
                    if (!searchStringByResult.containsKey(searchString)) {

                        for (int k = 0; k < searchString.length(); k++) {
                            char searchChar = searchString.charAt(k);

                            for (int l = 0; l < searchString.length(); l++) {

                                boolean isFound = (searchString.charAt(l) == searchChar) && (l != k);
                                if (isFound) {
                                    longestSubstringLength = searchString.length() - 1;
                                } else {
                                    longestSubstringLength = searchString.length();
                                }
                            }
                        }


                        searchStringByResult.put(searchString, 2);
                    }
                }


            }
        }

        return longestSubstringLength;

    }


    @Test
    void testMaxSum() {

        int[] ages = {1, 6, 9, 3, 7, 2, 5, 4, 2, 6};
        int k = 2;
        int max = 0;
        int sum = ages[0] + ages[1];
        for (int i = 1; i < (ages.length - k); i++) {
            sum = sum - ages[i - 1] + ages[i - 1 + k];
            System.out.println(sum);
            max = Math.max(sum, max);

        }

        Assertions.assertEquals(15, max);

    }


    @Test
    void testContainerWithMostWater() {
        // GIVEN
        // int height[] = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        int height[] = createRandomArrayOfSize(1000000);

        // WHEN 2147483646
        int actualResult1 = containerWithMostWaterBrutal(height);
        int actualResult2 = containerWithMostWater(height);


        Assertions.assertEquals(49, actualResult2);

    }

    private int containerWithMostWaterBrutal(int[] height) {
        int max = 0;
        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                int xDistance = Math.abs(i - j);
                int yDistance = Math.min(height[i], height[j]);
                int area = xDistance * yDistance;
                max = Math.max(area, max);
            }
        }
        return max;
    }


    private int containerWithMostWater(int[] height) {

        int leftPointerIndex = 0;
        int rightPointerIndex = height.length - 1;
        int max = 0;

        while (leftPointerIndex < rightPointerIndex) {
            int area = Math.min(height[leftPointerIndex], height[rightPointerIndex]) * (rightPointerIndex - leftPointerIndex);

            max = Math.max(area, max);
            if (height[leftPointerIndex] < height[rightPointerIndex]) {
                leftPointerIndex++;
            } else {
                rightPointerIndex--;
            }
        }


        return max;

    }


    @Test
    void asdsa() {
        // GIVEN
        String s = "ABC";
        String t = "BC";
        String expectedResult = "BC";


        // WHEN
        String actualResult = getT(s, t);


        // THEN
        Assertions.assertEquals(expectedResult, actualResult);

    }

    private String getT(String s, String t) {
        // Validate
        if (!doesStringContainsAllInAnyOrder(s, t)) {
            return "";
        }
        String minimumSubString = s;
        // WHEN
        // create all possible substrings of size n, n+1 ... m starting from s[0]
        // check if a substring contains all in any order, if true then save the  substring if the found substring.length < previous substring length
        ArrayList<String> allNSizedSubstrings = new ArrayList<>();
        int n = t.length();
        int m = s.length();
        //         String s = "ADOBECODEBANC";
        //        String t = "ABC";
        // i = 0, n = 3,
        for (int i = 0; i < m; i++) {
            for (int j = i + n; j < m; j++) {
                allNSizedSubstrings.add(s.substring(i, j));
            }
        }


        for (String sub : allNSizedSubstrings) {
            boolean isStringValid = doesStringContainsAllInAnyOrder(sub, t);
            if (isStringValid && sub.length() < minimumSubString.length()) {
                minimumSubString = sub;
            }
        }


        return minimumSubString;
    }


    private boolean doesStringContainsAllInAnyOrder(String input, String toBeSearch) {
        boolean result = true;
        System.out.println("Input: " + input + " search: " + toBeSearch);

        ArrayList<Character> inputArray = new ArrayList<>();
        for (char c : input.toCharArray()) {
            inputArray.add(c);
        }


        for (char c : toBeSearch.toCharArray()) {

            int index = inputArray.indexOf(c);

            if (index >= 0) {
                inputArray.remove(index);
            } else {
                System.out.println(c);
                System.out.println(index);
                System.out.println(input);
                return false;
            }

        }
        return result;
    }

    private int[] createRandomArrayOfSize(int n) {

        int[] array = new int[n];

        for (int i = 0; i < n; i++) {
            array[i] = i;
        }


        return array;
    }
}
