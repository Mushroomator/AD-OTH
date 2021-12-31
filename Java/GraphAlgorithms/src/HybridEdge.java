import java.util.Objects;

public class HybridEdge<T> {
    private final T from;
    private final T to;
    private final Double weight;

    public HybridEdge(T from, T to) {
        this(from, to, 1.0);
    }

    public HybridEdge(T from, T to, Double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public T getFrom() {
        return from;
    }

    public T getTo() {
        return to;
    }

    public Double getWeight() {
        return weight;
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
        return """
                    %5s
               %5s ------> %5s""".formatted(weight, from, to);
    }
}
