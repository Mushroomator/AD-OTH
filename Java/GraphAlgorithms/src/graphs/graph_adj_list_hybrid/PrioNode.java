package graphs.graph_adj_list_hybrid;

public class PrioNode<T extends Comparable<T>> implements Comparable<PrioNode<T>>{
    public double distance;
    public HybridNode<T> predecessor;
    public HybridNode<T> node;

    PrioNode(double distance, HybridNode<T> node, HybridNode<T> predecessor){
        this.distance = distance;
        this.node = node;
        this.predecessor = predecessor;
    }

    public double getDistance() {
        return distance;
    }


    @Override
    public int compareTo(PrioNode<T> o) {
        // nodes with smaller distance first,
        // if two nodes have the same distance, the one with the smaller key should be used first
        if(this.distance < o.distance) return -1;
        if(this.distance > o.distance) return 1;
        else return this.node.getKey().compareTo(o.node.getKey());
    }

    @Override
    public String toString() {
        var predStr = predecessor != null ? predecessor.getKey().toString(): "null";
        return  "{key=" + node.getKey() +
                ", distance=" + distance +
                ", predecessor=" +  predStr +
                '}';
    }
}
