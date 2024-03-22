import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeTutorial {

    @Test
    public void testDate() {
        System.out.println("Date Tutorial");
        LocalDate date = LocalDate.now();
        System.out.println(date.withMonth(12));
        System.out.println(date);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = date.format(dateTimeFormatter);
        System.out.println(formattedDate);
    }


    @Test
    public void testTime() {
        System.out.println("Time Tutorial");
        LocalTime time = LocalTime.now();

        System.out.println(time);



    }
}
