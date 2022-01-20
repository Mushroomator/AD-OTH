package graphs.graph_adj_list_hybrid;

public class DfsNode<T extends Comparable<T>> {
    public HybridNode<T> node;
    public NodeColor color;
    public T predecessor;
    public Integer firstTime;
    public Integer lastTime;

    public DfsNode(HybridNode<T> node, NodeColor color, T predecessor, Integer firstTime, Integer lastTime) {
        this.node = node;
        this.color = color;
        this.predecessor = predecessor;
        this.firstTime = firstTime;
        this.lastTime = lastTime;
    }

    @Override
    public String toString() {
        return this.node.getKey().toString();
    }
}
