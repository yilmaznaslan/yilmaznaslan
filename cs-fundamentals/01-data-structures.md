# Data Structures

## What is a data structure

A data strucure a way to organize and store the data so that we can perform operations differently like **insert, delete, search, traverse**

### 1) Linear Data Structures

### 2) Non-Linear Data Structures

- Tree Data Structure
- Graph Data Structure

An abstract data type (ADT) defines what operations are supported ( List suppors insert, delete, remove) and concerete data strucurte is one way to implement that ADT(an array or linked list for a list)

1. **Arrays**: An array is a container object that holds a **fixed** number of values of a single type contigously. The length of an array is established when the array is created. After creation, its length is **fixed**.

- Operations:
- - Access by index. Time complexity is **O(1)**
- - Insert / Delete in the middle are **O(n)**, insert / delete at the end **O(1)**

- Use when you need fast index based acces and a small, fixed frinedy collection

```
   int[] ages = new int[5];
```

2. **ArrayList(Dynamic Array)**: A dynamic array implementation that can grow or shrink in size. It provides constant-time access and amortized constant-time addition and removal.

- Operations
- - Accesss by index -> **O(1)**
- - Insert / Delete in the middle -> **O(n)**
- - Append -> **O(1)**

* It can be helpful in programs where lots of manipulation in the array is needed.
* It is primaryl used for appending accosioanl resize use cases

- Use \*_ArrayList_ when yourprimary operations are retrieving or setting elements based on an index.

- ArrayList inherits AbstractList class and implements List interface.
- ArrayList can not be used for primitive types, like int, char, etc. We need a wrapper class for such cases

3. **LinkedList(Singly and Doubly**:

- What it is: Elements (nodes) are not stored in conitgoues memory like array, each node contains data and pointers to next (and optionl aprevious) node.

- Operations:
- - Access By Index -> **O(n)**
- - Insert / Delete -> **O(1)**

- Use **LinkedList** when is an excellent choice when you need to perform frequent insertions and deletions at both ends or in the
  middle of the list, and when you don't need to access elements by index often. If you require constant-time access to
  elements, an ArrayList might be a better choice. Your primary operations involve iterating through the list, such as with a list iterator.

Since a LinkedList acts as a dynamic array and we do not have to specify the size while creating it, the size of the
list automatically increases when we dynamically add and remove items.

4. **Stack:** A Last-In-First-Out **(LIFO)** data structure.

- Operations:
- - Push -> **O(1)**
- - Pop(remove top element) -> **O(1)**
- - Peek (look at the top element without removing it) -> **O(1)**

- Use cases for **Stack** are Undo / Redo functionality in peditory

5. **Queue**: A First-In-First-Out **(FIFO)** data structure.

- Supports;
- - Enqueue(add element at the back) -> **O(1)**
- - Dequeue(rmove and the element from the front of the queue) -> **O(1)**
- - Peek(look at the front)

Use when processing tasks in order

HashSet: A collection that does not allow duplicate elements. Provides O(1) average time for add, remove, and contains
operations.

6. **HashTable** (Java implemetation HashMap): A key-value map that uses hashing to store data. A has function is used to convert key into ao number and that number is mapped to an index in an internal array. The mapping is done like this

The index in a hash table is calculated by taking the result of the hash function and applying the modulus operation with the array size.. Sometimes in the same slot, multiple slements might need to be stored called 'collisions'. This can be handled by searchign in the list of the slot for example

- Supports;
- - Direct Access(get), Insert, Delete -> **O(1)**

- Use when oyu need a fast look by key

**HashFunction**
A **Hash Function** takes a key(String, number or Object) and converts that key into an integer(hash code). The hash funbctions are **Determminits**, so same input results saem output. They hsoul dbe fast

- **Collision-probe but minited**. Two different inputs might hast to same value but good function sminimite this
  String< -> Sum of ASCII codes( Simple but weak)
  Objeets -> combine the ahs of each field

7. **Tree's'** Non-Linear

A Tree is a hierarchical data structure made up of nodes. Each noce contains a **data** and **linkgs(edges)** to chiuld nodes. The top node is called the root.

Types
**Binary Tree** -> Each node has at most two children(left & right)

        10
       /  \
      25    5
     / \     \
    3   7     2

**Binary Search Tree (BST)** → A special binary tree with ordering property: Left child < Parent < Right child. This makes the **search, insert, delete** efficient -> **O(log n)**

        10
       /  \
      5    15
     / \     \
    3   7     20

8. Graphs: Data structures that represent relationships between nodes (vertices) and edges. Can be represented as adjacency
   lists or adjacency matrices.

## Importance of DSA knowledge in software development

Knowledge of Data Structures and Algorithms (DSA) is indeed useful in software development jobs. While it may not be
necessary to know every single data structure or algorithm in detail, having a solid understanding of the fundamentals
can greatly benefit your career as a software developer. Here are some reasons why DSA knowledge is valuable:

1. **Problem Solving**: Understanding various data structures and algorithms helps you approach problems more
   effectively. You'll be able to analyze and determine the best-suited data structure or algorithm to solve a
   particular problem, leading to more efficient and optimized solutions.

2. **Performance Optimization**: A strong foundation in DSA allows you to identify performance bottlenecks in your
   code and select the most efficient data structures or algorithms to improve the performance of your software.

3. **Scalability**: As software applications grow in size and complexity, using the right data structures and algorithms
   becomes critical to ensuring scalability. Good DSA knowledge will help you design software systems that can handle
   large amounts of data and users without compromising performance.

4. **Code Maintainability**: Understanding the underlying data structures and algorithms used in your codebase makes it
   easier
   for you and your team to maintain and improve the code. This knowledge helps to ensure that the code is modular,
   reusable, and easy to understand.

5. Job Interviews: DSA knowledge is commonly assessed in technical interviews for software development roles. Companies
   often use DSA-related questions to evaluate a candidate's problem-solving skills and their ability to write efficient
   and optimized code.

6. Broad Applicability: Data structures and algorithms are fundamental concepts that apply across various programming
   languages and paradigms. Gaining proficiency in DSA will enable you to work with a wide range of technologies and
   platforms, making you a more versatile developer.

In summary, DSA knowledge is essential for software developers because it equips them with the skills needed to solve
complex problems, optimize performance, and design scalable software systems. Additionally, it helps with code
maintainability and increases your employability in the job market.
