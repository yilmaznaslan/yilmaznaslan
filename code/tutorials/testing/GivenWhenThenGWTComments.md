# Title: Given-When-Then (GWT) Comments

GIVEN-WHEN-THEN (GWT) is a format for writing automated tests that is often used in Behavior-Driven Development (BDD)
and Test-Driven Development (TDD) approaches. The format consists of three parts:

**GIVEN**: This part defines the initial context or state of the system, which includes any preconditions that must be
satisfied before the test can be executed. It sets up the necessary conditions for the test to be performed.

**WHEN**: This part defines the specific action or event that is being tested, which is the behavior or functionality that
the system should exhibit. It is the part where the system is triggered to perform an action or respond to an event.

**THEN**: This part defines the expected outcome or result of the action or event. It describes what the system should
produce or how it should behave in response to the action or event defined in the WHEN section.

By using the GWT format, tests become more structured and easier to read and understand. This helps to ensure that tests
are comprehensive and that all aspects of the system's behavior are tested thoroughly.


``` {.java} 
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