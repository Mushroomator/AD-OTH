package graphs.graph_adj_list_hybrid;

public class GraphExerciseCreator {
    public static void main(String[] args) {
        // Create one of many provided graphs
        var exercise = createSS2018KruskalDijkstraGraph();

        System.out.println("""
                
                +----------------------------------------+
                | Breadth-First Search                   |
                +----------------------------------------+
                """);
        // do breadth first search
        exercise.breadthFirstSearch();

        System.out.println("""
                
                +----------------------------------------+
                | Depth-First Search                     |
                +----------------------------------------+
                """);
        // do depth first search
        exercise.depthFirstSearch(true);

        System.out.println("""
                
                +----------------------------------------+
                | Topological sort                       |
                +----------------------------------------+
                """);
        exercise.topologicalOrder(true);

        System.out.println("""
                
                +----------------------------------------+
                | Minimal Spanning Tree (Kruskal)        |
                +----------------------------------------+
                """);
        exercise.kruskalMinimalSpanningTree();

        System.out.println("""
                
                +----------------------------------------+
                | Minimal Spanning Tree (Prim)           |
                +----------------------------------------+
                """);
        exercise.primMinimalSpanningTree();

        System.out.println("""
                
                +----------------------------------------+
                | Dijkstra's algorithm                   |
                +----------------------------------------+
                """);
        exercise.dijkstra(1);

        System.out.println("""
                
                +----------------------------------------+
                | Bellmann-Ford algorithm                |
                +----------------------------------------+
                """);
        exercise.bellmannFord(1);

        System.out.println("""
                
                +----------------------------------------+
                | Floyd-Warshall algorithm               |
                +----------------------------------------+
                """);
        exercise.floydWarshallAlgorithm();

    }

    public static HybridGraph<Integer> createBreadthFirstSearchLectureSampleGraph(){
        var graph = new HybridGraph<Integer>();
        for(int i = 0; i < 8; i++) graph.addNode(i);
        graph.addEdge(0, 4, false);
        graph.addEdge(0, 1, false);
        graph.addEdge(1, 5, false);
        graph.addEdge(5, 2, false);
        graph.addEdge(5, 6, false);
        graph.addEdge(2, 3, false);
        graph.addEdge(2, 6, false);
        graph.addEdge(6, 3, false);
        graph.addEdge(6, 7, false);
        graph.addEdge(3, 7, false);
        return graph;
    }

    public static HybridGraph<Integer> createLectureSample02Graph(){
        var graph = new HybridGraph<Integer>();
        for(int i = 0; i < 9; i++) graph.addNode(i);
        graph.addEdge(0, 1, false);
        graph.addEdge(0, 4, false);
        graph.addEdge(1, 2, false);
        graph.addEdge(1, 6, false);
        graph.addEdge(1, 8, false);
        graph.addEdge(3, 4, false);
        graph.addEdge(3, 7, false);
        graph.addEdge(4, 5, false);
        graph.addEdge(6, 7, false);
        return graph;
    }

    public static HybridGraph<Integer> createLectureTopologicalOrderSampleGraph(){
        var graph = new HybridGraph<Integer>();
        for(int i = 0; i < 9; i++) graph.addNode(i);
        graph.addEdge(0, 4, true);
        graph.addEdge(1, 0, true);
        graph.addEdge(1, 6, true);
        graph.addEdge(1, 8, true);
        graph.addEdge(2, 1, true);
        graph.addEdge(4, 3, true);
        graph.addEdge(5, 4, true);
        graph.addEdge(6, 7, true);
        graph.addEdge(7, 3, true);
        return graph;
    }

