# Object Oriented Programming (OOP) Principles

- inheritance,
- polymorphism,
- encapsulation, and
- abstraction

### 1) Inheritance

Inheritance: It's a mechanism that allows one class to **inherit properties** and **methods from another class**. This
promotes **reusability** and **modularity** in code.

- Question: Can you explain the difference between **method overloading** and **method overriding** in Java?

Method overloading allows multiple methods in the same class to have the same name but **different parameter lists.**
Method
overriding occurs when a subclass provides a new implementation for a method that is already defined in its superclass.

``` java
class Shape {
    // Method overloading
    void draw() {
        System.out.println("Drawing a shape");
    }

    void draw(String color) {
        System.out.println("Drawing a shape with color: " + color);
    }
}

class Circle extends Shape {
    // Method overriding
    @Override
    void draw() {
        System.out.println("Drawing a circle");
    }
}

```

