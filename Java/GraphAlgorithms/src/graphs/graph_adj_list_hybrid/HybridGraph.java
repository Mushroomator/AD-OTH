package graphs.graph_adj_list_hybrid;


import union_find.UnionFind;
import union_find.UnionFindLecture;

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

    public List<HybridEdge<T>> getEdges() {
        // assumption: on average each node has 3 edges
        var allEdges = new ArrayList<HybridEdge<T>>(nodes.size() * 3);
        nodes.values().forEach(node -> allEdges.addAll(node.getAdjList().values()));
        return allEdges;
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
     * are fulfilled unexpected results/ error might occur in such case.
     *
     * @return List of edges that build minimal spanning tree.
     */
    public List<HybridEdge<T>> kruskalMinimalSpanningTree() {
        int stepCounter = 0;
        // each spanning tree must consist of exactly |V| - 1 nodes, so set this as the initial capacity
        // so elements are not copied for resizing of array.
        var mst = new ArrayList<HybridEdge<T>>(nodes.size() - 1);
        // create Union-Find data structure (choose either UnionFindLecture() or UnionFindOptimized() (see ))
        UnionFind<T> uf = new UnionFindLecture<T>();
        // create a set for each node
        nodes.keySet().forEach(uf::createSet);
        // get all edges and sort them by ascending weight
        var edges = this.getEdges();
        // this sort merges using a stable (adapted) merge sort (Variation of TimSort) so O(|E| * log |E|) is guaranteed, which is important here!
        // sort by weight is sufficient, but to have the same order as in the lecture also compare the "from" nodes and process smaller nodes first!
        edges.sort(Comparator
                .comparingDouble(HybridEdge<T>::getWeight)
                .thenComparing(HybridEdge<T>::getFrom));
        printKruskalMinimalSpanningTreeResult(edges, uf, mst, stepCounter);
        for (var edge : edges) {
            // Note:
            // performance could be optimized here, since isUnion() and merge() do basically the same thing
            // (asymptotically identical, but both isUnion() and merge() take O(log n) so in total O(2 * log n); actually no need to traverse through inverse tree twice -> do merge within is union call if required or do not merge)
            // With the UnionFindOptimized<T> this is not a problem as due to path shortening the first call in WC will take O(log n), and the second call will be in O(1), as all nodes on the path have been appended to the inverse tree root.
            // but for reason of simplicity/ accordance to lecture keep it like this

            // Check if a cycle would be formed by checking if both sets have the same representative (are in the same set)
            System.out.printf("Checking if %s and %s can be merged without creating a cycle...\n", edge.getFrom(), edge.getTo());
            if (!uf.isUnion(edge.getFrom(), edge.getTo())) {
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

    private void printKruskalMinimalSpanningTreeResult(List<HybridEdge<T>> sortedEdges, UnionFind<T> uf, List<HybridEdge<T>> mst, int step) {
        // print all nodes and their representatives
        System.out.printf("\nStep: %s\n", step);
        printKruskalSpanningTreeResultEdges(sortedEdges, step);
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

    private void printKruskalSpanningTreeResultEdges(List<HybridEdge<T>> sortedEdges, int step) {
        System.out.println("Sorted edges: ");
        var lineSb = new StringBuilder();
        var headerLineSb = new StringBuilder();
        var contentSb = new StringBuilder();
        lineSb.append("+").append("-----------------------|");
        contentSb.append("|").append(" Edge                  |");
        headerLineSb.append("|").append(" Edge index            |");
        for (int i = 0; i < sortedEdges.size(); i++) {
            contentSb.append(sortedEdges.get(i)).append(" |");
            lineSb.append("-----------------------|");
            if (i == step) {
                headerLineSb.append("       CURRENT         |");
            } else {
                headerLineSb.append("       %5s           |".formatted(i));
            }
        }
        System.out.println(lineSb);
        System.out.println(headerLineSb);
        System.out.println(lineSb);
        System.out.println(contentSb);
        System.out.println(lineSb);
    }


    /**
     * Calculate minimal spanning tree in a graph using Prim's algorithm.
     *
     * Important: This implementation does not check if the requirements for the existence of a minimal spanning tree
     * are fulfilled unexpected results/ error might occur in such case.
     *
     * @return List of edges that form the minimal spanning tree
     */
    public List<HybridEdge<T>> primMinimalSpanningTree() {
        int stepCounter = 0;
        var mst = new LinkedList<HybridEdge<T>>();
        var unprocessedNodes = new HashSet<T>();
        var allNodes = new HashMap<T, PrioNode<T>>();

        var nodeList = this.nodes.values().stream().toList();
        if (nodeList.size() < 1) {
            System.out.println("No nodes in graph!");
            return mst;
        }
        var first = nodeList.get(0);
        for (var node : nodeList) {
            unprocessedNodes.add(node.getKey());
            allNodes.put(node.getKey(), new PrioNode<>(Double.POSITIVE_INFINITY, node, null));
        }
        // set first nodes priority to zero
        var startNode = new PrioNode<>(0d, first, null);
        allNodes.put(first.getKey(), startNode);
        var prioQueue = new PriorityQueue<PrioNode<T>>(allNodes.values());
        printPrimSpanningTreeResultEdges(mst, stepCounter);
        System.out.printf("Start node: %s\n", startNode);
        while (!prioQueue.isEmpty()) {
            // get node which can be connected with the lightest weight (takes O(log |V|) as Heap must be recreated)
            var leastWeightNode = prioQueue.poll();
            // loop over all neighbors of this node
            var adjList = leastWeightNode.node.getAdjList();
            for (var adj : adjList.values()) {
                // lookup the corresponding PrimNode to the HybridNode in O(1) (PrimNode has just the fields relevant for Prim's algorithm; HybridNode just the key information relevant for a graph)
                var adjPrimNode = allNodes.get(adj.getTo());
                // lookup in set in O(1) if the node is still in the heap and has a smaller distance
                if (unprocessedNodes.contains(adj.getTo()) && adj.getWeight() < adjPrimNode.getDistance()) {
                    // remove node from priority queue and insert a new node for this node with the updated priority
                    // there is no method decreaseKey()! --> take O(log |V|)
                    prioQueue.remove(adjPrimNode);
                    // Update priority and predecessor
                    var updatedPrimNode = new PrioNode<>(adj.getWeight(), adjPrimNode.node, leastWeightNode.node);
                    // update priority queue
                    prioQueue.add(updatedPrimNode);
                    // update map with all keys so predecessor is updated there as well
                    allNodes.put(adjPrimNode.node.getKey(), updatedPrimNode);
                }
            }
            // remove the node, which has already been removed from the heap from the set of unprocessed nodes
            unprocessedNodes.remove(leastWeightNode.node.getKey());
            // get edge which connects the least weighted node to the MST
            if(leastWeightNode.predecessor != null){
                // get adjacent nodes of predecessor...
                var predAdjList = leastWeightNode.predecessor.getAdjList();
                // and get the edge that connected the least weight node to the MST
                var cheapestEdge = predAdjList.get(leastWeightNode.node.getKey());
                // add edge to MST
                mst.add(cheapestEdge);
            }
            stepCounter++;
            printPrimSpanningTreeResultEdges(mst, stepCounter);
        }
        return mst;
    }

    /**
     * Print (partial) result of Prim's minimal spanning tree algorithm.
     * @param mst Edges of minimal spanning tree
     * @param step step within the algorithm
     */
    private void printPrimSpanningTreeResultEdges(List<HybridEdge<T>> mst, int step) {
        AtomicReference<Double> mstWeight = new AtomicReference<>(0d);
        System.out.printf("Step %d\n", step);
        System.out.println("\nMinimal Spanning Tree:");
        mst.forEach(edge -> {
            System.out.println(edge);
            mstWeight.updateAndGet(v -> (double) (v + edge.getWeight()));
        });
        System.out.printf("Weight of Minimal Spanning Tree: %s\n", mstWeight);
    }



    /**
     * Single-pair shortest path with Dijkstra's algorithm.
     * @param sourceKey key of start node
     * @return list of edges that
     */
    public void dijkstra(T sourceKey){
        int stepCounter = 0;
        var unprocessedNodes = new HashSet<T>(nodes.size());
        var allNodes = new HashMap<T, PrioNode<T>>();
        nodes.values().forEach(it -> {
            unprocessedNodes.add(it.getKey());
            allNodes.put(it.getKey(), new PrioNode<T>(Double.POSITIVE_INFINITY, nodes.get(it.getKey()), null));
        });
        var startNode = nodes.get(sourceKey);
        if(startNode == null) throw new IllegalArgumentException("Source node %s does not exist.\n".formatted(sourceKey));

        // set distance of start node to 0 and no predecessor
        allNodes.put(startNode.getKey(), new PrioNode<>(0d, nodes.get(startNode.getKey()), null));

        // build priority queue for all values
        var prioQueue = new PriorityQueue<>(allNodes.values());


        printDijkstraAlgorithmResult(prioQueue, allNodes, stepCounter);
        while (!prioQueue.isEmpty()){
            stepCounter++;
            // get node with minimum distance (and remove that node from the priority queue)
            var minNodePrio = prioQueue.poll();
            var prioNode = allNodes.get(minNodePrio.node.getKey());

            for (var edgeToAdjNode : minNodePrio.node.getAdjList().values()){
                var adjNode = allNodes.get(edgeToAdjNode.getTo());
                if(unprocessedNodes.contains(adjNode.node.getKey()) && minNodePrio.distance + edgeToAdjNode.getWeight() < adjNode.distance){
                    // a shorter path has been found (-> update distance and predecessor)
                    prioQueue.remove(adjNode);
                    var shorterPathLen = minNodePrio.distance + edgeToAdjNode.getWeight();
                    // updated node (new predecessor, new shortest distance)
                    var newNode = new PrioNode<>(shorterPathLen, adjNode.node, minNodePrio.node);
                    prioQueue.add(newNode);

                    // only to be able to print everything out: update allNodes so for each node the latest distance can easily be retrieved
                    allNodes.put(newNode.node.getKey(), newNode);
                }
            }

            unprocessedNodes.remove(minNodePrio.node.getKey());
            printDijkstraAlgorithmResult(prioQueue, allNodes, stepCounter);
        }
    }

    private void printDijkstraAlgorithmResult(PriorityQueue<PrioNode<T>> priorityQueue, HashMap<T, PrioNode<T>> allNodes, int step){
        System.out.printf("\nStep %s\n", step);
        // Heap is not sorted! Smallest element is on top but that is it (here sort so that nodes are in sorted by their priority and lexicographically if the priority is the same)
        // This is just so that we conform to standards defined within the lecture
        System.out.printf("Heap: %s\n", priorityQueue.stream().sorted().toList());

        System.out.print("""
        | Node  | Dist      | Pred  |
        |-------|-----------|-------|
        """);

        allNodes.forEach((key, value) -> System.out.printf("| %5s | %9s | %5s |\n", key, value.distance, value.predecessor != null ? value.predecessor.getKey(): "-" ));
    }

    public void bellmannFord(T sourceNodeKey){
        int stepCounter = 0;
        // set initial capacity to number of nodes so no resizing of hashmap needs to be done (load factor will be applied in constructor)
        var allNodes = new HashMap<T, PrioNode<T>>(nodes.size());
        nodes.values().forEach(it -> allNodes.put(it.getKey(), new PrioNode<>(Double.POSITIVE_INFINITY, it, null)));

        var startNode = nodes.get(sourceNodeKey);
        if(startNode == null) throw new IllegalArgumentException("Source node %s does not exist.\n".formatted(sourceNodeKey));
        // set distance of start node to 0 and no predecessor
        allNodes.put(startNode.getKey(), new PrioNode<>(0d, nodes.get(startNode.getKey()), null));

        var edges = getEdges();
        // sort edges lexicographically
        // this step is not required for the algorithm, but in order to adhere to the order defined in the lecture
        // edges should be sorted by keys of the "from"-nodes in ascending order
        edges.sort(Comparator.comparing(HybridEdge::getFrom));
        System.out.print("""
        Order in which edges are processed:
        | Step  | From  | To    | Weight |
        |-------|-------|-------|--------|
        """);
        for (int i = 1; i <= edges.size(); i++) {
            var edge = edges.get(i - 1);
            System.out.printf("| %5d | %5s | %5s | %6s |\n", i, edge.getFrom(), edge.getTo(), edge.getWeight());
        }

        printBellmannFordResult(allNodes, stepCounter);
        for(int i = 0; i < nodes.size() - 1; i++){
            stepCounter++;
            for (var edge: edges){
                var fromNode = allNodes.get(edge.getFrom());
                var toNode = allNodes.get(edge.getTo());
                if(toNode.distance > fromNode.distance + edge.getWeight()){
                    allNodes.put(edge.getTo(), new PrioNode<>(fromNode.distance + edge.getWeight(), toNode.node, fromNode.node));
                }
            }
            printBellmannFordResult(allNodes, stepCounter);
        }

        for (var edge: edges){
            var fromNode = allNodes.get(edge.getFrom());
            var toNode = allNodes.get(edge.getTo());
            if(toNode.distance > fromNode.distance + edge.getWeight()){
                System.out.println("Negative cycle!");
                return;
            }
        }
    }

    private void printBellmannFordResult(HashMap<T, PrioNode<T>> allNodes, int step){
        System.out.printf("\nStep %s\n", step);
        System.out.print("""
        | Node  | Dist      | Pred  |
        |-------|-----------|-------|
        """);

        allNodes.forEach((key, value) -> System.out.printf("| %5s | %9s | %5s |\n", key, value.distance, value.predecessor != null ? value.predecessor.getKey(): "-" ));
    }

    /**
     * Compute all-pairs-shortest-path (APSP) using Floyd-Warshall algorithm.
     * Runtime:
     *  - BC/ AC/ WC: Θ(|V|³)
     * @implNote As this graph is implemented as a graph with adjacency list, (a lot of) preprocessing needs to be done to generate the adjacency matrix (= distance matrix in iteration 0) required for the Floyd-Warshall algorithm.
     *
     */
    public void floydWarshallAlgorithm(){
        int stepCounter = 0;
        var predecessorMatrix = new String[nodes.size()][nodes.size()];
        var prevDistanceMatrix = new double[nodes.size()][nodes.size()];
        var curDistanceMatrix = new double[nodes.size()][nodes.size()];
        var mapKeysToArrayIdx = new HashMap<T, Integer>(nodes.size());
        var mapReverse = new HashMap<Integer, T>(nodes.size());

        for (int i = 0; i < prevDistanceMatrix.length; i++) {
            for (int j = 0; j < prevDistanceMatrix.length; j++) {
                if(i == j) {
                    prevDistanceMatrix[i][j] = 0d;
                    curDistanceMatrix[i][j] = 0d;
                }
                else {
                    prevDistanceMatrix[i][j] = Double.POSITIVE_INFINITY;
                    curDistanceMatrix[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }

        int i = 0;
        for(var key: nodes.keySet()){
            mapKeysToArrayIdx.put(key, i);
            mapReverse.put(i, key);
            i++;
        }

        int j = 0;
        for (var node: nodes.values()){
            for (var ajdNodeEdge: node.getAdjList().values()) {
                var nodeToIdx = mapKeysToArrayIdx.get(ajdNodeEdge.getTo());
                predecessorMatrix[j][nodeToIdx] = ajdNodeEdge.getFrom().toString();
                //setGenericMultidimMatrix(predecessorMatrix, j, nodeToIdx, ajdNodeEdge.getFrom());
                prevDistanceMatrix[j][nodeToIdx] = ajdNodeEdge.getWeight();
            }
            j++;
        }

        printFloydWarshallResult(prevDistanceMatrix, predecessorMatrix, mapReverse, stepCounter);
        // end of initialization of Floyd-Warshall algorithm

        // Start of actual algorithm
        // Dynamic programming (bottom-up approach): loop over all possible "in between nodes k" with k in [0;|V| - 1]
        // i. e.: start computing shortest paths with no nodes in between (-> adjacency matrix)
        // , then use node 1 as "in between node" and check whether new shorter path can be found with paths going through node 1 -> shortest paths for "in between nodes" 0, 1 are found
        // , then use node 2 as new "in between node" and check whether new shorter path can be found with paths going through node 2 -> shortest paths for "in between nodes" 0, 1, 2 are found
        // and so on until all nodes have been used as "in between nodes"
        for (int k = 0; k < curDistanceMatrix.length; k++) { // Θ(|V|)
            stepCounter++;
            // iterate over all "from nodes" in Θ(|V|)
            for (int y = 0; y < curDistanceMatrix.length; y++) {
                // iterate over all "to nodes" in Θ(|V|)
                for (int x = 0; x < curDistanceMatrix.length; x++) {
                    // shortest path which currently connects (or does not connect (= Infinity)) the two nodes y ("from node") and x ("to node")
                    var curShortestPath = prevDistanceMatrix[y][x];
                    // possible new path consists of: shortest path from node y to new "in between node k" + shortest from new "in between node k" to node x
                    var newShortestPath = prevDistanceMatrix[y][k] + prevDistanceMatrix[k][x];
                    // check whether the new path is shorter than the already existing one
                    if(newShortestPath < curShortestPath){
                        // update path length
                        curDistanceMatrix[y][x] = newShortestPath;
                        // update predecessor: new predecessor is now the predecessor of the shortest path from the "in between node k" to target node x
                        // Note: be aware that new "in between node k" is not necessarily the immediate predecessor for path, though in small graphs with just a few nodes this may often be the case!
                        predecessorMatrix[y][x] = predecessorMatrix[k][x];

                    } else curDistanceMatrix[y][x] = curShortestPath; // copy previous shortest path and predecessor over to new distance matrix
                }
            }
            printFloydWarshallResult(curDistanceMatrix, predecessorMatrix, mapReverse, stepCounter);
            prevDistanceMatrix = curDistanceMatrix;
        }
    }

    private void setGenericMultidimMatrix(List<List<T>> m, int i, int j, T value){
        var curList = m.get(j);
        curList.set(i, value);
        m.set(j, curList);
    }

    private void printFloydWarshallResult(double[][] distMatrix, String[][] predecessorMatrix, Map<Integer, T> mapIndexToKey, int step){
        System.out.printf("\n\n> Step: %d\n\tDistance matrix:\n\t %4s |", step, "");
        for (int i = 0; i < distMatrix.length; i++) System.out.printf(" %8s ", mapIndexToKey.get(i));
        System.out.println("\n\t" + "-".repeat(6) + "|" + "-".repeat(distMatrix.length * 10));
        for (int i = 0; i < distMatrix.length; i++) {
            System.out.printf("\t %4s |", mapIndexToKey.get(i));
            for (int j = 0; j < distMatrix.length; j++) {
                System.out.printf(" %8s ", distMatrix[i][j]);
            }
            System.out.println();
        }

        System.out.printf("\n\tPredecessor matrix:\n\t %4s |", "");
        for (int i = 0; i < distMatrix.length; i++) System.out.printf(" %8s ", mapIndexToKey.get(i));
        System.out.println("\n\t" + "-".repeat(6) + "|" + "-".repeat(distMatrix.length * 10));
        for (int i = 0; i < distMatrix.length; i++) {
            System.out.printf("\t %4s |", mapIndexToKey.get(i));
            for (int j = 0; j < distMatrix.length; j++) {
                System.out.printf(" %8s ", predecessorMatrix[i][j]);
            }
            System.out.println();
        }
    }
}