    public static HybridGraph<Integer> createLectureMinimalSpanningTreeGraph(){
        var graph = new HybridGraph<Integer>();
        for(int i = 0; i < 9; i++) graph.addNode(i);
        graph.addEdge(0, 1, 4d, false);
        graph.addEdge(0, 8, 8d, false);
        graph.addEdge(1, 3, 8d, false);
        graph.addEdge(2, 3, 2d, false);
        graph.addEdge(2, 7, 6d, false);
        graph.addEdge(2, 8, 7d, false);
        graph.addEdge(3, 4, 7d, false);
        graph.addEdge(3, 6, 4d, false);
        graph.addEdge(4, 5, 9d, false);
        graph.addEdge(4 ,6,14d, false);
        graph.addEdge(5 ,6,10d, false);
        graph.addEdge(6, 7, 2d, false);
        graph.addEdge(7, 8, 1d, false);
        return graph;
    }

    public static HybridGraph<Integer> createSampleGraph01(){
        var graph = new HybridGraph<Integer>();
        for(int i = 0; i < 6; i++) graph.addNode(i);
        graph.addEdge(0, 2, false);
        graph.addEdge(0, 4, false);
        graph.addEdge(0, 5, false);
        graph.addEdge(1, 4, false);
        graph.addEdge(1, 5, false);
        graph.addEdge(2, 3, false);
        graph.addEdge(2, 4, false);
        graph.addEdge(4, 5, false);
        return graph;
    }

    public static HybridGraph<Integer> createExamPrimGraph(){
        var graph = new HybridGraph<Integer>();
        for(int i = 0; i < 21; i++) graph.addNode(i);
        graph.addEdge(0, 6, 15d, false);
        graph.addEdge(0, 1, 9d, false);
        graph.addEdge(1, 7, 1d, false);
        graph.addEdge(1, 2, 2d, false);
        graph.addEdge(2, 8, 17d, false);
        graph.addEdge(2, 3, 8d, false);
        graph.addEdge(3, 9, 13d, false);
        graph.addEdge(3, 4, 14d, false);
        graph.addEdge(4, 10, 21d, false);
        graph.addEdge(4, 5, 18d, false);
        graph.addEdge(5, 10, 1d, false);
        graph.addEdge(10, 14, 11d, false);
        graph.addEdge(10, 9, 12d, false);
        graph.addEdge(9, 14, 10d, false);
        graph.addEdge(9, 8, 2d, false);
        graph.addEdge(8, 13, 4d, false);
        graph.addEdge(8, 7, 15d, false);
        graph.addEdge(7, 12, 13d, false);
        graph.addEdge(7, 6, 21d, false);
        graph.addEdge(6, 11, 19d, false);
        graph.addEdge(11, 15, 5d, false);
        graph.addEdge(11, 12, 19d, false);
        graph.addEdge(12, 16, 3d, false);
        graph.addEdge(12, 13, 10d, false);
        graph.addEdge(13, 17, 17d, false);
        graph.addEdge(13, 14, 9d, false);
        graph.addEdge(14, 17, 9d, false);
        graph.addEdge(17, 19, 7d, false);
        graph.addEdge(17, 16, 7d, false);
        graph.addEdge(16, 19, 16d, false);
        graph.addEdge(16, 15, 11d, false);
        graph.addEdge(15, 18, 8d, false);
        graph.addEdge(18, 20, 8d, false);
        graph.addEdge(18, 19, 14d, false);
        graph.addEdge(19, 20, 6d, false);
        return graph;
    }

    public static HybridGraph<Integer> createLectureDijkstraAlgorithmGraph(){
        var graph = new HybridGraph<Integer>();
        for(int i = 0; i < 5; i++) graph.addNode(i);
        graph.addEdge(0, 1,  3d, true);
        graph.addEdge(0, 3,  5d, true);
        graph.addEdge(1, 2,  6d, true);
        graph.addEdge(1, 3,  1d, true);
        graph.addEdge(2, 4,  1d, true);
        graph.addEdge(3, 1,  1d, true);
        graph.addEdge(3, 2,  4d, true);
        graph.addEdge(3, 4,  6d, true);
        graph.addEdge(4, 0,  3d, true);
        graph.addEdge(4, 2,  7d, true);
        return graph;
    }

