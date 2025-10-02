package questions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

class TestSlidingWindow {


    // --- Fixed size sliding window ----
    @Test
    void findMaxSumOfAnySubArrayUsingSlidingWindow() {
        // [2, 1, 5, 1, 3, 2], k = 3.
        int[] input = {2, 1, 5, 1, 3, 2};
        int k = 3;
        int expected = 9;

        Assertions.assertEquals(expected, demo1(input, 3));


    }

    private int demo1(int[] input, int k) {

        int currentSum = 0;
        for (int i = 0; i < k; i++) {
            currentSum = currentSum + input[i];
        }
        int max = currentSum;
        for (int endIndex = k; endIndex < input.length; endIndex++) {
            currentSum = currentSum + input[endIndex] - input[endIndex - k];
            max = Math.max(currentSum, max);
        }


        return max;
    }

    private int findMaxSumOfAnySubArray(int[] input, int k) {
        int max = 0;
        // i=3 -> n- k
        // i=2, k=3, J=0, j=1, j=2
        for (int i = 0; i < input.length - k + 1; i++) {
            int sum = 0;
            for (int j = 0; j < k; j++) {
                sum = sum + input[i + j];
            }
            max = Math.max(sum, max);
        }
        return max;
    }

    private int findMaxSumOfAnySubArrayUsingSlidingWindow(int[] input, int k) {
        int currentSum = 0;
        for (int i = 0; i < k; i++) {
            currentSum = currentSum + input[i];
        }

        int max = currentSum;
        for (int j = k; j < input.length; j++) {
            currentSum = currentSum + input[j] - input[j - k];
            max = Math.max(currentSum, max);
        }
        return max;
    }


    // --- Variable size sliding window ----
    // "Problem 1: Smallest Subarray's length  with a Sum ≥ Target"

    @Test
    /**
     * Input: arr = [2, 1, 5, 2, 3, 2], S = 7
     * Output: 2
     * Explanation: The smallest subarrayLength with sum ≥ 7 is [5,2].
     */
    void findSmallestSubArrayLength() {
        var input = new int[]{2, 1, 1, 0, 5, 2};
        var result = getSmallestSubArrayLength(input, 7);
        var expected = 2;
        Assertions.assertEquals(expected, result);

    }

    /**
     * The smallest subarrayLength with sum ≥ 7 is [5,2].
     *
     * @param input
     * @param target
     * @return
     */
    private int getSmallestSubArrayLength(int[] input, int target) {
        int minLength = Integer.MAX_VALUE;
        int left = 0;
        int sum = 0;

        for (int right = 0; right < input.length; right++) {
            sum = sum + input[right];
            while (sum >= target) {
                int currentMinLength = right - left + 1;
                minLength = Math.min(currentMinLength, minLength);
                sum = sum - input[left];
                left++;
            }

        }

        return minLength;
    }


    // "Problem 2: Smallest Subarray   with a Sum ≥ Target"
    @Test
    /**
     * Input: arr = [2, 1, 5, 2, 3, 2], S = 7
     * Output: 2
     * Explanation: The smallest subarray with sum ≥ 7 is [5,2].
     */
    void findSmallestSubArray() {

        var input = new int[]{2, 1, 1, 0, 5, 2};
        //var result = getSmallestSubArray(input, 7);
        var result = demo(input, 7);
        var expected = new int[]{5, 2};
        Assertions.assertArrayEquals(expected, result);

    }

    private int[] demo(int[] input, int s) {
        int minLength = Integer.MAX_VALUE;
        int left = 0;
        int leftAtMin = 0;
        int sum = 0;

        for (int end = 0; end < input.length; end++) {
            sum = sum + input[end];
            while (sum >= s) {
                int currentMinLength = end - left + 1;
                sum = sum - input[left];
                if (currentMinLength < minLength) {
                    leftAtMin = left;
                    minLength = currentMinLength;
                }
                left++;

            }

        }

        int[] result = Arrays.copyOfRange(input, leftAtMin, leftAtMin + minLength);
        return result;
    }

    // Create all possible subArrays and find the smallest one. The smallest would be starting at subArrays 1
    // 1. Start creating subArrays(0, 1, 2, ... n)  at each index and check the sum of the array, if true break;
    // The first found is the result
    private int[] getSmallestSubArray(int[] input, int s) {
        boolean isValid = sumOfArray(input) >= s;
        if (!isValid) throw new RuntimeException("");

        int n = input.length;

        for (int size = 1; size < n; size++) {
            for (int i = 0; i <= n - size; i++) {
                int[] subArrayOfSize = slice(input, i, size);
                System.out.println(Arrays.toString(subArrayOfSize));
                if (sumOfArray(subArrayOfSize) >= s) {
                    return subArrayOfSize;
                }
            }

        }


        return input;

    }


