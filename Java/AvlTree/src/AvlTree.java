import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Adelson-Velsky, Landis (AVL) tree.
 * Some methods recursively other iteratively implemented.
 * @author Thomas Pilz
 */
public class AvlTree {

    private AvlNode root;

    public AvlTree(){
        this.root = null;
    }

    /**
     * Updates the height of a node after a node has been inserted.
     * Runtime: Θ(1)
     * @param node node which height needs to be updated.
     */
    private void updateNodeHeight(AvlNode node){
        int newHeight = 1 + Math.max(node.getLeft().getHeight(), node.getRight().getHeight());
        node.setHeight(newHeight);
    }

    /**
     * Gets height of a node in the tree
     * Runtime: Θ(1)
     * @param node node which height is required
     * @return height of node
     */
    private int getNodeHeight(AvlNode node){
        if(node == null) return -1;
        return node.getHeight();
    }

    /**
     * Print tree layer by layer.
     * Runtime: Θ(n) each node is inserted and retrieved exactly once.
     */
    public void printByLayers(){
        var nodesWithinNextLayer = new ConcurrentLinkedQueue<AvlNodeLayer>();
        if(root == null){
            System.out.println("No nodes in AVL tree!");
            return;
        }
        int previousLayer = -1;
        nodesWithinNextLayer.add(new AvlNodeLayer(root, 0));
        while (!nodesWithinNextLayer.isEmpty()){
            var curNode = nodesWithinNextLayer.poll();
            if(curNode.layer != previousLayer) {
                previousLayer = curNode.layer;
                System.out.printf("\nLayer %s: ", curNode.layer);
            }
            System.out.print(curNode);
            var cur = curNode.node;
            if(cur.getLeft() != null) nodesWithinNextLayer.add(new AvlNodeLayer(cur.getLeft(), curNode.layer + 1));
            if(cur.getRight() != null) nodesWithinNextLayer.add(new AvlNodeLayer(cur.getRight(), curNode.layer + 1));
        }
    }

