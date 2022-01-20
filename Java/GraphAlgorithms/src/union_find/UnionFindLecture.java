package union_find;

import java.util.HashMap;
import java.util.Objects;

/**
 * @implNote Simple union-find data structure, which does <em>NOT</em> use "path shortening" or smart "merging" of trees to keep the
 * trees as small as possible. Using this implementation you get exactly the results as you get when you do the algorithm
 * yourself on paper as discussed in the lecture. So use this if you want to check if your result are correct.
 * For best performance using all the optimizations use {@link UnionFindOptimized}. Note that this might give you different
 * results in terms of how sets are merges as the tree height are generally smaller there.
 * @see UnionFindOptimized
 * @param <T> Type of key used as unique identifier for a set.
 * @author Thomas Pilz
 */
public class UnionFindLecture<T extends Comparable<T>> extends UnionFind<T> {

    public UnionFindLecture() {
        this.sets = new HashMap<>();
    }

    public UnionFindSet<T> createSet(T key){
        assert !sets.containsKey(key);
        var newSet = new UnionFindSetLecture<T>(key);
        sets.put(key, newSet);
        return newSet;
    }

    public UnionFindSet<T> merge(T a, T b){
        assert sets.containsKey(a);
        assert sets.containsKey(b);

        final var aSet = sets.get(a);
        final var bSet = sets.get(b);
        final var repA = aSet.getRep();
        final var repB = bSet.getRep();

        // only merge/ unify if they are not already members of the same set
        if(repA != repB){
            if(repA.height > repB.height) repB.rep = repA;
            else if(repB.height > repA.height) repA.rep = repB;
            else {
                // Trees have equal height!
                // --> as this implementation does not use path shortage (which is the better option!) we must update the height of the tree
                // as the tree will grow by 1, so the top-most node will have his height increased by 1
                // special case to make sure the data structure behaves as defined in the lecture, but any another
                // handling of trees with same height is also possible
                // Here: Rep which with the "bigger" key is appended to rep with "smaller" key
                if(a.compareTo(b) < 0) repB.rep = repA;
                else repA.rep = repB;
                repA.getRep().height += 1;
            }
        }
        // no matter if the sets were merged already they are now merged, so all reps will be the same!
        return repA.getRep();
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnionFindLecture<?> unionFind = (UnionFindLecture<?>) o;
        return Objects.equals(sets, unionFind.sets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sets);
    }
}
