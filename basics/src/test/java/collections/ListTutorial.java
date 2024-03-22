package collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListTutorial {


    /**
     * Initialization
     */
    @Test
    void ASSERT_THAT_list_is_updated_GIVEN_THAT_list_is_initiated_from_immutable_list() {
        // GIVEN
        List<String> names = List.of("Yilmaz", "Naci");
        List<String> expectedList = List.of("Yilmaz", "Naci", "Aslan");

        // WHEN
        List<String> updatedNames = new ArrayList<>(names);
        updatedNames.add("Aslan");

        // THEN
        Assertions.assertEquals(expectedList, updatedNames);

    }

    /**
     * List operation: Copying a list
     */
    @Test
    void ASSERT_THAT_list_is_updated_WHEN_list_is_copied() {
        // GIVEN
        List<String> sourceList = List.of("Yilmaz", "Naci");
        List<String> expectedList = List.of("Aslan", "Yilmaz", "Naci");

        // WHEN
        List<String> updatedNames = new ArrayList<>();
        updatedNames.add("Aslan");
        Collections.copy(updatedNames, sourceList);

        // THEN
        Assertions.assertEquals(expectedList, updatedNames);

    }

    @Test
    void ASSERT_THAT_source_list_is_not_updated_WHEN_referenced_list_is_updated_GIVEN_THAT_list_is_initiated_from_immutable_list() {
        // GIVEN
        List<String> names = List.of("Yilmaz", "Naci");
        List<String> expectedList = List.of("Yilmaz", "Naci");

        // WHEN
        List<String> updatedNames = new ArrayList<>(names);
        updatedNames.set(1, "naci");

        // THEN
        Assertions.assertEquals(expectedList, names);
    }

    @Test
    void ASSERT_THAT_source_list_is_updated_WHEN_referenced_list_is_updated_GIVEN_THAT_list_is_initiated_from_immutable_list() {
        // GIVEN
        List<String> names = List.of("Yilmaz", "Naci");
        List<String> expectedList = List.of("Yilmaz", "Naci");

        // WHEN
        List<String> updatedNames = names;
        updatedNames.set(1, "naci");

        // THEN
        Assertions.assertEquals(expectedList, names);
    }

}
