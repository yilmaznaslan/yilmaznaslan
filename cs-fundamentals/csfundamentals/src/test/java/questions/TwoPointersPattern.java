package questions;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Applied to the sorted arrays, merging or partitioning problems.
 */
class TwoPointersPattern {


    @Test
    void testFindPairWithTargetSum() {
        int[] arr = {1, 2, 3, 4, 6};
        int target = 6;
        int[] expectedOutput = new int[]{2, 4};

        int[] actualOutput = findPairWithTargetSum(arr, target);

        Assertions.assertArrayEquals(expectedOutput, actualOutput);
    }

    private int[] findPairWithTargetSum(int[] arr, int sum) {
        int[] fallbackResult = new int[]{-1, -1};
        int leftIndex = 0;
        int rightIndex = arr.length - 1;

        while (leftIndex < rightIndex) {
            int currentSum = arr[leftIndex] + arr[rightIndex];

            if (currentSum > sum) {
                rightIndex = rightIndex - 1;
            } else if (currentSum < sum) {
                leftIndex = leftIndex + 1;
            } else {
                return new int[]{arr[leftIndex], arr[rightIndex]};
            }

        }

        return fallbackResult;
    }


    @Test
    void testCountUniques() {
        int[] input = {1, 1, 1, 1, 2, 2, 3, 3, 3};

        Assertions.assertEquals(3, countUniques(input));
    }

    // {1, 1, 1, 1, 2, 2, 3, 3, 3}
    private int countUniques(int[] arr) {

        int lastUniqueIndex = 0;
        int counter = 1;
        for (int currentIndex = 1; currentIndex < arr.length; currentIndex++) {
            boolean areTheySame = arr[lastUniqueIndex] == arr[currentIndex];
            if (areTheySame) {
                continue;
            } else {
                counter++;
                lastUniqueIndex = currentIndex;

            }
        }
        return counter;
    }

    @Test
    void asd() {

        int[] arr = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        int[] expected = {8, 7};

        int[] actual = findContainerWithMostWater(arr);

        Assertions.assertArrayEquals(expected, actual);

    }

    private int[] findContainerWithMostWater(int[] arr) {


        int left = 0;
        int right = arr.length - 1;
        int maxArea = 0;
        int rightAtMax = right;
        int leftAtMax = left;
        while (left < right) {

            int currentArea = calculateAreaAtBetweenIndexes(arr, left, right);
            if (currentArea > maxArea) {
                rightAtMax = right;
                leftAtMax = left;
                maxArea = currentArea;
            }

            if (arr[left] < arr[right]) {
                left++;
            } else {
                right--;
            }


        }


        return new int[]{arr[leftAtMax], arr[rightAtMax]};
    }

    private int calculateAreaAtBetweenIndexes(int[] arr, int i, int k) {

        int horizontalDistance = Math.abs(i - k);
        int minVerticalHeight = Math.min(arr[i], arr[k]);

        return horizontalDistance * minVerticalHeight;
    }
}
