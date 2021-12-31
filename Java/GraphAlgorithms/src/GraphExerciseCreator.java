public class GraphExerciseCreator {
    public static void main(String[] args) {
        // Create one of many provided graphs
        var exercise = createLectureMinimalSpanningTreeGraph();

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
}
