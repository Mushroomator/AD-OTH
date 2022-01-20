package graphs.graph_adj_list_hybrid;

public class BfsNode<T extends Comparable<T>> {
    public HybridNode<T> node;
    public Double distance;
    public NodeColor color;
    public T predecessor;

    public BfsNode(graphs.graph_adj_list_hybrid.HybridNode<T> node, Double distance, NodeColor color, T predecessor) {
        this.node = node;
        this.distance = distance;
        this.color = color;
        this.predecessor = predecessor;
    }

    @Override
    public String toString() {
        return this.node.getKey().toString();
    }
}
