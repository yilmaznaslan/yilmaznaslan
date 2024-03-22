import org.junit.jupiter.api.Test;

interface Animal {
    void makeSound(); // abstract method

    default void eat() { // default method
        System.out.println("The animal is eating.");
    }
}


public class Dog implements Animal {
    @Override
    public void makeSound() {
        System.out.println("The dog barks.");
    }
}

class Main {

    @Test
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.makeSound(); // "The dog barks."
        dog.eat(); // "The animal is eating."
    }
}
