# Spring Context

### Example code

```java

@Configuration
public class ApplicationConfig {

    @Bean
    public CustomerRepository customerRepository() {
        return new InMemoryCustomerRepository();
    }

    @Bean
    public CustomerService customerService() {
        return new CustomerService(customerRepository());
    }

    @Bean
    public OrderRepository orderRepository() {
        return new InMemoryOrderRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderService(orderRepository(), customerService());
    }
}
```

### Annotations

#### @Configuration

This annotation is used at the class level and indicates that the class can be used by the Spring IoC container to
define beans. A bean is an object that is managed and instantiated by the Spring container.
This approach allows you to write your application configuration without relying on XML.

#### @Bean

This annotation is used at the method level and indicates that a method produces a bean to be managed by the Spring
container. The method's return value becomes a bean in the Spring application context, and you can then inject it into
other parts of your application where needed.

- **Dependency Injection**: Beans defined with `@Bean` can be injected into other beans using `@Autowired`,
  constructor injection, or method injection.
- **Lifecycle Management**: You can define initialization and destruction callbacks by using the initMethod and
  destroyMethod attributes of the `@Bean annotation. For example, `@Bean(initMethod = "init", destroyMethod =
  "cleanup")`.
- **Conditional Beans**: By using annotations like `@Conditional`, `@ConditionalOnProperty`, or `@ConditionalOnClass`,
  you
  can create beans that are only instantiated under specific conditions.

#### @Component

- -What It Does: Marks a class as a Spring bean to be automatically discovered and managed by the Spring container.
- How It Works: When classpath scanning is enabled, Spring looks for classes annotated with @Component and registers
  them as beans.

#### @Service

We mark beans with @Service to indicate that they're holding the business logic. Besides being used in the service
layer, there isn't any other special use for this annotation.

##### Note

Both `@Component` and `@Service`  annotations marks a class as a Spring bean to be automatically discovered and
managed by the Spring container.

#### Summary:

- **@Configuration** tells Spring that this class contains bean definitions.
- **@Bean** tells Spring that the method's return value should be registered as a bean in the Spring application
  context.
