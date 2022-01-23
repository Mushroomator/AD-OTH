public class BinaryTree {
    private BinaryNode root;

    public BinaryTree(BinaryNode root) {
        this.root = root;
    }

    public void inorder() {
        if (root == null) {
            System.out.println("No nodes in tree.");
            return;
        }
        inorder(root);
        System.out.println();
    }

    private void inorder(BinaryNode node) {
        if (node == null) return;
        System.out.print("(");
        inorder(node.left);
        System.out.print(",");
        System.out.print(node.key);
        System.out.print(",");
        inorder(node.right);
        System.out.print(")");
    }

    public void removeRec(int key) {
        removeRec(root, key);
    }

    private BinaryNode removeRec(BinaryNode node, int key) {
        if (node == null) {
            System.out.printf("Node %s not found in tree. Nothing has been removed%n", key);
            return null;
        }
        ;
        if (node.key == key) {
            BinaryNode ret;
            // node to be deleted has been found
            if (node.right == null && node.left == null) {
                ret = null;
            } else if (node.left == null) {
                // no left successor --> return right successor
                ret = node.right;
            } else if (node.right == null){
                // no right successor -> return left successor
                ret = node.left;
            }
            else {
                // two successors (--> there is an inorder successor)
                var inorderSuccessor = getInorderSuccessor(node);
                // inorder successor has at most 1 successor so can be deleted using one of the previous cases
                removeRec(node, inorderSuccessor.key);

                // set successor of inorderSuccessor to those of current node as the current node will be removed
                inorderSuccessor.left = node.left;
                inorderSuccessor.right = node.right;

                ret = inorderSuccessor;
            }
            return ret;
        } else if (key < node.key) {
            node.left = removeRec(node.left, key);
            return node;
        } else {
            node.right = removeRec(node.right, key);
            return node;
        }
    }

    /**
     * Get inorder successor of a node;
     * @param node any node within the tree
     * @return inorder successor of the given node, or null if there is non
     */
    public BinaryNode getInorderSuccessor(BinaryNode node) {
        if(node == null) throw new IllegalArgumentException("Node must not be null!");
        var next = node.right;
        // there is no inorder successor
        if (next == null) return null;
        while (next.left != null) next = next.left;
        return next;
    }
}