    public static HybridGraph<Integer> createLectureBellmannFordGraph(){
        var graph = new HybridGraph<Integer>();
        for(int i = 0; i < 5; i++) graph.addNode(i);
        graph.addEdge(0, 1, 6d, true);
        graph.addEdge(0, 3, 7d, true);
        graph.addEdge(1, 2, 5d, true);
        graph.addEdge(1, 3, 8d, true);
        graph.addEdge(1, 4, -4d, true);
        graph.addEdge(2, 1, -2d, true);
        graph.addEdge(3, 2, -3d, true);
        graph.addEdge(3, 4, 9d, true);
        graph.addEdge(4, 0, 2d, true);
        graph.addEdge(4, 2, 7d, true);
        return graph;
    }

    public static HybridGraph<Integer> createLectureFloydWarshallGraph(){
        var graph = new HybridGraph<Integer>();
        for(int i = 1; i <= 5; i++) graph.addNode(i);
        graph.addEdge(1, 2, 3d, true);
        graph.addEdge(1, 3, 8d, true);
        graph.addEdge(1, 5, -4d, true);
        graph.addEdge(2, 4, 1d, true);
        graph.addEdge(2, 5, 7d, true);
        graph.addEdge(3, 2, 4d, true);
        graph.addEdge(4, 1, 2d, true);
        graph.addEdge(4, 3, -5d, true);
        graph.addEdge(5, 4, 6d, true);
        return graph;
    }

    public static HybridGraph<Integer> createWikipediaFloydWarshallGraph(){
        var graph = new HybridGraph<Integer>();
        for (int i = 1; i <= 4; i++) graph.addNode(i);
        graph.addEdge(1, 3, -2d, true);
        graph.addEdge(2, 1, 4d, true);
        graph.addEdge(2, 3, 3d, true);
        graph.addEdge(3, 4, 2d, true);
        graph.addEdge(4, 2, -1d, true);
        return graph;
    }

    public static HybridGraph<Integer> createSS2018KruskalDijkstraGraph(){
        var graph = new HybridGraph<Integer>();
        for (int i = 1; i <= 8; i++) graph.addNode(i);
        graph.addEdge(1, 2, 28d, false);
        graph.addEdge(1, 3, 24d, false);
        graph.addEdge(1, 4, 26d, false);
        graph.addEdge(1, 6, 3d, false);
        graph.addEdge(2, 3, 2d, false);
        graph.addEdge(2, 8, 1d, false);
        graph.addEdge(3, 4, 6d, false);
        graph.addEdge(4, 5, 4d, false);
        graph.addEdge(5, 6, 5d, false);
        graph.addEdge(6, 7, 23d, false);
        graph.addEdge(6, 8, 22d, false);
        graph.addEdge(7, 8, 7d, false);
        return graph;
    }

    public static HybridGraph<Integer> createSS2017PrimGraph(){
        var graph = new HybridGraph<Integer>();
        for (int i = 0; i <= 16; i++) graph.addNode(i);
        graph.addEdge(0, 13, 3d, false);
        graph.addEdge(0, 15, 11d, false);
        graph.addEdge(1, 13, 2d, false);
        graph.addEdge(1, 2, 4d, false);
        graph.addEdge(2, 3, 5d, false);
        graph.addEdge(3, 13, 21d, false);
        graph.addEdge(3, 4, 13d, false);
        graph.addEdge(3, 8, 20d, false);
        graph.addEdge(4, 5, 14d, false);
        graph.addEdge(5, 8, 6d, false);
        graph.addEdge(5, 6, 22d, false);
        graph.addEdge(6, 7, 19d, false);
        graph.addEdge(7, 8, 12d, false);
        graph.addEdge(8, 9, 25d, false);
        graph.addEdge(8, 14, 18d, false);
        graph.addEdge(9, 10, 23d, false);
        graph.addEdge(9, 12, 15d, false);
        graph.addEdge(10, 11, 17d, false);
        graph.addEdge(11, 12, 5d, false);
        graph.addEdge(11, 16, 16d, false);
        graph.addEdge(12, 14, 10d, false);
        graph.addEdge(12, 15, 7d, false);
        graph.addEdge(13, 14, 8d, false);
        graph.addEdge(15, 16, 1d, false);
        return graph;
    }
}


