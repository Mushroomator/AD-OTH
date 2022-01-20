package union_find;

import java.util.Objects;

public abstract class UnionFindSet<T extends Comparable<T>>{
    public UnionFindSet<T> rep;
    public int height;
    // This is not required for the data structure to work properly but allows identifying which node is actually
    // associated with this "set" to be able to print it to console
    public T key;

    /**
     * Get representative of this set.
     * @return Union-Find set that represents this set.
     */
    public abstract UnionFindSet<T> getRep();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnionFindSet<?> that = (UnionFindSet<?>) o;
        return height == that.height && Objects.equals(rep, that.rep) && Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rep, height, key);
    }
}
