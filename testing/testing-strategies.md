## Testing Strategies: TDD, BDD, GWT, and Red-Green-Refactor

Both **TDD (Test Driven Development)** and **BDD (Behavior Driven Development)** are testing methodologies used in software
development. Both aim to improve software quality and reliability by introducing a structured approach to testing.

In **unit testing**, the focus is on testing small and isolated pieces of code, usually at the function or method
level. This allows for faster feedback and easier debugging of issues. **Assertions** are an important part of unit
testing, as they provide a way to check that the expected output is produced for a given input.

When it comes to using assertions in BDD and TDD, the difference lies in the way the tests are written and the language
used to describe them. BDD is a more high-level approach to testing that emphasizes the **behavior** of the system rather
than the implementation details. In BDD, tests are written in a more natural language that describes the behavior of the
system in a way that non-technical stakeholders can understand.

In contrast, TDD is a more technical approach to testing that emphasizes the correctness of the **implementation**. In
TDD, tests are written before the actual code is written, and the code is then developed iteratively to pass the tests.

In terms of assertions, both BDD and TDD use assertions to check the expected output of a given input. However,
BDD-style assertions are often more descriptive and focus on the behavior of the system, while TDD-style assertions tend
to be more technical and focus on the implementation details.

In summary, the choice of whether to use BDD or TDD assertions in unit testing depends on the testing approach used, and
the language and style preferred by the development team. Both approaches have their advantages and disadvantages, and
the choice ultimately depends on the specific needs and goals of the project.

---

## Given-When-Then (GWT) Comments

**GIVEN-WHEN-THEN (GWT)** is a format for writing automated tests that is often used in Behavior-Driven Development (BDD)
and Test-Driven Development (TDD) approaches. The format consists of three parts:

- **GIVEN**: This part defines the initial context or state of the system, which includes any preconditions that must be
  satisfied before the test can be executed. It sets up the necessary conditions for the test to be performed.

- **WHEN**: This part defines the specific action or event that is being tested, which is the behavior or functionality that
  the system should exhibit. It is the part where the system is triggered to perform an action or respond to an event.

- **THEN**: This part defines the expected outcome or result of the action or event. It describes what the system should
  produce or how it should behave in response to the action or event defined in the WHEN section.

By using the GWT format, tests become more structured and easier to read and understand. This helps to ensure that tests
are comprehensive and that all aspects of the system's behavior are tested thoroughly.

Example:

```java
@Test
public void testCalculateDiscount() {
    // GIVEN
    int orderTotal = 100;
    Customer customer = new Customer("John Doe");

    // WHEN
    int discount = customer.calculateDiscount(orderTotal);

    // THEN
    assertEquals(10, discount);
}
```

---

## Red-Green-Refactor Loop

The **Red-Green-Refactor (RGR) loop** is a popular software development technique used in Test-Driven Development (TDD) that
involves three phases:

- **Red**: Write a failing test. The first step is to write a test case that defines the expected behavior of the code. The
  test is expected to fail because the code has not yet been implemented.

- **Green**: Write the simplest code that passes the test. In this phase, the developer writes the minimal code necessary to
  pass the test. The goal is not to write perfect code, but to get the test to pass.

- **Refactor**: Improve the code without changing its behavior. In this phase, the developer improves the code's design,
  readability, and performance without changing its behavior. The code should still pass the test after the refactor.

Once the refactor phase is complete, the cycle repeats with a new test case. By following the RGR loop, developers can
build high-quality, maintainable code incrementally while ensuring that each piece of code is thoroughly tested.

---

## Fuzz Testing

**Fuzz testing (fuzzing)** is a technique where a system is tested by providing it with a large amount of **random,
unexpected, or invalid input data** to see how it behaves. The main goals are to:

- **Discover crashes and unexpected behavior** that might indicate bugs or security vulnerabilities.
- **Stress-test input validation and error handling** paths that are often overlooked in normal testing.

Typical fuzz testing workflow:

- **Target selection**: Choose functions or components that parse or process external inputs (e.g., parsers, APIs, protocol
  handlers).
- **Input generation**: Use tools or libraries to generate random or mutated inputs (often based on a known input format).
- **Execution and monitoring**: Run the system with these inputs and monitor for crashes, hangs, or incorrect outputs.

Fuzz testing is especially effective for finding **edge cases**, **memory issues**, and **security vulnerabilities** in code
that handles complex or untrusted input.

---

## Mutation Testing

**Mutation testing** is a technique to evaluate the **quality of your test suite**, rather than the application code itself.
Instead of changing the tests, mutation testing **introduces small changes (mutations) into the production code** and then
runs the existing tests:

- If the tests **fail** after a mutation, the mutation is **killed** (good – the tests detected the change).
- If the tests **still pass**, the mutation **survives** (bad – the tests did not detect the behavioral change).

Examples of typical mutations:

- Changing `>` to `>=` or `==` to `!=`
- Replacing constants (e.g., `1` to `0`)
- Removing method calls or return statements

From the ratio of killed vs. surviving mutants, you get a **mutation score**, which indicates how effective your tests are at
catching defects. This goes beyond code coverage by measuring **how sensitive tests are to real behavior changes**, helping
you identify weak or missing assertions and improve overall test robustness.

