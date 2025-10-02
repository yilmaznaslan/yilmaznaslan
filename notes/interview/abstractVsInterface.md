# What is the difference between abstract classes and interfaces in Java?

In Java, both abstract classes and interfaces are used to define contracts for other classes to implement. However, they
have some key differences:

**Abstract classes**:

- Can have both abstract and concrete (implemented) methods.
- Can have instance variables (fields) with any access modifier (private, protected, public).
- Can have constructors.
- Can extend other classes and implement interfaces.
- Subclasses can inherit from only one abstract class (due to **single inheritance** in Java).

**Interfaces**:

* Can have only abstract methods (prior to Java 8), default methods (introduced in Java 8), and static methods
  (introduced in Java 8)
  ```java
  interface ISampleInterface {
      public static String SAMPLE_CONSTANT = "Sample Constant";
  }
  
  public class inheritanceTutorial implements ISampleInterface {
  
  
      @Test
      public void testInheritance() {
          System.out.println("Inheritance Tutorial");
          System.out.println("Sample Constant: " + SAMPLE_CONSTANT);
      }
  
  }
  ```

* Cannot have instance variables, but can have public static final (constant) variables.
  Cannot have constructors.
  Cannot extend classes, but can extend other interfaces.
  A class can implement multiple interfaces, which allows for a form of multiple inheritance.
  

### When to use which?

- Use an abstract class when you want to provide a **common base class with some default behavior** that subclasses can 
build upon.

- Use an interface when you want to define a contract that multiple unrelated classes should adhere to, or when you want
to achieve multiple inheritance.

In summary, abstract classes provide a foundation for subclasses to build upon, while interfaces define a contract for
classes to implement. Java's single inheritance rule applies to abstract classes but not to interfaces, allowing a class
to implement multiple interfaces.

## Resource

Chat GPT prompt(model4):
> What is the difference between abstract classes and interfaces in Java?