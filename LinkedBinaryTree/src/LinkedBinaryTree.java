import java.util.Stack;

public class LinkedBinaryTree {

    private Node root;

    public LinkedBinaryTree(){
        this.root = null;
    }

    /**
     * Insert a node into the tree iteratively.
     * @param toBeInserted node to be inserted
     * @return inserted node or null if it could not be inserted
     */
    public Node insertIter(Node toBeInserted){
        // Check if tree has a root
        if(root != null){
            Node current = root;
            while(current != null){
                // keys must be unique, key already exists -> return null
                if(toBeInserted.getKey() == current.getKey()) return null;
                if(toBeInserted.getKey() < current.getKey()) {
                    // Value of to be inserted node is smaller -> must go to left
                    if(current.getLeft() == null) current.setLeft(toBeInserted);  // No successor for left -> set node to be the left successor of current node
                    else current = current.getLeft(); // Go to left successor and check again
                } else {
                    // Value of to be inserted node must now ge greater than current node
                    if(current.getRight() == null) current.setRight(toBeInserted); // No successor for right -> set node to be the right successor of current node
                    else current = current.getRight(); // Go to right successor and check again
                }
            }
        } else {
            // 1 element: No root yet -> set root
            root = toBeInserted;
        }
        return toBeInserted;
    }

    public void printIterInorder(){
        if(root == null) return;
        Node current = root;
        Stack<Node> visitedNodes = new Stack<>();
        boolean done = false;

        while (current != null || visitedNodes.size() > 0){
            // go to left most node (= smallest value)
            while (current != null){
                // remember all the nodes on the way
                visitedNodes.push(current);
                current = current.getLeft();
            }
            // current is null now -> last visited node is the left most node
            current = visitedNodes.pop();
            // print node
            System.out.print(current.getKey());
            System.out.print(" ");
            // now go to right subtree
            current = current.getRight();
        }
        System.out.println();
    }

    public void printIterPreorder(){
        if(root == null) return;
        Node current = root;
        Stack<Node> visitedNodes = new Stack<>();
        boolean done = false;

        while (current != null || visitedNodes.size() > 0){
            // go to left most node (= smallest value)
            while (current != null){
                // print node
                System.out.print(current.getKey());
                System.out.print(" ");
                // remember all the nodes on the way
                visitedNodes.push(current);
                current = current.getLeft();
            }
            // current is null now -> last visited node is the left most node
            current = visitedNodes.pop();
            // now go to right subtree
            current = current.getRight();
        }
        System.out.println();
    }

    /**
     * Print tree using recursive implementation.
     * @param order oder in which the tree should be printed.
     */
    public void printRecursive(Traversal order){
        if(root != null) {
            printNodeRecursive(order, root);
            System.out.println();
        }
    }

    /**
     * Prints a node using recursive implementation.
     * @param order order in which the node should be printed
     * @param node current node to be printed
     */
    public void printNodeRecursive(Traversal order, Node node){
        if(node != null){
            switch (order){
                case INORDER -> {
                    System.out.print("(");
                    printNodeRecursive(order, node.getLeft());
                    System.out.print(",");
                    System.out.print(node.getKey());
                    System.out.print(",");
                    printNodeRecursive(order, node.getRight());
                    System.out.print(")");
                }
                case PREORDER -> {
                    System.out.print("(");
                    System.out.print(node.getKey());
                    System.out.print(",");
                    printNodeRecursive(order, node.getLeft());
                    System.out.print(",");
                    printNodeRecursive(order, node.getRight());
                    System.out.print(")");
                }
                case POSTORDER -> {
                    System.out.print("(");
                    printNodeRecursive(order, node.getLeft());
                    System.out.print(",");
                    printNodeRecursive(order, node.getRight());
                    System.out.print(",");
                    System.out.print(node.getKey());
                    System.out.print(")");
                }
            }
        } else {
            System.out.print("n");
        }
    }

    /**
     * Checks if node is in the tree.
     * @param node node to be searched for
     * @return node if found or null if not
     */
    public Node contains(Node node){
        if(root == null) return null;
        Node current = root;
        while (current != null){
            if(current.getKey() == node.getKey()) return current;   // node found -> return it
            if(node.getKey() < current.getKey()) current = current.getLeft(); // node is smaller -> check left successor
            else current = current.getRight();  // node must be bigger -> check right successor
        }
        // All relevant objects have been checked and nothing has been found
        return null;
    }

