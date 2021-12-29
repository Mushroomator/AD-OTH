import java.util.HashMap;
import java.util.Objects;

public class HybridNode<T> {
    private final T key;
    private HashMap<T, HybridEdge<T>> adjList;


    public HybridNode(T key, HashMap<T, HybridEdge<T>> adjList) {
        this.key = key;
        this.adjList = adjList;
    }

    public HybridNode(T key) {
        this(key, new HashMap<>());
    }

    public void connect(HybridEdge<T> edge){
        adjList.put(edge.getTo(), edge);
    }

    public HashMap<T, HybridEdge<T>> getAdjList() {
        return adjList;
    }

    public void setAdjList(HashMap<T, HybridEdge<T>> adjList) {
        this.adjList = adjList;
    }

    public T getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "HybridNode{" +
                "key=" + key +
                ", adjList=" + adjList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HybridNode<?> that = (HybridNode<?>) o;
        return Objects.equals(key, that.key) && Objects.equals(adjList, that.adjList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, adjList);
    }
}
