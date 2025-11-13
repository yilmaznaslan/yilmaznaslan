package datastructures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class StringTest {

    /**
     * Strings
     */
    @Test
    void StringsBasics() {
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
        Assertions.assertEquals(2, actualString.length());

        Assertions.assertEquals('a', actualString.charAt(0));


    }
}