    /**
     * Delete a node from the tree.
     * @param toBeDeleted node to be deleted
     * @return true when node was deleted, false otherwise
     */
    public boolean delete(Node toBeDeleted){
        if(root == null) return false;

        // TODO handle case: root node

        Node current = root;
        Node predecessor = null;
        while (current != null){
            if(current.getKey() == toBeDeleted.getKey()) {
                // node found
                if(current.getLeft() == null && current.getRight() == null){
                    // Case 1: node is a leaf (no successors)
                    removeLeaf(current, predecessor);
                    // Binary Search Tree is in correct shape again, node will be deleted by garbage collector anyway but do it here explicitly
                    current = null;
                    return true;
                } else if (current.getLeft() != null && current.getRight() != null){
                    // Case 3: Node is inner node with two successors
                    // find smallest value bigger than the one to be deleted (so called inorder-successor)
                    Node next = current.getRight();
                    Node preInorderSucessor = root; // must start at root so if root needs to be deleted, the references for inorder-successor can be set correctly
                    Node inorderSuccessor = null;
                    while (inorderSuccessor == null){
                        if(next.getLeft() != null) {
                            // save predecessor of next node
                            preInorderSucessor = next;
                            // go to next node on the left
                            next = next.getLeft();
                        }
                        else {
                            // Inorder-successor has been found
                            inorderSuccessor = next;

                            // Make sure tree is in correct shape when removing inorder successor
                            // --> remove inorder-successor from the tree
                            // check whether it has a right successor or not to call correct method
                            if(inorderSuccessor.getRight() != null) removeOnePredecessor(inorderSuccessor, preInorderSucessor);
                            else removeLeaf(inorderSuccessor, preInorderSucessor);
                        }
                    }

                    // No predecessor --> root node --> inorder-successor is new root node
                    if(predecessor == null){
                        inorderSuccessor.setLeft(root.getLeft());
                        inorderSuccessor.setRight(root.getRight());
                        root = inorderSuccessor;
                        // Binary Search Tree is in correct shape again, node will be deleted by garbage collector anyway but do it here explicitly
                        current = null;
                        return true;
                    }

                    // Check whether inorderSuccessor is left or right successor of predecessor, and set predecessor reference accordingly
                    if(inorderSuccessor.getKey() < predecessor.getKey()) predecessor.setLeft(inorderSuccessor);
                    else predecessor.setRight(inorderSuccessor);

                    // Set predessors for inorder-successor
                    inorderSuccessor.setLeft(current.getLeft());
                    inorderSuccessor.setRight(current.getRight());
                    // Binary Search Tree is in correct shape again, node will be deleted by garbage collector anyway but do it here explicitly
                    current = null;
                    return true;
                } else {
                    // Case 2: Node has either right or left  successor but just one
                    removeOnePredecessor(current, predecessor);
                    // Binary Search Tree is in correct shape again, node will be deleted by garbage collector anyway but do it here explicitly
                    current = null;
                    return true;
                }
            }
            predecessor = current;
            if(toBeDeleted.getKey() < current.getKey()) current = current.getLeft(); // node is smaller -> check left successor
            else current = current.getRight();  // node must be bigger -> check right successor
        }
        // value has not been found
        return false;
    }

    /**
     * Removes a leaf node of tree given its predessor.
     * @param current node to be deleted from tree
     * @param predecessor predessor of node to be deleted
     */
    private void removeLeaf(Node current, Node predecessor){
        // Delete root node if there is no predecessor
        if(predecessor == null) {
            root = null;
            return;
        }
        // Check whether toBeDeleted node was left or right successor of predecessor node and set reference accordingly
        if(current.getKey() < predecessor.getKey()) predecessor.setLeft(null);
        else predecessor.setRight(null);
    }

    /**
     * Removes a node of tree with one successor given its predecessor.
     * @param current node to be deleted from tree
     * @param predecessor predessor of node to be deleted
     */
    private void removeOnePredecessor(Node current, Node predecessor){
        Node successor = null;
        if(current.getLeft() != null){
            // Node has a left successor
            successor = current.getLeft();
        } else {
            // Node has a right successor
            successor = current.getRight();
        }

        // If no predecessor --> root node --> successor will be the new root
        if(predecessor == null){
            root = successor;
            return;
        }

        // check whether the successor node is left or right of its predecessor
        if(successor.getKey() < predecessor.getKey()) predecessor.setLeft(successor);
        else predecessor.setRight(successor);
    }
}
