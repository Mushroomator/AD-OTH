package union_find;

import java.util.Stack;

/**
 * @implNote Implementation of a set within a Union-Find data structure according to the implementation that has been discussed
 * in the lecture with all optimizations. This is the most performant way, but it may result in different representatives
 * as the height of the trees differ from the {@link UnionFindSetLecture} implementation and thus do some merge operations.
 *
 * @see UnionFindSetLecture
 * @param <T> Type of Key
 * @author Thomas Pilz
 */
public class UnionFindSetOptimized<T extends Comparable<T>> extends UnionFindSet<T> {

    public UnionFindSetOptimized(T key) {
        this.rep = this;
        this.height = 0;
        this.key = key;
    }

    public UnionFindSet<T> getRep() {
        var cur = rep;
        // Traverse up all nodes until the representative is the same as the current node
        // --> Topmost node is reached --> return it
        // While doing this store all traversed nodes in a stack, and when the representative is found set all their representatives
        // to the found representative, this way the "path" up to the topmost root is kept as short as possible
        var path = new Stack<UnionFindSet<T>>();
        while (cur != cur.rep) {
            cur = cur.rep;
            path.add(cur);
        }

        // shorten path for better performance
        for (var node : path) {
            node.rep = cur;
            node.height = 1;
        }
        return cur;
    }
}

