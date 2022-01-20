package union_find;

/**
 * @implNote Implementation of a set within a Union-Find data structure according to the implementation that has been discussed
 * in the lecture without any optimizations. This is the way the algorithm is done by hand in the lecture, so use this
 * implementation when you want to check whether the way you conducted the algorithm by hand is correct.
 * For a more performant option, which might give you a different result (i.e. other representatives due to smaller trees)
 * use {@link UnionFindSetOptimized}.
 *
 * @see UnionFindSetOptimized
 * @param <T> Type of Key
 * @author Thomas Pilz
 */
public class UnionFindSetLecture<T extends Comparable<T>> extends UnionFindSet<T>{

    public UnionFindSetLecture(T key) {
        this.rep = this;
        this.height = 0;
        this.key = key;
    }

    public UnionFindSet<T> getRep(){
        var cur = rep;
        // Traverse up all nodes until the representative is the same as the current node
        // --> Topmost node is reached --> return it
        while (cur != cur.rep) cur = cur.rep;

        return cur;
    }
}