    // ---- Variable-size window based on a condition -----
    /*
     * Input: arr = [1, 1, 0, 1, 1, 1]
     * Output: 3
     * Explanation: The longest subarray of 1s is [1,1,1], length = 3
     */
    @Test
    void findLongestLengthOfSubArray() {
        int[] arr = {1, 1, 0, 1, 1, 1};

        int actualResult = findLongestLengthOfSubArray(arr);
        Assertions.assertEquals(actualResult, 3);

    }

    // I will come to this later
    private int findLongestLengthOfSubArrayUsingPointer(int[] arr) {
        int maxLength = 0;
        int sum = arr[0];

        return 0;
    }

    private int findLongestLengthOfSubArray(int[] arr) {
        int n = arr.length;
        for (int size = n; size > 0; size--) {
            for (int i = 0; i < n - size + 1; i++) {
                int[] subArrayOfSizeAtI = slice(arr, i, size);
                boolean isValid = isArrayValid(subArrayOfSizeAtI);
                if (isValid) {
                    return size;
                }
            }
        }

        return n;
    }

    private boolean isArrayValid(int[] arr) {
        for (int i : arr) {
            if (i == 0) {
                return false;
            }
        }
        return true;
    }

    private int sumOfArray(int[] arr) {
        int sum = 0;
        for (var i : arr) {
            sum = sum + i;
        }
        return sum;
    }

    private int[] slice(int[] arr, int startIndex, int size) {
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = arr[startIndex + i];
        }
        return result;
    }


    //Task: Longest Substring Without Repeating Characters

    /**
     * Input: s = "abcabcbb"
     * Output: 3
     * Explanation: The longest substring without repeating characters is "abc", length = 3
     */
    @Test
    void as() {
        String s = "abcbabcdbb";
        int expectedResult = 4;

        int startIndex = 0;
        int maxLength = 0;

        HashSet<Character> uniqueChars = new HashSet<Character>();
        for (int endIndex = 0; endIndex < s.length(); endIndex++) {
            char c = s.charAt(endIndex);

            while (uniqueChars.contains(c)) {
                char charAtStart = s.charAt(startIndex);
                uniqueChars.remove(charAtStart);
                startIndex++;
            }
            uniqueChars.add(c);
            int currentLength = endIndex - startIndex + 1;
            maxLength = Math.max(maxLength, currentLength);

        }

        Assertions.assertEquals(expectedResult, maxLength);

    }


    /**
     * Maximum Sum of Subarray of Size K (Array Version)
     * <p>
     * Problem Statement:
     * <p>
     * You are given an array of integers and a number k.
     * <p>
     * Find the maximum sum of any contiguous subarray of size k.
     */
    @Test()
    void testMaxSumOfSubarrayOfSizeK() {

        int[] input = {2, 1, 5, 1, 3, 2};
        int k = 3;
        int expected = 9;
        int actual = maxSumOfSubarrayOfSizeK(input, k);

        Assertions.assertEquals(expected, actual);

    }

    /**
     * Input: arr = [2, 1, 5, 1, 3, 2], k = 3
     * Output: 9
     * Explanation: The subarray [5,1,3] has the maximum sum 9.
     *
     * @param arr
     * @param k
     * @return
     */
    private int maxSumOfSubarrayOfSizeK(int[] arr, int k) {

        int currentSum = 0;
        for (int i = 0; i < k; i++) {
            currentSum = currentSum + arr[i];
        }


        int max = currentSum;
        int startIndex = 0;
        for (int end = k; end < arr.length; end++) {
            currentSum = currentSum + arr[end] - arr[startIndex];
            max = Math.max(currentSum, max);
            startIndex++;
        }

        return max;

    }


    @Test
    void asd() {

        int[] input = {2, 1, 5, 2, 3, 2};
        int s = 7;

        int expected = 2;
        int actual = smallestSubarrayWithSumBiggerThenTarget(input, s);
        Assertions.assertEquals(expected, actual);
    }

    /**
     * Input: arr = [2, 1, 5, 2, 3, 2], S = 7
     * Output: 2
     * Explanation: The smallest subarray with sum ≥ 7 is [5, 2].
     */
    private int smallestSubarrayWithSumBiggerThenTarget(int[] arr, int s) {

        int startIndex = 0;
        int windowSum = 0;
        int minLength = arr.length;


        for (int endIndex = 0; endIndex < arr.length; endIndex++) {

            windowSum = windowSum + arr[endIndex];
            while (windowSum >= s) {
                int currentLength = endIndex - startIndex + 1;
                minLength = Math.min(currentLength, minLength);

                windowSum = windowSum - arr[startIndex];
                startIndex++;

            }

        }

        return minLength == arr.length ? 0 : minLength;
    }


}
