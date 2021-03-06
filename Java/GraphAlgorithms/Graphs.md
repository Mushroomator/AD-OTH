# Graph Algorithms
All graphs can be visualized using [CS Academy Graph Visualizer](https://csacademy.com/app/graph_editor/) and the input provided in the sections below.

## Table of Contents
- [Graph Algorithms](#graph-algorithms)
  - [Table of Contents](#table-of-contents)
  - [Graph Attributes](#graph-attributes)
    - [Algorithms](#algorithms)
  - [Undirected graphs](#undirected-graphs)
    - [createBreadthFirstSearchLectureSampleGraph()](#createbreadthfirstsearchlecturesamplegraph)
      - [Description](#description)
      - [AvlNode Count:](#avlnode-count)
      - [Graph Data:](#graph-data)
    - [createLectureSample02Graph()](#createlecturesample02graph)
      - [Description](#description-1)
      - [AvlNode Count:](#avlnode-count-1)
      - [Graph Data:](#graph-data-1)
    - [createSampleGraph01()](#createsamplegraph01)
      - [AvlNode Count:](#avlnode-count-2)
      - [Graph Data:](#graph-data-2)
    - [createBreadthFirstSearchLectureSampleGraph()](#createbreadthfirstsearchlecturesamplegraph-1)
      - [Description](#description-2)
      - [AvlNode Count:](#avlnode-count-3)
      - [Graph Data:](#graph-data-3)
    - [createExamPrimGraph()](#createexamprimgraph)
      - [Description](#description-3)
      - [AvlNode Count:](#avlnode-count-4)
      - [Graph Data:](#graph-data-4)
  - [Directed graphs](#directed-graphs)
    - [createLectureTopologicalOrderSampleGraph()](#createlecturetopologicalordersamplegraph)
      - [Description](#description-4)
      - [AvlNode Count:](#avlnode-count-5)
      - [Graph Data:](#graph-data-5)
    - [createLectureDijkstraAlgorithmGraph()](#createlecturedijkstraalgorithmgraph)
      - [Description](#description-5)
      - [AvlNode Count:](#avlnode-count-6)
      - [Graph Data:](#graph-data-6)
    - [createLectureBellmannFordGraph()](#createlecturebellmannfordgraph)
      - [Description](#description-6)
      - [AvlNode Count:](#avlnode-count-7)
      - [Graph Data:](#graph-data-7)
    - [createLectureFloydWarshallGraph()](#createlecturefloydwarshallgraph)
      - [Description](#description-7)
      - [AvlNode Count:](#avlnode-count-8)
      - [Graph Data:](#graph-data-8)

## Graph Attributes
Each graph has different attributes which can be seen looking at the graph but are also shown as a badge for each graph here. Have a look at the following table to see all badges and their meaning.

| Badge                                                                                                                                                                                      | Description                                                                                                                                                                                                              |
| ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| [![Directed graph](https://img.shields.io/badge/directed-yes-green.svg)](https://shields.io/) [![Undirected graph](https://img.shields.io/badge/directed-no-red.svg)](https://shields.io/) | Shows whether a graph is directed or not                                                                                                                                                                                 |
| [![Weighted graph](https://img.shields.io/badge/weighted-yes-green.svg)](https://shields.io/) [![Unweighted graph](https://img.shields.io/badge/weighted-no-red.svg)](https://shields.io/) | Shows whether a graph is weighted or not. "unweighted" here means the default weight 1.0 is applied!                                                                                                                     |
| [![Suitable for algorithms](https://img.shields.io/badge/Suitable%20for-BFS%2C%20DFS%2C...-ffffff.svg)](https://GitHub.com/Naereen/ama)                                                    | Shows which algorithms of the implemented algorithms can be applied to this graph. Some algorithms won't work with any graph! See [section "Algorithms"](#algorithms) to see the meaning of the abbreviations used here. |


### Algorithms
| Abbreviation | Name                                      | Prerequisites                                                         |
|--------------|-------------------------------------------|-----------------------------------------------------------------------|
| BFS          | Breadth-First Search                      | - connected (in this implementation)                                  |
| DFS          | Depth-First Search                        |                                                                       |
| MST          | Minimal Spanning Tree                     | - undirected graph<br/> - weighted<br/> - connected                   |
| DIJ          | Dijkstra (single-pair shortest path)      | - No negative weights on edges                                        |
| BMF          | Bellmann-Ford (single-pair shortest path) | - No negative cycles within the graph<br/> - weighted                 |
| FWS          | Floyd-Warshall (all-pairs shortest paths) | - No negative cycles within the graph<br/> - weighted<br/> - directed |


## Undirected graphs
### createBreadthFirstSearchLectureSampleGraph()
[![Is a directed graph?](https://img.shields.io/badge/directed-no-red.svg)](https://shields.io/)
[![Is a weighted graph?](https://img.shields.io/badge/weighted-no-red.svg)](https://shields.io/)
[![Suitable for algorithms](https://img.shields.io/badge/Suitable%20for-BFS%2C%20DFS%2C%20MST-ffffff.svg)](https://GitHub.com/Naereen/ama)

![](graph_images/Lecture_BreadthFirstSearch_Sample.png)
#### Description
Graph as shown in lecture 19 on page 4 for BFS and 13 for DFS.
#### AvlNode Count:
```
8
```
#### Graph Data:
```
0 4
0 1
1 5
5 2
5 6
2 3
2 6
6 3
6 7
3 7
```

### createLectureSample02Graph()
[![Is a directed graph?](https://img.shields.io/badge/directed-no-red.svg)](https://shields.io/)
[![Is a weighted graph?](https://img.shields.io/badge/weighted-no-red.svg)](https://shields.io/)
[![Suitable for algorithms](https://img.shields.io/badge/Suitable%20for-BFS%2C%20DFS%2C%20MST-ffffff.svg)](https://GitHub.com/Naereen/ama)

![](graph_images/Lecture_BFS_DFS_Sample02.png)
#### Description
Graph as shown in lecture 19 on page 22.
#### AvlNode Count:
```
9
```
#### Graph Data:
```
0 1
0 4
1 2
1 6
1 8
3 4
3 7
4 5
6 7
```

### createSampleGraph01()
[![Is a directed graph?](https://img.shields.io/badge/directed-no-red.svg)](https://shields.io/)
[![Is a weighted graph?](https://img.shields.io/badge/weighted-no-red.svg)](https://shields.io/)
[![Suitable for algorithms](https://img.shields.io/badge/Suitable%20for-BFS%2C%20DFS%2C%20MST-ffffff.svg)](https://GitHub.com/Naereen/ama)

![](graph_images/graph01.png)

#### AvlNode Count:
```
6
```
#### Graph Data:
```
0 2
0 4
0 5
1 4
1 5
2 3
2 4
4 5
```

### createBreadthFirstSearchLectureSampleGraph()
[![Is a directed graph?](https://img.shields.io/badge/directed-no-red.svg)](https://shields.io/)
[![Is a weighted graph?](https://img.shields.io/badge/weighted-yes-green.svg)](https://shields.io/)
[![Suitable for algorithms](https://img.shields.io/badge/Suitable%20for-BFS%2C%20DFS%2C%20MST-ffffff.svg)](https://GitHub.com/Naereen/ama)

![](graph_images/Lecture_MST_Sample.png)
#### Description
Graph as shown in lecture 20 on page 13 for Kruskal's algorithm and page 24 for Prim's algorithm.
#### AvlNode Count:
```
9
```
#### Graph Data:
```
0 1 4
0 8 8
1 3 8
2 3 2
2 7 6
2 8 7
3 4 7
3 6 4
4 5 9
4 6 14
5 6 10
6 7 2
7 8 1
```

### createExamPrimGraph()
[![Is a directed graph?](https://img.shields.io/badge/directed-no-red.svg)](https://shields.io/)
[![Is a weighted graph?](https://img.shields.io/badge/weighted-yes-green.svg)](https://shields.io/)
[![Suitable for algorithms](https://img.shields.io/badge/Suitable%20for-BFS%2C%20DFS%2C%20MST-ffffff.svg)](https://GitHub.com/Naereen/ama)

![](graph_images/Exam_SimilarPrimGraph.png)
#### Description
Graph similar to the graph in the exam for the summer term 2021 for the application of Prim's algorithms.
#### AvlNode Count:
```
21
```
#### Graph Data:
```
0 6 15
0 1 9
1 7 1
1 2 2
2 8 17
2 3 8
3 9 13
3 4 14
4 10 21
4 5 18
5 10 1
10 14 11
10 9 12
9 14 10
9 8 2
8 13 4
8 7 15
7 12 13
7 6 21
6 11 19
11 15 5
11 12 19
12 16 3
12 13 10
13 17 17
13 14 9
14 17 9
17 19 7
17 16 7
16 19 16
16 15 11
15 18 8
18 20 8
18 19 14
19 20 6
```

### createSS2018KruskalDijkstraGraph()
[![Is a directed graph?](https://img.shields.io/badge/directed-no-red.svg)](https://shields.io/)
[![Is a weighted graph?](https://img.shields.io/badge/weighted-yes-green.svg)](https://shields.io/)
[![Suitable for algorithms](https://img.shields.io/badge/Suitable%20for-BFS%2C%20DFS%2C%20MST-ffffff.svg)](https://GitHub.com/Naereen/ama)

![](graph_images/SS2018_KruskalDijkstraGraph.png)
#### Description
Graph as used in summer term exam of 2018 for Kruskal and Dijkstra's algorithm.
#### AvlNode Count:
```
8
```
#### Graph Data:
```
1 2 28
1 3 24
1 4 26
1 6 3
2 3 2
2 8 1
3 4 6
4 5 4
5 6 5
6 7 23
6 8 22
7 8 7
```

### createSS2017PrimGraph()
[![Is a directed graph?](https://img.shields.io/badge/directed-no-red.svg)](https://shields.io/)
[![Is a weighted graph?](https://img.shields.io/badge/weighted-yes-green.svg)](https://shields.io/)
[![Suitable for algorithms](https://img.shields.io/badge/Suitable%20for-BFS%2C%20DFS%2C%20MST-ffffff.svg)](https://GitHub.com/Naereen/ama)

![](graph_images/SS2017_PrimGraph.png)
#### Description
Graph as used in summer term exam of 2018 for Kruskal and Dijkstra's algorithm.
#### AvlNode Count:
```
17
```
#### Graph Data:
```
0 13 3
0 15 11
1 13 2
1 2 4
2 3 5
3 13 21
3 4 13
3 8 20
4 5 14
5 8 6
5 6 22
6 7 19
7 8 12
8 9 25
8 14 18
9 10 23
9 12 15
10 11 17
11 12 5
11 16 16
12 14 10
12 15 7
13 14 8
15 16 1
```



## Directed graphs
### createWikipediaFloydWarshall()
[![Is a directed graph?](https://img.shields.io/badge/directed-yes-green.svg)](https://shields.io/)
[![Is a weighted graph?](https://img.shields.io/badge/weighted-yes-green.svg)](https://shields.io/)
[![Suitable for algorithms](https://img.shields.io/badge/Suitable%20for-BFS%2C%20DFS%2C%20MST-ffffff.svg)](https://GitHub.com/Naereen/ama)

![](graph_images/Wikipedia_FloydWarshallAlgorithm.png)
#### Description
Simple graph from [Wikipedia](https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm) used to show how Floyd-Warshall algorithm works.
#### AvlNode Count:
```
4
```
#### Graph Data:
```
1 3 -2
2 1 4
2 3 3
3 4 2
4 2 -1
```

### createLectureTopologicalOrderSampleGraph()
[![Is a directed graph?](https://img.shields.io/badge/directed-yes-green.svg)](https://shields.io/)
[![Is a weighted graph?](https://img.shields.io/badge/weighted-no-red.svg)](https://shields.io/)
[![Suitable for algorithms](https://img.shields.io/badge/Suitable%20for-BFS%2C%20DFS-ffffff.svg)](https://GitHub.com/Naereen/ama)

![](graph_images/Lecture_Topological_Order_Sample.png)
#### Description
Graph as shown in lecture 21 on page 11.
#### AvlNode Count:
```
9
```
#### Graph Data:
```
0 4
1 0
1 6
1 8
2 1
4 3
5 4
6 7
7 3

```

### createLectureDijkstraAlgorithmGraph()
[![Is a directed graph?](https://img.shields.io/badge/directed-yes-green.svg)](https://shields.io/)
[![Is a weighted graph?](https://img.shields.io/badge/weighted-yes-green.svg)](https://shields.io/)
[![Suitable for algorithms](https://img.shields.io/badge/Suitable%20for-BFS%2C%20DFS%2C%20DIJ%2C%20BMF-ffffff.svg)](https://GitHub.com/Naereen/ama)

![](graph_images/Lecture_DijkstraAlgorithm.png)
#### Description
Graph as shown in lecture 19 on page 26.
#### AvlNode Count:
```
5
```
#### Graph Data:
```
0 1 3
0 3 5
1 2 6
1 3 1
2 4 1
3 1 1
3 2 4
3 4 6
4 0 3
4 2 7
```

### createLectureBellmannFordGraph()
[![Is a directed graph?](https://img.shields.io/badge/directed-yes-green.svg)](https://shields.io/)
[![Is a weighted graph?](https://img.shields.io/badge/weighted-yes-green.svg)](https://shields.io/)
[![Graph contains negative edge weights?](https://img.shields.io/badge/negative%20edges-yes-green.svg)](https://shields.io/)
[![Graph contains negative cycle?](https://img.shields.io/badge/negative%20cycle-no-red.svg)](https://shields.io/)
[![Graph strongly connected?](https://img.shields.io/badge/strongly%20connected-yes-green.svg)](https://shields.io/)
[![Suitable for algorithms](https://img.shields.io/badge/Suitable%20for-BFS%2C%20DFS%2C%20BMF%2C%20FWS-ffffff.svg)](https://GitHub.com/Naereen/ama)

![](graph_images/Lecture_BellmannFord.png)
#### Description
Graph as shown in lecture 21 on page 21.
#### AvlNode Count:
```
5
```
#### Graph Data:
```
0 1 6
0 3 7
1 2 5
1 3 8
1 4 -4
2 1 -2
3 2 -3
3 4 9
4 0 2
4 2 7
```

### createLectureFloydWarshallGraph()
[![Is a directed graph?](https://img.shields.io/badge/directed-yes-green.svg)](https://shields.io/)
[![Is a weighted graph?](https://img.shields.io/badge/weighted-yes-green.svg)](https://shields.io/)
[![Graph contains negative edge weights?](https://img.shields.io/badge/negative%20edges-yes-green.svg)](https://shields.io/)
[![Graph contains negative cycle?](https://img.shields.io/badge/negative%20cycle-no-red.svg)](https://shields.io/)
[![Graph strongly connected?](https://img.shields.io/badge/strongly%20connected-yes-green.svg)](https://shields.io/)
[![Suitable for algorithms](https://img.shields.io/badge/Suitable%20for-BFS%2C%20DFS%2C%20BMF%2C%20FWS-ffffff.svg)](https://GitHub.com/Naereen/ama)

![](graph_images/Lecture_FloydWarshall_Sample.png)
#### Description
Graph as shown in lecture 22 on page 22.
#### AvlNode Count:
```
5
```
#### Graph Data:
```
1 2 3
1 3 8
1 5 -4
2 4 1
2 5 7
3 2 4
4 1 2
4 3 -5
5 4 6
```

