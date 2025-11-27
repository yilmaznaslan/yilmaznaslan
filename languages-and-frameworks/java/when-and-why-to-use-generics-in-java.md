---
title: "When and Why to Use Generics in Java"
date: 2025-11-12
categories: ["Java Best Practices", "Programming Language Fundamentals"]
tags: ["java", "generics", "clean code", "type safety", "reusability"]
description: "Generics make Java code type-safe and reusable — but when do they actually make sense? Here's how and when to use them effectively."
---

# When and Why to use Generics in Java

## What are Generics ?

Generics in Java let you write **classes, interfaces and methods** that can operate on different data types, while still providing type safety.

## Syntax of Generics

You use type parameters like (**T, E, K, V**) inside angle brackets

### Ex 1: Generic Class

A **generic class** is a class that can work with different types of data without having to write a new class for each type. You give it a "placeholder" for a type, and when you create an instance of the class, you tell which actual type to use. This makes the code reusable and type-safe

```java
public class Main {
    private static record Apple(String color, String type){};
    private static record Orange(String country){};


    private static class Fruitbox<T>{
        private final T[] fruits;

        public Fruitbox(T[] fruits){
            this.fruits = fruits;
        };

        public void countAndPrintSize(){
            System.out.println(String.format("Size of the box is: %d",fruits.length));
        };
    }


    public static void main(String[] args){

        final var apple1 = new Apple("","");
        final var apple2 = new Apple("","");

        final var orange1 = new Orange("");

        final var apples = new Apple[]{apple1, apple2};
        final var oranges = new Orange[]{orange1};

        final var appleBox = new Fruitbox<Apple>(apples);
        final var orangeBox = new Fruitbox<Orange>(oranges);

        appleBox.countAndPrintSize();
        orangeBox.countAndPrintSize();
    }

}

```

### Ex 2: Generic Methods

```java
 public class Main {

  private static class Utility {
    public <T> void printArray(T[] arr){
        for(T element: arr){
            System.out.println(element);
        }
    }
  }

  public static void main(String[] args){
    final var utility = new Utility();
    final var arr = new Integer[]{3,6,7};
    String[] cities = {"Berlin", "Hamburg"};
    utility.printArray(arr);
    utility.printArray(cities);
  }

}
```

### Ex: 3 Bounded type Parameters

You can restrcit what types a generic accepts using **bounds**.

Wildcard Uppert bound

```java

  private static class Utility {
    public <T> void printArray(T[] arr){
        for(T element: arr){
            System.out.println(element);
        }
    }

    public void printNumbers(List<? extends Number> arr){
        for(Number i: arr){
        System.out.println(i.intValue()*2);
        }
    }

  }

```

Wildcars using LowerBound

```java

  private static class Utility {
    public <T> void printArray(T[] arr){
        for(T element: arr){
            System.out.println(element);
        }
    }

    public void printNumbers(List<? super Number> arr){
        for(Number i: arr){
        System.out.println(i.intValue()*2);
        }
    }

  }

```

Double Generic

```java
private static class Cache<T>{
    private String keyObj;
    private T valueObj;

    private Map<String, T> storage = new HashMap<String, T>();

    public void put(String key, T value) {
        storage.put(key, value);
    }

    public T get(String key) {
        return storage.get(key);
    }
}

```

The whole sample code below;

```java

 public class Main {
    static private record Apple(String color, String type){};
    static private record Orange(String country){};
  private static class Utility {
    public <T> void printArray(T[] arr){
        for(T element: arr){
            System.out.println(element);
        }
    }

      public void printNumbers(List<? extends Number> arr){
    for(Number i: arr){
    System.out.println(i.intValue()*2);
    }
}

  }
private static class Cache<T>{
    private String keyObj;
    private T valueObj;

    private Map<String, T> storage = new HashMap<String, T>();

    public void put(String key, T value) {
        storage.put(key, value);
    }

    public T get(String key) {
        return storage.get(key);
    }
}

  public static void main(String[] args){
    final var utility = new Utility();
    final var arr = new Integer[]{3,6,7};
    String[] cities = {"Berlin", "Hamburg"};
    utility.printArray(arr);
    utility.printArray(cities);

    List<Number> ages = List.of(23, 4, 5);
    utility.printNumbers(ages);

    Cache<Apple> appleCache = new Cache<Apple>();
      Apple apple1 = new Apple("","");
     appleCache.put("",apple1);
  }

}
```

- Playground : https://leetcode.com/playground/2ymAjk6A
