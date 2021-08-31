import java.util.Stack;

/**
 * Adelson-Velsky, Landis (AVL) tree.
 * Some methods recursively other iteratively implemented.
 * @author Thomas Pilz
 */
public class AvlTree {

    private Node root;

    public AvlTree(){
        this.root = null;
    }

    /**
     * Updates the height of a node after a node has been inserted.
     * @param node node which height needs to be updated.
     */
    private void updateNodeHeight(Node node){
        int newHeight = 1 + Math.max(node.getLeft().getHeight(), node.getRight().getHeight());
        node.setHeight(newHeight);
    }

    /**
     * Gets height of a node in the tree
     * @param node node which height is required
     * @return height of node
     */
    private int getNodeHeight(Node node){
        if(node == null) return -1;
        return node.getHeight();
    }

    private void checkRotationRightRequired(Stack<Node> path){
        // There must be at least one node in the stack, otherwise nothing can be done
        if(path.size() < 1) return;
        Node predecessor = path.pop();
        Node current = null;

        while (path.size() > 0 || current == null){
            current = predecessor;
            // check if node needs to rotate

            // no --> continue

            // rotate and get new root node (rotation will change root node)
            Node newRoot = new Node(1);
            if(path.size() == 0) {
                // no predecessor anymore --> root node
                this.root = newRoot;
                return;
            }
            predecessor = path.pop();

            // set successor of predecessor correctly to new root node
            if(newRoot.getKey() < predecessor.getKey()) predecessor.setLeft(newRoot);
            else predecessor.setRight(newRoot);



        }
    }

    /**
     * Insert node into tree. Recursive implementation.
     * @param toBeInserted node to be inserted
     */
    public void insertRec(Node toBeInserted){
        insertRec(root, toBeInserted);
    }

    /**
     * Insert node into tree. Recursive implementation. PRIVATE as this is only to be used internally.
     * @param current current node
     * @param toBeInserted node to be inserted
     * @return node that has been inserted of null if node already exists
     */
    private Node insertRec(Node current, Node toBeInserted){
        if(current == null) {
            // insert node
            toBeInserted.setRight(null);
            toBeInserted.setLeft(null);
            current = toBeInserted;
            return current;
        }
        if(current.getKey() == toBeInserted.getKey()) return null;
        else if (toBeInserted.getKey() < current.getKey()) {
            current.setLeft(insertRec(current.getLeft(), toBeInserted));
            return current;
        }
        else {
            current.setRight(insertRec(current.getRight(), toBeInserted));
            return current;
        }
    }

    /**
     * Insert node into tree. Iterative implementation.
     * @param toBeInserted node to be inserted
     * @return node that has been inserted of null if node already exists
     */
    public Node insertIter(Node toBeInserted){
        // Node will always be inserted as a leaf (at least at first, before possible rotations)
        toBeInserted.setRight(null);
        toBeInserted.setLeft(null);
        toBeInserted.setHeight(0);

        if(root == null){
            root = toBeInserted;
            return toBeInserted;
        }
        Node current = root;
        Stack<Node> path = new Stack<>();

        while (current != null){
            // Remeber all the nodes visited when traversing through the tree
            // These nodes need to be revisited in case any rotations need to be performed after insertion
            path.push(current);
            if(toBeInserted.getKey() == current.getKey()) return null;
            else if(toBeInserted.getKey() < current.getKey()) {
                if (current.getLeft() == null){
                    // found spot where node needs to go
                    current.setLeft(toBeInserted);
                    // AVL tree may not be balanced now; Insertion was on left so right rotation might need to be performed
                    checkRotationRightRequired(path);

                }
                else current = current.getLeft();
            }
            else {
                if(current.getRight() == null) {
                    // found spot where node needs to go
                    current.setRight(toBeInserted);
                    // traverse path back again: update heights and check if rotations need to be done
                }
                else current = current.getRight();
            }
        }
        // return inserted element
        return toBeInserted;
    }

    /**
     * Remove a node from the tree. Recursive implementation.
     * @param toBeRemoved node to be removed
     * @return true if node has been removed, false if node does not exist.
     */
    public Node removeRec(Node toBeRemoved){
        return removeRec(root,toBeRemoved);
    }

    /**
     * Remove a node from the tree. Recursive implementation. PRIVATE as this is only to be used internally.
     * @param current current node to be checked.
     * @param toBeRemoved node to be removed
     * @return true if node has been removed, false if node does not exist.
     */
    private Node removeRec(Node current, Node toBeRemoved){
        // no more nodes to go to -> node does not exist in tree
        if(current == null) return null;
        if(current.getKey() == toBeRemoved.getKey()){
            // found node to be deleted -> 3 cases
            if(current.getRight() != null && current.getLeft() != null){
                // node somewhere in the middle of the tree
                // 1. Search inorder-successor
                Node inorderSuccessor = getLeftMostChild(current.getRight());
                // 2. Delete inorder successor (will be one of the other two cases so algorithm terminates)
                removeRec(current.getRight(), inorderSuccessor);
                // 2. Right node of current node (which will be deleted) will be the successor of the parent node
                return current.getRight();
            } else {
                if(current.getLeft() == null) return current.getRight();
                else return current.getLeft();
            }
        }
        else if (toBeRemoved.getKey() < current.getKey()) {
            // Set left successor to returned node from remove
            current.setLeft(removeRec(current.getLeft(), toBeRemoved));
            // return current node
            return current;
        }
        else {
            // Set right successor to returned node from remove
            current.setRight(removeRec(current.getRight(), toBeRemoved));
            return current;
        }
    }

    /**
     * Find node in tree. Recursive implementation.
     * @param toBeFound node to be found
     * @return Node that has been searched for or null if not found.
     */
    public Node findRec(Node toBeFound){
        return findRec(root, toBeFound);
    }

    /**
     * Find node in tree. Recursive implementation. PRIVATE as this is only to be used internally.
     * @param current current node
     * @param toBeFound node to be found
     * @return Node that has been searched for or null if not found.
     */
    private Node findRec(Node current, Node toBeFound){
        // no more nodes --> node does not exist
        if(current == null) return null;
        var key = toBeFound.getKey();
        if(key == current.getKey()) return current;
        else if(key < current.getKey()) return findRec(current.getLeft(), toBeFound);
        else return findRec(current.getRight(), toBeFound);
    }

    /**
     * Find node in tree. Iterative implementation.
     * @param toBeFound node to be found
     * @return Node that has been searched for or null if not found.
     */
    public Node findIter(Node toBeFound){
        var current = root;
        while (current != null){
            if(toBeFound.getKey() == current.getKey()) return current;
            else if (toBeFound.getKey() < current.getKey()) current = current.getLeft();
            else current = current.getRight();
        }
        // No more nodes --> node does not exist
        return null;
    }

    /**
     * Get left most child of a node. Returns null if there is no child.
     * @param node node which left most child should be returned
     * @return left most child or null if there is no child or given node is null
     */
    private Node getLeftMostChild(Node node){
        Node current = node;
        while (current != null){
            if(current.getLeft() == null) return current;
            else current = current.getLeft();
        }
        return null;
    }

    /**
     * Delete tree and all nodes within it.
     */
    public void deleteTree(){
        // remove reference to root; Garbage collector takes care of the rest
        this.root = null;
    }
}
