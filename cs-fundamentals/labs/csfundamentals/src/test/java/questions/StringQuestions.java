package questions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class StringQuestions {


    @Test
    void Stringbasics() {
        String word1 = "hello";
        //word1.charAt(10);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('s');
        stringBuilder.toString();
    }

    /*
    You are given two strings word1 and word2. Merge the strings by adding letters in alternating order, starting with word1. If a string is longer than the other, append the additional letters onto the end of the merged string.

    Return the merged string.



    Example 1:

    Input: word1 = "abc", word2 = "pqr"
    Output: "apbqcr"
    Explanation: The merged string will be merged as so:
    word1:  a   b   c
    word2:    p   q   r
    merged: a p b q c r
    Example 2:

    Input: word1 = "ab", word2 = "pqrs"
    Output: "apbqrs"
    Explanation: Notice that as word2 is longer, "rs" is appended to the end.
    word1:  a   b
    word2:    p   q   r   s
    merged: a p b q   r   s
    Example 3:

    Input: word1 = "abcd", word2 = "pq"
    Output: "apbqcd"
    Explanation: Notice that as word1 is longer, "cd" is appended to the end.
    word1:  a   b   c   d
    word2:    p   q
    merged: a p b q c   d


    Constraints:

    1 <= word1.length, word2.length <= 100
    word1 and word2 consist of lowercase English letters.
     */
    @Test
    public void mergeAlternately() {

    }


    private String solutionMergeAlternatilyEfficient(String word1, String word2) {
        if (word1 == null || word2 == null) {
            return null;
        }
        int longestWordLength = Math.max(word1.length(), word2.length());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < longestWordLength; i++) {
            if (i < word1.length()) stringBuilder.append(word1.charAt(i));
            if (i < word2.length()) stringBuilder.append(word2.charAt(i));
        }
        return stringBuilder.toString();
    }

    private String solutionMergeAlternately(String word1, String word2) {

        if (word1 == null || word2 == null) {
            return null;
        }

        char[] word1AsArray = word1.toCharArray();
        char[] word2AsArray = word2.toCharArray();
        int lengthOfWord1 = word1.length();
        int lengthOfWord2 = word2AsArray.length;
        int totalLength = lengthOfWord1 + lengthOfWord2;
        char[] mergedStringArray = new char[totalLength];
        ArrayList<Character> resultingArray = new ArrayList<>();

        int shortestLength = lengthOfWord1 < lengthOfWord2 ? lengthOfWord1 : lengthOfWord2;

        int longestLength = lengthOfWord1 > lengthOfWord2 ? lengthOfWord1 : lengthOfWord2;

        for (int i = 0; i < longestLength; i++) {
            if (i < shortestLength) {
                resultingArray.add(word1AsArray[i]);
                resultingArray.add(word2AsArray[i]);
            } else {
                if (lengthOfWord1 > lengthOfWord2) {
                    resultingArray.add(word1AsArray[i]);
                } else {
                    resultingArray.add(word2AsArray[i]);
                }
            }

        }

        char[] result = new char[totalLength];

        for (int i = 0; i < totalLength; i++) {
            result[i] = resultingArray.get(i);
        }

        return String.valueOf(result);

    }

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


        String asd = "asd";

        System.out.println(asd.length());

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
