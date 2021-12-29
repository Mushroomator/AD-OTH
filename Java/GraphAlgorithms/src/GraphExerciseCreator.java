public class GraphExerciseCreator {
    public static void main(String[] args) {
        // Create one of many provided graphs
        var exercise = createBreadthFirstSearchLectureSampleGraph();

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
