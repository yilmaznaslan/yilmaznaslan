# Understanding Parameter Passing Mechanisms in Programming Languages

When working with functions or methods in programming languages, it's essential to understand how parameters are passed
to them. This concept, known as parameter passing mechanisms, varies between languages and can affect how your code
behaves. In this article, we'll explore two common parameter passing mechanisms: pass by value and pass by reference.

## Pass by Value

* In Java, primitive data types (such as int, float, char, etc.) are passed by value.

* When you pass a primitive type to a method, a copy of the value is passed to the method, and any modifications made to
  the parameter inside the method do not affect the original value outside the method.

```java
void modifyValue(int num){
  num=num*2;
}

int x=10;
modifyValue(x);
System.out.println(x); // Output: 10 (unchanged)

```

## Pass by Reference:

* In Java, objects (including arrays) are passed by reference to their memory locations.
* When you pass an object to a method, you're actually passing a reference to the object's memory location. This means
  changes made to the object's state inside the method will affect the original object outside the method.

```java
class MyClass {
    int value;
}
MyClass myObj = new MyClass();

void modifyObject(MyClass obj) {
    obj.value = obj.value * 2;
}
myObj.value = 10;
modifyObject(myObj);
System.out.println(myObj.value); // Output: 20 (modified)

```

## Reference
- https://www.javadude.com/articles/passbyvalue.htm