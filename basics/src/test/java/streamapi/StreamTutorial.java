package streamapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class StreamTutorial {


    /**
     * Stream Creation
     */
    @Test
    void testStream() {

        List<String> names = new ArrayList<>();
        names.add("yilmaz");
        names.add("naci");
        names.add("aslan");

        // Stream of Collection
        Stream<String> streamOfArray = names.stream();

    }


    /**
     * Terminal Operations
     * <list> filter() </list>
     * <list> </list>
     */
    @Test
    void applyLazyOperations() {


    }


    /**
     * Terminal Operations
     * <list> forEach() </list>
     * <list> </list>
     */
    @Test
    void applyTerminalOperations() {

        // forEach
        List<String> names = List.of("Yilmaz", "Naci", "Aslan");
        names.stream().forEach(asd -> System.out.println(asd));

        // anyMatch
        boolean result = names.stream().anyMatch( name -> name.equals("Yilmaz"));
        Assertions.assertTrue(result);


    }

}
