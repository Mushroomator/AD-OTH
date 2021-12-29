import java.util.Objects;

public class HybridEdge<T> {
    private final T to;
    private final Double weight;

    public HybridEdge(T to) {
        this(to, 1.0);
    }

    public HybridEdge(T to, Double weight) {
        this.to = to;
        this.weight = weight;
    }

    public T getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HybridEdge<?> that = (HybridEdge<?>) o;
        return Objects.equals(to, that.to) && Objects.equals(weight, that.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(to, weight);
    }

    @Override
    public String toString() {
        return "HybridEdge{" +
                "to=" + to +
                ", weight=" + weight +
                '}';
    }
}
