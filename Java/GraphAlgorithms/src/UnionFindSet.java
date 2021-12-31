import java.util.Comparator;
import java.util.Stack;

public class UnionFindSet<T extends Comparable<T>> {
    public UnionFindSet<T> rep;
    public int height;
    // This is not required for the data structure to work properly but allows identifying which node is actually
    // associated with this "set" to be able to print it to console
    public T key;

    public UnionFindSet(T key) {
        this.rep = this;
        this.height = 0;
        this.key = key;
    }

    public UnionFindSet<T> getRep(){
        var cur = rep;
        // Traverse up all nodes until the representative is the same as the current node
        // --> Topmost node is reached --> return it
        // While doing this store all traversed nodes in a stack, and when the representative is found set all their representatives
        // to the found representative, this way the "path" up to the topmost root is kept as short as possible
        var path = new Stack<UnionFindSet<T>>();
        while (cur != cur.rep){
            cur = cur.rep;
            path.add(cur);
        }

        // shorten path for better performance
        for (var node: path){
            node.rep = cur;
            node.height = 1;
        }
        return cur;
    }
}
