import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class HybridGraph<T extends Comparable<T>> {
    private HashMap<T, HybridNode<T>> nodes;
    private final boolean DIRECTED_DEFAULT = false;
    private final Double WEIGHT_DEFAULT = 1.0d;

    public HybridGraph() {
        this(new HashMap<>());
    }

    public HybridGraph(HashMap<T, HybridNode<T>> nodes) {
        this.nodes = nodes;
    }


    public HashMap<T, HybridNode<T>> getNodes() {
        return nodes;
    }

    public void setNodes(HashMap<T, HybridNode<T>> nodes) {
        this.nodes = nodes;
    }

    public Optional<HybridNode<T>> addNode(T key) {
        if (nodes.get(key) == null) {
            var newNode = new HybridNode<T>(key);
            nodes.put(key, newNode);
            return Optional.of(newNode);
        }
        System.out.printf("Node %s already exists!", key.toString());
        return Optional.empty();
    }


    public void addEdge(T from, T to) {
        addEdge(from, to, WEIGHT_DEFAULT, DIRECTED_DEFAULT);
    }

    public void addEdge(T from, T to, Double weight) {
        addEdge(from, to, weight, DIRECTED_DEFAULT);
    }

    public void addEdge(T from, T to, boolean isDirected) {
        addEdge(from, to, WEIGHT_DEFAULT, isDirected);
    }


    public void addEdge(T from, T to, Double weight, boolean isDirected) {
        var fromNode = nodes.get(from);
        var toNode = nodes.get(to);
        assert fromNode != null;
        assert toNode != null;

        var oneDir = new HybridEdge<T>(from, to, weight);
        fromNode.connect(oneDir);
        if (!isDirected) {
            var otherDir = new HybridEdge<T>(to, from, weight);
            toNode.connect(otherDir);
        }
    }


    public HashMap<T, BfsNode<T>> breadthFirstSearch() {
        var bfsNodes = new HashMap<T, BfsNode<T>>();
        var nodeList = this.nodes.values().stream().toList();
        int stepCounter = 1;
        if (nodeList.size() == 0) {
            System.out.println("No nodes in graph!");
            return bfsNodes;
        }

        for (var node : nodeList) {
            bfsNodes.put(node.getKey(), new BfsNode<>(node, Double.POSITIVE_INFINITY, NodeColor.WHITE, null));
        }

        var queue = new ConcurrentLinkedQueue<BfsNode<T>>();
        // this call is safe since list has at least one element -> hashmap also has one element
        var startNode = bfsNodes.get(nodeList.get(0).getKey());
        startNode.distance = 0d;
        startNode.color = NodeColor.GREY;
        queue.add(startNode);
        printBreadthFirstResult(stepCounter, bfsNodes, queue);

        while (!queue.isEmpty()) {
            var curNode = queue.poll();
            for (var adj : curNode.node.getAdjList().values()) {
                var adjNode = bfsNodes.get(adj.getTo());
                if (adjNode.color == NodeColor.WHITE) {
                    adjNode.distance = curNode.distance + 1;
                    adjNode.color = NodeColor.GREY;
                    adjNode.predecessor = curNode.node.getKey();
                    queue.add(adjNode);
                }
            }
            curNode.color = NodeColor.BLACK;
            stepCounter++;
            printBreadthFirstResult(stepCounter, bfsNodes, queue);
        }
        return bfsNodes;
    }

    /**
     * Print (intermediate) result of breadth-first algorithm.
     *
     * @param step     ID for this step within the breadth-first algorithm
     * @param allNodes all nodes with all information required for DFS sort
     * @param queue    queue with nodes that have yet to be processed
     */
    private void printBreadthFirstResult(int step, HashMap<T, BfsNode<T>> allNodes, Queue<BfsNode<T>> queue) {
        var nodeList = allNodes.values();
        System.out.printf("\nStep %d:\n", step);
        System.out.printf("Queue: %s\n", queue.toString());
        System.out.println("| Key   | Pred  | Dist     | Color |");
        System.out.println("|-------|-------|----------|-------|");
        for (var node : nodeList) {
            System.out.printf("| %5s | %5s | %8.2f | %5s |\n", node.node.getKey().toString(), node.predecessor, node.distance, node.color.name());
        }
    }

    /**
     * Depth-first search algorithm.
     *
     * @param print prints intermediate results to screen if set to true
     * @return nodes and all relevant values which are calculated during DFS search i.e. predecessor, color, first time, last time
     */
    public HashMap<T, DfsNode<T>> depthFirstSearch(boolean print) {
        var dfsNodes = new HashMap<T, DfsNode<T>>();
        var nodeList = this.nodes.values().stream().toList();

        if (nodeList.size() == 0) {
            System.out.println("No nodes in graph!");
            return dfsNodes;
        }

        // Initialize algorithm in O(|V|)
        for (var node : nodeList) {
            dfsNodes.put(node.getKey(), new DfsNode<>(node, NodeColor.WHITE, null, null, null));
        }

        // Use AtomicInteger so the time value mutated in depthFirstSearchVisit() will also be mutated here!
        var time = new AtomicInteger(0);
        for (var curNode : dfsNodes.values()) {
            if (curNode.color == NodeColor.WHITE) {
                depthFirstSearchVisit(dfsNodes, curNode, time, print);
            }
        }
        return dfsNodes;
    }

    private void depthFirstSearchVisit(HashMap<T, DfsNode<T>> allNodes, DfsNode<T> curNode, AtomicInteger time, boolean print) {
        // increase time by 1 (be aware: mutates time object from caller -> that's what we want here)
        curNode.firstTime = time.addAndGet(1);
        curNode.color = NodeColor.GREY;
        if (print) printDepthFirstSearchResult(allNodes);
        // Go through all adjacent nodes
        for (var adj : curNode.node.getAdjList().values()) {
            var adjNode = allNodes.get(adj.getTo());
            if (adjNode.color == NodeColor.WHITE) {
                // Node has not yet been visited
                // --> store this node as its predecessor
                // --> "Go deep": visit this node
                adjNode.predecessor = curNode.node.getKey();
                depthFirstSearchVisit(allNodes, adjNode, time, print);
            }
        }
        // All adjacent nodes have been visited
        // --> this node is "done"
        // --> mark nodes as "BLACK" (=done)
        // --> add the "done" timestamp
        curNode.color = NodeColor.BLACK;
        curNode.lastTime = time.addAndGet(1);
        if (print) printDepthFirstSearchResult(allNodes);
    }

    /**
     * Do topological sort algorithm and prints results to screen
     *
     * @param print prints result to screen if set to true
     * @return nodes in topological order
     */
    public List<DfsNode<T>> topologicalOrder(boolean print) {
        var result = this.depthFirstSearch(false);
        var nodeList = new ArrayList<>(result.values());
        nodeList.sort((o1, o2) -> o2.lastTime.compareTo(o1.lastTime));
        if (print) System.out.println(nodeList);
        return nodeList;
    }

    /**
     * Print (intermediate) result of depth-first algorithm.
     *
     * @param allNodes all nodes with all information required for DFS sort
     */
    private void printDepthFirstSearchResult(HashMap<T, DfsNode<T>> allNodes) {
        var nodeList = allNodes.values();
        System.out.println("\n| Key   | Pred  | Color | FirstT | LastT |");
        System.out.println("|-------|-------|-------|--------|-------|");
        for (var node : nodeList) {
            System.out.printf("| %5s | %5s | %5s | %6s | %5s |\n", node.node.getKey().toString(), node.predecessor, node.color.name(), node.firstTime, node.lastTime);
        }
    }

    /**
     * Important: This implementation does not check if the requirements for the existence of a minimal spanning tree
     *            are fulfilled unexpected results/ error might occur in such case.
     * @return List of edges that build minimal spanning tree.
     */
    public List<HybridEdge<T>> kruskalMinimalSpanningTree(){
        int stepCounter = 0;
        // each spanning tree must consist of exactly |V| - 1 nodes, so set this as the initial capacity
        // so elements are not copied for resizing of array.
        var mst = new ArrayList<HybridEdge<T>>(nodes.size() - 1);
        // create Union-Find data structure
        var uf = new UnionFind<T>();
        // create a set for each node
        nodes.keySet().forEach(uf::createSet);
        // get all edges and sort them by ascending weight
        var edges = this.getEdges();
        // this sort merges using a stable (adapted) merge sort (Variation of TimSort) so O(|E| * log |E|) is guaranteed, which is important here!
        // sort by weight is sufficient, but to apply have the same order as in the lecture also compare the "from" nodes and process smaller nodes first!
        edges.sort(Comparator
                .comparingDouble(HybridEdge<T>::getWeight)
                .thenComparing(HybridEdge<T>::getFrom));
        printKruskalMinimalSpanningTreeResult(edges, uf, mst, stepCounter);
        for(var edge: edges){
            // Note:
            // performance could be optimized here, since isUnion() and merge() do basically the same thing
            // but for reason of simplicity/ accordance to lecture keep it like this

            // Check if a cycle would be formed by checking if both sets have the same representative (are in the same set)
            System.out.printf("Checking if %s and %s can be merged without creating a cycle...\n", edge.getFrom(), edge.getTo());
            if(!uf.isUnion(edge.getFrom(), edge.getTo())){
                // no cycle is formed
                // --> add edge to MST
                // --> merge sets
                mst.add(edge);
                System.out.printf("Merging %s and %s\n", edge.getFrom(), edge.getTo());
                uf.merge(edge.getFrom(), edge.getTo());
            } else System.out.println("Merge would cause a cycle. Checking next edge...");

            stepCounter++;
            printKruskalMinimalSpanningTreeResult(edges, uf, mst, stepCounter);
        }
        return mst;
    }

    private void printKruskalMinimalSpanningTreeResult(List<HybridEdge<T>> sortedEdges, UnionFind<T> uf, List<HybridEdge<T>> mst, int step){
        // print all nodes and their representatives
        System.out.printf("\nStep: %s\n", step);
        printKruskalSpanningTreeResultEdges(sortedEdges,step);
        System.out.println("\nUnion-Find data structure:");
        System.out.println("| Node  | Repr  |");
        System.out.println("|-------|-------|");
        uf.getSets().forEach((nodeKey, node) -> System.out.printf("| %5s | %5s |\n", nodeKey, node.getRep().key));
        System.out.println("\nMinimal Spanning Tree:");
        AtomicReference<Double> mstWeight = new AtomicReference<>(0d);
        mst.forEach(edge -> {
            System.out.println(edge);
            mstWeight.updateAndGet(v -> (double) (v + edge.getWeight()));
        });
        System.out.println("Weight of Minimal Spanning Tree: " + mstWeight.get());
    }

    private void printKruskalSpanningTreeResultEdges(List<HybridEdge<T>> sortedEdges, int step){
        System.out.println("Sorted edges: ");
        var lineSb = new StringBuilder();
        var headerLineSb = new StringBuilder();
        var contentSb = new StringBuilder();
        lineSb.append("+").append("-----------------------|");
        contentSb.append("|").append(" Edge                  |");
        headerLineSb.append("|").append(" Edge index            |");
        for (int i = 0; i < sortedEdges.size(); i++){
            contentSb.append(sortedEdges.get(i)).append(" |");
            lineSb.append("-----------------------|");
            if(i == step) {
                headerLineSb.append("       CURRENT         |");
            }
            else {
                headerLineSb.append("       %5s           |".formatted(i));
            }
        }
        System.out.println(lineSb);
        System.out.println(headerLineSb);
        System.out.println(lineSb);
        System.out.println(contentSb);
        System.out.println(lineSb);
    }

    public List<HybridEdge<T>> getEdges(){
        // assumption: on average each node has 3 edges
        var allEdges = new ArrayList<HybridEdge<T>>(nodes.size() * 3);
        nodes.values().forEach(node -> allEdges.addAll(node.getAdjList().values()));
        return allEdges;
    }
}
