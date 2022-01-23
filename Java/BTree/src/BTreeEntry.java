import java.util.Objects;

public class BTreeEntry<T extends Comparable<T>> implements Comparable<BTreeEntry<T>> {
    public T key;
    public Object data;

    public BTreeEntry(T key, Object data) {
        this.key = key;
        this.data = data;
    }

    public BTreeEntry(T key) {
        this(key, null);
    }

    @Override
    public String toString() {
        return key.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BTreeEntry<?> that = (BTreeEntry<?>) o;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public int compareTo(BTreeEntry<T> o) {
        if(this == o) return 0;
        return key.compareTo(o.key);
    }
}
