# What is Red-Green-Refactor loop

The Red-Green-Refactor (RGR) loop is a popular software development technique used in Test-Driven Development (TDD) that
involves three phases:

**Red**: Write a failing test. The first step is to write a test case that defines the expected behavior of the code. The
test is expected to fail because the code has not yet been implemented.

**Green**: Write the simplest code that passes the test. In this phase, the developer writes the minimal code necessary to
pass the test. The goal is not to write perfect code, but to get the test to pass.

**Refactor**: Improve the code without changing its behavior. In this phase, the developer improves the code's design,
readability, and performance without changing its behavior. The code should still pass the test after the refactor.

Once the refactor phase is complete, the cycle repeats with a new test case. By following the RGR loop, developers can
build high-quality, maintainable code incrementally while ensuring that each piece of code is thoroughly tested.