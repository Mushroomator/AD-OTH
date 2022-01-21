# Implementation Details - Python
The code was written using [OpenJDK 17](https://openjdk.java.net/projects/jdk/17/). There are multiple Java Modules, one for all logically related algorithms as well as one for each Lab.

## Table of Contents
- [Implementation Details - Python](#implementation-details---python)
  - [Table of Contents](#table-of-contents)
  - [Implemented algorithms](#implemented-algorithms)
  - [Sorting algorithms](#sorting-algorithms)
  - [String Matching algorithms](#string-matching-algorithms)
  - [Graph algorithms](#graph-algorithms)

## Implemented algorithms
- Maximum partial sum
  - Naive algorithm Θ(n³)
  - Faster algorithm Θ(n²)
  - Divide & Conquer algorithm Θ(n*log(n))
  - Linear algortihm Θ(n)
- Fibonacci numbers
  - recursive
  - iterative
- Towers of Hanoi
- Greatest common divisor (euclidean algorithm)
- Sorting algorithms
  - Insertion sort
  - Selection sort (using minimum)
  - Bubble sort (using minimum)
  - Heap sort
  - Quick sort (pivot element: random/ first)
  - Merge sort (in-situ and ex-situ)
- String matching
  - Naive algorithm
  - Boyer-Moore algorithm (bad-character heuristic)
- Graph algorithms
  - Breadth-first search
  - Depth-first search
  - Minimal spanning tree
    - Prim's algorithm
    - Kruskal's algorithm
  - Shortest path algorithms
    - Dijkstra's algorithm
    - Bellmann-Ford algorithm
    - Floyd-Warshall algorithm

## Sorting algorithms
All sorting algorithms sort in ascending order. 

See [SortingAlgorithms.java](SortingAlgorithms/src/SortingAlgorithms.java)

## String Matching algorithms
See [StringMatcher.java](StringMatching/src/StringMatcher.java)

## Graph algorithms
All algorithms are currently only implemented on a graph with adjacency list.

Please see [Graphs.md](GraphAlgorithms/Graphs.md) for details on the graph algorithms.