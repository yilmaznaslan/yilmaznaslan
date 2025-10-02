package questions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringQuestions {


    @Test
    void testReversString() {
        String input = "abcede";
        String expectedOutput = "edecba";
        // "


        String actualOutput = reverseString(input.toCharArray());
        String actualOutputEff = String.valueOf(reverseStringEfficient(input.toCharArray()));

        Assertions.assertEquals(expectedOutput, actualOutput);
        Assertions.assertEquals(expectedOutput, actualOutputEff);

    }

    private String reverseString(char[] inputAsArray) {


        char[] resultArray = new char[inputAsArray.length];

        for (int i = 0; i < inputAsArray.length; i++) {

            int targetIndex = (inputAsArray.length - i - 1);
            resultArray[targetIndex] = inputAsArray[i];
        }


        return String.valueOf(resultArray);
    }

    //    String input = "abc ede";
    //   String expectedOutput = "edecba";
    // "
    private char[] reverseStringEfficient(char[] inputAsArray) {

        int inputLength = inputAsArray.length;
        int swappingCount = inputLength / 2;

        int rightIndex = inputLength - 1;
        for (int i = 0; i < swappingCount; i++) {
            char temp = inputAsArray[i];
            inputAsArray[i] = inputAsArray[rightIndex];
            inputAsArray[rightIndex] = temp;

            rightIndex--;
        }

        return inputAsArray;
    }


    @Test
    void testRotateArrayByKStep() {

        int[] input = {1, 2, 3, 4, 5};
        int[] expectedInput = {4, 5, 1, 2, 3};

        Assertions.assertArrayEquals(expectedInput, rotateArrayByKStep(input, 12));
    }


    // int[] ages = {1,2,3,4,5} rotate by '12'
    // -> {4,5,1,2,3}

    private int[] rotateArrayByKStep(int[] input, int step) {
        int absStepToMove = step % input.length;
        int maxIndex = input.length - 1;
        for (int index = 0; index < input.length; index++) {
            if (index + absStepToMove < maxIndex) {
                input[index + absStepToMove] = input[index];
            } else {
                input[index + absStepToMove - input.length - 1] = input[index];
            }
        }

        return input;
    }
}
