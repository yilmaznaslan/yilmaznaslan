## Testing Strategies and Techniques

This guide summarizes several testing approaches and techniques commonly used in modern software development:

- **TDD (Test-Driven Development)** and **BDD (Behavior-Driven Development)**
- **Given-When-Then (GWT)** for structuring tests
- The **Red-Green-Refactor** loop
- **Fuzz testing**
- **Mutation testing**

Use it as a quick reference when designing or reviewing your test strategy.

---

## TDD vs BDD

Both **TDD (Test-Driven Development)** and **BDD (Behavior-Driven Development)** are testing methodologies used in software
development. Both aim to improve software quality and reliability by introducing a structured approach to testing.

In **unit testing**, the focus is on testing small and isolated pieces of code, usually at the function or method level.
This allows for faster feedback and easier debugging of issues. **Assertions** are an important part of unit testing, as
they provide a way to check that the expected output is produced for a given input.

When it comes to using assertions in BDD and TDD, the difference lies in the way the tests are written and the language
used to describe them:

- **BDD** is a more high-level approach to testing that emphasizes the **behavior** of the system rather than the
  implementation details. Tests are written in a more natural language that describes the behavior of the system in a way
  that non-technical stakeholders can understand.

- **TDD** is a more technical approach to testing that emphasizes the correctness of the **implementation**. Tests are
  written before the actual code, and the code is then developed iteratively to pass the tests.

In terms of assertions, both BDD and TDD use assertions to check the expected output of a given input. However,
BDD-style assertions are often more descriptive and focus on the behavior of the system, while TDD-style assertions tend
to be more technical and focus on the implementation details.

In summary, the choice of whether to use BDD or TDD assertions in unit testing depends on the testing approach used, and
the language and style preferred by the development team. Both approaches have their advantages and disadvantages, and
the choice ultimately depends on the specific needs and goals of the project.

---

### TDD: practical example (developer-focused)

Assume we are implementing a simple `Calculator.add(int a, int b)` method.

```java
class CalculatorTest {

    @Test
    void add_shouldReturnSumOfTwoIntegers() {
        // arrange
        Calculator calculator = new Calculator();

        // act
        int result = calculator.add(2, 3);

        // assert
        assertEquals(5, result);
    }
}
```

- **Intent**: Focus on method behavior and correctness.
- **Audience**: Primarily developers.
- **Style**: Technical naming (`add_shouldReturnSumOfTwoIntegers`), often written before implementation as part of the
  Red-Green-Refactor loop.

### BDD: practical example (business-language scenarios)

A BDD-style test typically starts from a human-readable scenario (e.g., using Cucumber).

**Feature file:**

```gherkin
Feature: Calculator addition

  Scenario: Add two positive numbers
    Given a calculator
    When I add 2 and 3
    Then the result should be 5
```

**Step definitions:**

```java
public class CalculatorSteps {

    private Calculator calculator;
    private int result;

    @Given("a calculator")
    public void a_calculator() {
        calculator = new Calculator();
    }

    @When("I add {int} and {int}")
    public void i_add_and(int a, int b) {
        result = calculator.add(a, b);
    }

    @Then("the result should be {int}")
    public void the_result_should_be(int expected) {
        assertEquals(expected, result);
    }
}
```

- **Intent**: Describe behavior in a language that business and technical people can both understand.
- **Audience**: Whole team (product, QA, developers).
- **Style**: Uses scenarios and steps that can be discussed in refinement sessions, then automated.

### Collaboration in BDD

BDD is not something only “business people” do:

- **Domain experts / product owners** help define and review examples and scenarios in natural language.
- **Developers and QA / SDETs** turn those scenarios into executable specifications (step definitions and tests) and
  implement the code to satisfy them.

In practice, TDD is usually **developer-driven**, while BDD is a **collaborative practice** with the automation work still
done by technical team members.

### Given-When-Then (GWT) inside BDD and TDD

**GIVEN-WHEN-THEN (GWT)** is a format for writing automated tests that originated in BDD, but is now widely reused in TDD
tests as well. The format consists of three parts:

- **GIVEN**: Initial context or state of the system (preconditions).
- **WHEN**: The action or event being tested (the behavior or functionality).
- **THEN**: The expected outcome or result of that action.

By using the GWT format, tests become more structured and easier to read and understand. This helps to ensure that tests
are comprehensive and that all aspects of the system's behavior are tested thoroughly.

You can use GWT in:

- **BDD scenarios** (e.g., Gherkin `Given/When/Then` steps).
- **TDD-style unit tests** as comments or method names:

```java
@Test
void givenTwoNumbers_whenAdding_thenReturnTheirSum() {
    // GIVEN
    Calculator calculator = new Calculator();

    // WHEN
    int result = calculator.add(2, 3);

    // THEN
    assertEquals(5, result);
}
```

## Red-Green-Refactor (RGR) Loop

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

