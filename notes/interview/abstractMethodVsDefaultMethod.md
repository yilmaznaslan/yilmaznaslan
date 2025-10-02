# What is default method and abstract methods in interfaces ? Explain by a sample java code

In Java, abstract methods and default methods are two types of methods you can have in an interface. Here's an
explanation of each with sample code:

1. Abstract methods:

- These are methods declared in an interface without any implementation.
- Any class implementing the interface must provide an implementation for these methods, unless the class itself is
  abstract.
- Prior to Java 8, interfaces could only have abstract methods.

```java

public interface Animal {
    void makeSound(); // abstract method
}

```

2. Default methods:

- Introduced in Java 8, default methods allow you to provide a default implementation of a method in an interface.
- Default methods help with the evolution of interfaces, as you can add new methods with default implementations without
  breaking existing implementations of the interface.
- Default methods can be overridden by implementing classes if they wish to provide a different implementation.

```java
public interface Animal {
    void makeSound(); // abstract method

    default void eat() { // default method
        System.out.println("The animal is eating.");
    }

}
```

Here's an example that demonstrates the usage of both abstract and default methods in an interface:

```java

public interface Animal {
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

public class Main {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.makeSound(); // "The dog barks."
        dog.eat(); // "The animal is eating."
    }
}
```

In this example, the Dog class implements the Animal interface. It provides an implementation for the abstract method
makeSound() and uses the default implementation of the eat() method.

## Resource

Chat GPTPrompt:
> What is default method and abstract methods in interfaces ? 
> - Consider java 17
> - Explain by a sample java code