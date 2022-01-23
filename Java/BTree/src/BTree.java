
public class BTree<T extends Comparable<T>> {
    private static final int DEFAULT_ORDER = 4;
    private static final int SPACES_BETWEEN_NODES = 3;

    private final int order;
    private BTreeNode<T> root;

    public BTree() {
        this(BTree.DEFAULT_ORDER);
    }

    public BTree(int order) {
        if(order < 3) throw new IllegalArgumentException("Order must be greater than 2 for this B-Tree implementation");
        this.order = order;
        this.root = null;
    }

    public void insert(T key, Object data){
        if(root == null) {
            root = new BTreeNode<>(order);
        }
        root.insert(new BTreeEntry<T>(key, data));
    }

    /**
     * Print B-Tree
     */
    public void print(){
        print(SPACES_BETWEEN_NODES);
    }

    /**
     * Print B-Tree
     * @param spacesBetweenNodes number of spaces between nodes
     */
    public void print(int spacesBetweenNodes){
        if(root == null) {
            System.out.println("No values in tree.");
            return;
        }
        if(spacesBetweenNodes <= 1) throw new IllegalArgumentException("Number of spaces between nodes must be at least 2.");
        root.printRoot(spacesBetweenNodes);
    }
}
