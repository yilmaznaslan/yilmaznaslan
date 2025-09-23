# Dependency Injection vs Dependency Injection Framework

## Dependency Injection

Dependency Injection (DI) is a **software design pattern** used in software development to achieve Inversion of Control
(IoC) between
classes and their dependencies. In simple terms, it means that objects are given their dependencies, rather than
constructing them themselves.

### What is it

In traditional programming, a class that needs to perform some function might also create the objects it needs to
perform that function. However, this creates a hard link between the class and the objects it creates, which can make it
harder to modify or test the code.

Dependency Injection flips that around. Instead of a class creating the objects it needs, those objects (or "
dependencies") are created elsewhere in the code and **"injected"** into the class that needs them.

### Types of Dependency Injection

- **Constructor Injection**: The dependencies are provided through a class constructor.
- **Method Injection**: The dependencies are provided through a setter method.

### Why It's Useful

- **Flexibility**: By decoupling the creation of dependencies from the class that uses them, it's easier to change how
  those dependencies are created without having to modify the class itself.

- **Testability**: Dependency Injection allows you to inject mock objects or stubs in place of real objects, making it
  easier to test how a class behaves in isolation from the rest of the system.

- **Maintainability**: It promotes a more modular architecture, making it easier to manage and evolve complex codebases.

### Example

Without DI

```java
public class Car {
    private Engine engine;

    public Car() {
        this.engine = new GasEngine();
    }
}
```

With DI

```java
public class Car {
    private Engine engine;

    public Car(Engine engine) {
        this.engine = engine;
    }
}

public class Main {
    public static void main(String[] args) {
        Engine engine = new ElectricEngine();
        Car car = new Car(engine);
    }
}
```

## Dependency Injection Framework

A Dependency Injection Framework is a tool or library that facilitates implementing Dependency Injection in your
application. It takes care of the boilerplate code needed to create and wire together the dependencies.

Here's what a Dependency Injection Framework generally provides:

- **Automated Wiring**: The framework can automatically create and inject dependencies based on configuration or 
  annotations, reducing the manual work required.

- **Lifecycle Management**: It may offer features to manage the lifecycle of dependencies, including instantiation,
initialization, and destruction.

- **Configuration Management**: Many DI frameworks allow you to configure dependencies using files or annotations, 
  making it easier to manage various configurations or environments.

  
In the context of a large application, manually managing these dependencies could become quite cumbersome. This is where
Dependency Injection Frameworks, like Spring, Guice, Dagger etc., can come into play. The concern of object creation 
and the setting of its dependent
objects is outsourced to a separate framework (for example, a DI container) instead of the object itself being
responsible for managing its dependencies. This can make your code more flexible, testable, and modular.

## Difference between manual dependency management vs DI framework

Let's compare these two approach with an example.
Consider a simple online shopping application, with classes like
ShoppingCart, Product,
Order, PaymentProcessor,
and ShippingService.

Without a DI framework, creating an instance of ShoppingCart might look something like this:

```java
Product product=new Product();
        Order order=new Order(product);
        PaymentProcessor paymentProcessor=new PaymentProcessor();
        ShippingService shippingService=new ShippingService();
        ShoppingCart shoppingCart=new ShoppingCart(order,paymentProcessor,shippingService);
```

As you can see, you need to manually create an instance of every dependency (and their dependencies, and so on), which
can be time-consuming and error-prone. If one class changes its dependencies, you need to manually change every place
where you're creating an instance of that class. Furthermore, this tightly couples your code together, making it harder
to change and test.

Now, let's imagine you're using the **Spring Framework for managing these dependencies**. With Spring, you define your
classes as Spring beans and let Spring handle the wiring. Here's how you might define your classes:

```java

@Component
public class ShoppingCart {
    private Order order;
    private PaymentProcessor paymentProcessor;
    private ShippingService shippingService;

    @Autowired
    public ShoppingCart(Order order, PaymentProcessor paymentProcessor, ShippingService shippingService) {
        this.order = order;
        this.paymentProcessor = paymentProcessor;
        this.shippingService = shippingService;
    }
    // methods...
}

@Component
public class Product {
    // fields, constructors, methods...
}

@Component
public class Order {
    private Product product;

    @Autowired
    public Order(Product product) {
        this.product = product;
    }
    // methods...
}

@Component
public class PaymentProcessor {
    // fields, constructors, methods...
}

@Component
public class ShippingService {
    // fields, constructors, methods...
}

```

In this scenario, all you have to do to get a fully configured ShoppingCart instance is:

```java
ApplicationContext context=new AnnotationConfigApplicationContext("com.mycompany.mypackage");
        ShoppingCart shoppingCart=context.getBean(ShoppingCart.class);
```

Spring's ApplicationContext is responsible for creating the beans and managing their lifecycles. When you ask Spring for
a ShoppingCart, it automatically creates all necessary dependencies (Order, PaymentProcessor, ShippingService), resolves
their own dependencies (Product for Order), and injects these into the ShoppingCart before handing it back to you.

With Spring's DI mechanism, the responsibility of managing dependencies shifts from the developer to the framework,
resulting in code that is cleaner, more testable, and easier to maintain.
