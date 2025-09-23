# What is the difference between Bean and Pojo

### 1) POJO (Plain Old Java Object):

The term POJO was coined to denote a regular Java Object (that is, not a JavaBean,
EntityBean etc.) and to avoid the confusion caused by the overloading of the term "Bean".

- It does not extend or implement any predefined interfaces or classes, nor does it have any annotations.
- It has no restrictions such as the need to override any methods.
- It does not require a no-argument constructor.
- It can have any number of private fields and corresponding public getters and setters.
- It does not necessarily need to implement the Serializable interface.

Here's an example of a POJO:

```java
public class User {
    private String name;
    private int age;

    // standard getters and setters

}
```

### 2) Bean

A Bean is a special kind of POJO that is meant to be used with frameworks like Spring or EJB. Beans follow
certain conventions such as

- having a default (no-argument) constructor
- It should be serializable, meaning it should ideally implement the Serializable interface.
- It should have private properties and corresponding public getter and setter methods.
    - allowing access to properties using getter and setter methods that follow a naming convention.
- Beans can also be bound to certain design contracts by the framework that uses them, and they can be annotated with
  special annotations (like @Entity, @Component, @Service, @Repository etc.).

Here's an example of a Bean:

```java
import java.io.Serializable;

public class StudentBean implements Serializable {
    private String name;
    private int age;

    // A no-argument constructor
    public StudentBean() {
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}


```

To summarize:

- A Bean is a POJO that follows certain conventions and is meant to be used with a framework like Spring or EJB.
- A POJO is just a term that denotes a simple, plain Java object that doesn't extend or implement some specific library
  interface, and doesn't have any annotations.





