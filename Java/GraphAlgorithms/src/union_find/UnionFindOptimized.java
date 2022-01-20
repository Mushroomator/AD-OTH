package union_find;

import java.util.HashMap;

/**
 * @implNote Optimized union-find data structure, which uses "path shortening" and smart "merging" of trees to keep the
 * trees as small as possible. Using this implementation you might get different result as the merge operations are different
 * as the tree height differ from the implemenntation in {@link UnionFindLecture}.
 * Use {@link UnionFindLecture} if you want to have the same results as when your doing the algorithm yourself on paper.
 * @see UnionFindLecture
 * @param <T> Type of key used as unique identifier for a set.
 * @author Thomas Pilz
 */
public class UnionFindOptimized<T extends Comparable<T>> extends UnionFind<T> {

    public UnionFindOptimized() {
        this.sets = new HashMap<>();
    }

    public UnionFindSet<T> createSet(T key){
        assert !sets.containsKey(key);
        var newSet = new UnionFindSetOptimized<T>(key);
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
                // special case to make sure the datastructure behaves as defined in the lecture, but any another
                // handling of trees with same height is also possible
                // Here: Rep which with the "bigger" key is appended to rep with "smaller" key
                if(a.compareTo(b) < 0) repB.rep = repA;
                else repA.rep = repB;
            }
        }
        // no matter if the sets were merged already they are now merged, so all reps will be the same!
        return repA.getRep();
    }
}

