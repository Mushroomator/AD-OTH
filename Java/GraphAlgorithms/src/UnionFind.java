import java.util.HashMap;
import java.util.Objects;

public class UnionFind<T extends Comparable<T>> {
    private final HashMap<T, UnionFindSet<T>> sets;

    public UnionFind() {
        this.sets = new HashMap<>();
    }

    public UnionFindSet<T> createSet(T key){
        assert !sets.containsKey(key);
        var newSet = new UnionFindSet<T>(key);
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

    public UnionFindSet<T> mergeWithPathShortage(T a, T b){
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

    public boolean isUnion(T a, T b){
        assert sets.containsKey(a);
        assert sets.containsKey(b);

        final var aSet = sets.get(a);
        final var bSet = sets.get(b);
        final var repA = aSet.getRep();
        final var repB = bSet.getRep();

        return repA == repB;
    }

    public HashMap<T, UnionFindSet<T>> getSets() {
        return sets;
    }

    @Override
    public String toString() {
        return "UnionFind{" +
                "sets=" + sets +
                '}';
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
