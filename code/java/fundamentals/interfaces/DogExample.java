package fundamentals.interfaces;

import org.junit.jupiter.api.Test;

interface Animal {
    void makeSound();

    default void eat() {
        System.out.println("The animal is eating.");
    }
}

public class DogExample implements Animal {
    private int age;
    private String name;

    public DogExample(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public void makeSound() {
        System.out.println("The dog barks.");
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Test
    public static void main(String[] args) {
        DogExample dog = new DogExample("Buddy", 3);
        dog.makeSound();
        dog.eat();
        System.out.println("Dog name: " + dog.getName() + ", age: " + dog.getAge());
    }
}
