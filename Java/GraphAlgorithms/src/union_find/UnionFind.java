package union_find;

import java.util.HashMap;
import java.util.Objects;

/**
 * Interface for a Union-Find data structure.
 * @param <T> Type of key used as unique identifier for a set.
 * @author Thomas Pilz
 */
public abstract class UnionFind<T extends Comparable<T>>{

    protected HashMap<T, UnionFindSet<T>> sets;

    /**
     * Create a new set within the Union-Find data structure.
     * @param key unique key, that identifies this set
     * @return a new union-find set
     */
    public abstract UnionFindSet<T> createSet(T key);

    /**
     * Merge two union find sets and return merged set.
     * If the two set already belong to the same set, nothing is done and the set is returned.
     * @param a a union-find set
     * @param b another union-find set
     * @return merged set of a and b
     */
    public abstract UnionFindSet<T> merge(T a, T b);

    /**
     * Check whether two sets are different or belong to the same set.
     * In other words: check whether two sets have the same representative.
     *
     * @param a a union-find set
     * @param b another union-find set
     * @return true, if both sets belong to the same set (have the same representative)
     */
    public boolean isUnion(T a, T b){
        assert sets.containsKey(a);
        assert sets.containsKey(b);

        final var aSet = sets.get(a);
        final var bSet = sets.get(b);
        final var repA = aSet.getRep();
        final var repB = bSet.getRep();

        return repA == repB;
    };

    /**
     * Get all sets in the union-find data structure.
     * @return all union-find sets in the data structure
     */
    public HashMap<T, UnionFindSet<T>> getSets(){
        return sets;
    };

    @Override
    public String toString() {
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnionFind<?> unionFind = (UnionFind<?>) o;
        return Objects.equals(sets, unionFind.sets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sets);
    }
}
