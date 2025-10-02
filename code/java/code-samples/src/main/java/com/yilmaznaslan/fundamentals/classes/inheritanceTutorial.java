import org.junit.jupiter.api.Test;


interface ISampleInterface {
    int SAMPLE_CONSTANT = 10;
}

public class inheritanceTutorial implements ISampleInterface {


    @Test
    public void testInheritance() {
        System.out.println("Inheritance Tutorial");
        System.out.println("Sample Constant: " + SAMPLE_CONSTANT);
    }

}