    private void checkRotationRightRequired(Stack<AvlNode> path){
        // There must be at least one node in the stack, otherwise nothing can be done
        if(path.size() < 1) return;
        AvlNode predecessor = path.pop();
        AvlNode current = null;

        while (path.size() > 0 || current == null){
            current = predecessor;
            // check if node needs to rotate

            // no --> continue

            // rotate and get new root node (rotation will change root node)
            AvlNode newRoot = new AvlNode(1);
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
    public void insertRec(AvlNode toBeInserted){
        insertRec(root, toBeInserted);
    }

    /**
     * Insert node into tree. Recursive implementation. PRIVATE as this is only to be used internally.
     * @param current current node
     * @param toBeInserted node to be inserted
     * @return node that has been inserted of null if node already exists
     */
    private AvlNode insertRec(AvlNode current, AvlNode toBeInserted){
        if(current == null) {
            // insert node
            toBeInserted.setRight(null);
            toBeInserted.setLeft(null);
            current = toBeInserted;
            return current;
        }
        if(current.getKey() == toBeInserted.getKey()) return null;
        else if (toBeInserted.getKey() < current.getKey()) {
            insertRec(current.getLeft(), toBeInserted);
            current.setLeft(checkRotationRightRec(current.getLeft()));
            return current;
        }
        else {
            insertRec(current.getRight(), toBeInserted);
            current.setRight(checkRotationLeftRec(current.getRight()));
            return current;
        }
    }

    public AvlNode checkRotationRightRec(AvlNode root){
        if(root != null){
            if(root.getLeft() != null){
                if(getNodeHeight(root.getLeft()) - getNodeHeight(root.getRight()) == 2){
                    // left subtree or right subtree is too high --> correct height to recreate AVL attributes
                    if(getNodeHeight(root.getLeft().getRight()) > getNodeHeight(root.getLeft().getLeft())){
                        // inner subtree is higher than outer --> double rotation
                        return doubleRotateRight(root);
                    } else {
                        // outer subtree is higher than inner --> rotate right once
                        return rotateRight(root);
                    }
                }
            }
            updateNodeHeight(root);
            //if(Math.abs())
        }
        return root;
    }

    public AvlNode checkRotationLeftRec(AvlNode root){
        if(root != null){
            if(root.getRight() != null){
                if(getNodeHeight(root.getRight()) - getNodeHeight(root.getLeft()) == 2){
                    // left subtree or right subtree is too high --> correct height to recreate AVL attributes
                    if(getNodeHeight(root.getRight().getLeft()) > getNodeHeight(root.getRight().getRight())){
                        // inner subtree is higher than outer --> double rotation
                        doubleRotateLeft(root);
                    } else {
                        // outer subtree is higher than inner --> rotate right once
                        rotateLeft(root);
                    }
                }
            }
            updateNodeHeight(root);
            //if(Math.abs())
        }
        return root;
    }

    /**
     * Rotate on node to right.
     * Runtime: Θ(1)
     * @param root root to rotate on
     * @return new root after rotation
     */
    public AvlNode rotateRight(AvlNode root){
        var newRoot = root.getLeft();
        // get right successor of new root and set it as left successor of old root
        root.setLeft(newRoot.getRight());
        // set old root as right successor
        newRoot.setRight(root);

        return newRoot;
    }

    public AvlNode doubleRotateRight(AvlNode root){
        var newRoot = rotateLeft(root.getLeft());
        return rotateRight(newRoot);
    }

    /**
     * Rotate on node to left.
     * Runtime: Θ(1)
     * @param root root to rotate on
     * @return new root after rotation
     */
    public AvlNode rotateLeft(AvlNode root){
        var newRoot = root.getRight();
        // get right successor of new root and set it as left successor of old root
        root.setRight(newRoot.getLeft());
        // set old root as right successor
        newRoot.setLeft(root);

        return newRoot;
    }

    public AvlNode doubleRotateLeft(AvlNode root){
        var newRoot = rotateRight(root.getRight());
        return rotateLeft(newRoot);
    }

    /**
     * Insert node into tree. Iterative implementation.
     * @param toBeInserted node to be inserted
     * @return node that has been inserted of null if node already exists
     */
    public AvlNode insertIter(AvlNode toBeInserted){
        // Node will always be inserted as a leaf (at least at first, before possible rotations)
        toBeInserted.setRight(null);
        toBeInserted.setLeft(null);
        toBeInserted.setHeight(0);

        if(root == null){
            root = toBeInserted;
            return toBeInserted;
        }
        AvlNode current = root;
        Stack<AvlNode> path = new Stack<>();

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
    public AvlNode removeRec(AvlNode toBeRemoved){
        return removeRec(root,toBeRemoved);
    }

    /**
     * Remove a node from the tree. Recursive implementation. PRIVATE as this is only to be used internally.
     * @param current current node to be checked.
     * @param toBeRemoved node to be removed
     * @return true if node has been removed, false if node does not exist.
     */
    private AvlNode removeRec(AvlNode current, AvlNode toBeRemoved){
        // no more nodes to go to -> node does not exist in tree
        if(current == null) return null;
        if(current.getKey() == toBeRemoved.getKey()){
            // found node to be deleted -> 3 cases
            if(current.getRight() != null && current.getLeft() != null){
                // node somewhere in the middle of the tree
                // 1. Search inorder-successor
                AvlNode inorderSuccessor = getLeftMostChild(current.getRight());
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
    public AvlNode findRec(AvlNode toBeFound){
        return findRec(root, toBeFound);
    }

    /**
     * Find node in tree. Recursive implementation. PRIVATE as this is only to be used internally.
     * @param current current node
     * @param toBeFound node to be found
     * @return Node that has been searched for or null if not found.
     */
    private AvlNode findRec(AvlNode current, AvlNode toBeFound){
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
    public AvlNode findIter(AvlNode toBeFound){
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
    private AvlNode getLeftMostChild(AvlNode node){
        AvlNode current = node;
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

    /**
     * Class only necessary within this class in method printByLayer() in order to be able to
     * print a layer number for each node.
     * @author Thomas Pilz
     */
    private class AvlNodeLayer{
        public AvlNode node;
        public int layer;

        public AvlNodeLayer(AvlNode node, int layer) {
            this.node = node;
            this.layer = layer;
        }

        @Override
        public String toString() {
            return "{" + node + ", " + layer + '}';
        }
    }
}
