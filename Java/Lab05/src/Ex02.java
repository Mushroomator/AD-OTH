import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Lab 5: Exercise 2 for B. Sc. Business Information Technology
 */
public class Ex02 {

    public static void main(String[] args) {
        var inorder = new Integer[]{100, 25, 210, 40, 38, 5, 3, 1};
        var preorder = new Integer[]{40, 25, 100, 210, 38, 5, 3, 1};
        var postorder = new Integer[]{100, 210, 25, 1, 3, 5, 38, 40};

        // reconstruct from inorder + preorder
        var inPreTree = reconstructBinTreeFromPreAndInorder(inorder, preorder);
        System.out.println("""
                +-----------------------------------------------------------------+
                | Reconstructed binary tree from inorder and preorder traversal   |
                +-----------------------------------------------------------------+
                """);
        inPreTree.inorder();

        // reconstruct from inorder + postorder
        var inPostTree = reconstructBinTreeFromPostAndInorder(inorder, postorder);
        System.out.println("""
                
                +-----------------------------------------------------------------+
                | Reconstructed binary tree from inorder and postorder traversal  |
                +-----------------------------------------------------------------+
                """);
        inPostTree.inorder();



        var preorderBst = new Integer[]{40, 25, 5, 3, 1, 38, 100, 210};
        var postorderBst = new Integer[]{1, 3, 5, 38, 25, 210, 100, 40};

        System.out.println("""
                
                +-----------------------------------------------------------------+
                | Reconstructed binary search tree (BST) from preorder traversal  |
                +-----------------------------------------------------------------+
                """);
        var preBst = reconstructBstFromPreorder(preorderBst);
        preBst.inorder();

        System.out.println("""
                
                +-----------------------------------------------------------------+
                | Reconstructed binary search tree (BST) from postorder traversal |
                +-----------------------------------------------------------------+
                """);
        var postBst = reconstructBstFromPostorder(postorderBst);
        postBst.inorder();
    }

    /**
     * Reconstruct a binary tree given inorder and postorder traversals.
     *
     * @implNote Implementation does not check if the elements actually form a tree.
     *
     * @param inorder inorder traversal
     * @param postorder postorder traversal
     * @return reconstructed binary tree
     */
    public static BinaryTree reconstructBinTreeFromPostAndInorder(Integer[] inorder, Integer[] postorder) {
        if (inorder.length != postorder.length)
            throw new IllegalArgumentException("Inorder and preorder must contain the same values");

        // Put values and their position within the array into the hashmap
        // Enables to get position in recursive function in O(1)
        var inorderPositions = new HashMap<Integer, Integer>();
        for (int i = 0; i < inorder.length; i++) inorderPositions.put(inorder[i], i);

        // check all values are actually in both arrays
        if (Arrays.stream(postorder).anyMatch(it -> !inorderPositions.containsKey(it)))
            throw new IllegalArgumentException("Inorder and preorder must contain the same values");

        // it is important to use AtomicInteger here as the value needs to be mutated/ accessed across calls
        var root = buildTreePostorder(postorder, 0, postorder.length - 1, new AtomicInteger(postorder.length - 1), inorderPositions);
        return new BinaryTree(root);
    }

    /**
     * Reconstruct a binary tree given inorder and preorder traversals.
     *
     * @implNote Implementation does not check if the elements actually form a tree.
     *
     * @param inorder inorder traversal
     * @param preorder preorder traversal
     * @return reconstructed binary tree
     */
    public static BinaryTree reconstructBinTreeFromPreAndInorder(Integer[] inorder, Integer[] preorder) {
        if (inorder.length != preorder.length)
            throw new IllegalArgumentException("Inorder and preorder must contain the same values");

        // Put values and their position within the array into the hashmap
        // Enables to get position in recursive function in O(1)
        var inorderPositions = new HashMap<Integer, Integer>();
        for (int i = 0; i < inorder.length; i++) inorderPositions.put(inorder[i], i);

        // check all values are actually in both arrays
        if (Arrays.stream(preorder).anyMatch(it -> !inorderPositions.containsKey(it)))
            throw new IllegalArgumentException("Inorder and preorder must contain the same values");

        // it is important to use AtomicInteger here as the value needs to be mutated/ accessed across calls
        var root = buildTreePreorder(preorder, 0, preorder.length - 1, new AtomicInteger(0), inorderPositions);
        return new BinaryTree(root);
    }

    /**
     * Builds partial tree from inorder and postorder traversal in constant time Θ(1).
     *
     * @implNote recursive
     *
     * @param preorder preorder traversal
     * @param first first index
     * @param last last index
     * @param rootIdx index of root element (in preorder traversal)
     * @param inorderPositions map containing mapping from elements in inorder/ preorder traversal and their positions in inorder traversal
     * @return partial tree with root node (key= preorder[rootIdx])
     */
    private static BinaryNode buildTreePreorder(Integer[] preorder, int first, int last, AtomicInteger rootIdx, HashMap<Integer, Integer> inorderPositions) {
        // no more nodes to process --> return null as caller sets left/ right successor to return value
        if (first > last) return null;

        // get key value for root
        var rootVal = preorder[rootIdx.get()];
        // create new node for the root node
        var root = new BinaryNode(rootVal);
        // increase counter for root
        rootIdx.addAndGet(1);

        // no children --> return node
        if (first == last) return root;

        // get position of that root in inorder traversal
        var posInorder = inorderPositions.get(rootVal);

        // build tree left of the current root and set left successor of root to that partial tree
        root.left = buildTreePreorder(preorder, first, posInorder - 1, rootIdx, inorderPositions);
        // build tree right of the current root and set right successor of root to that partial tree
        root.right = buildTreePreorder(preorder, posInorder + 1, last, rootIdx, inorderPositions);
        // successors have been set
        return root;
    }

    /**
     * Builds tree in constant time Θ(1).
     *
     * @implNote Identical to building the tree from inorder and preorder traversal except for three things:
     * - Start at end of postorder array as root nodes are written there in reverse order
     * - Decrease the rootIdx by one in each recursive call
     * - As nodes are written in postorder from right to left, change order of recursive calls so that right successors are processed before left successor
     *
     * @param postorder postorder traversal
     * @param first first index
     * @param last last index
     * @param rootIdx index of root element (in postorder traversal)
     * @param inorderPositions map containing mapping from elements in inorder/ preorder traversal and their positions in inorder traversal
     * @return partial tree with root node (key= postorder[rootIdx])
     */
    private static BinaryNode buildTreePostorder(Integer[] postorder, int first, int last, AtomicInteger rootIdx, HashMap<Integer, Integer> inorderPositions) {
        // no more nodes to process --> return null as caller sets left/ right successor to return value
        if (first > last) return null;

        // get key value for root
        var rootVal = postorder[rootIdx.get()];
        // create new node for the root node
        var root = new BinaryNode(rootVal);
        // increase counter for root
        rootIdx.addAndGet(-1);

        // no children --> return node
        if (first == last) return root;

        // get position of that root in inorder traversal
        var posInorder = inorderPositions.get(rootVal);

        // build tree right of the current root and set right successor of root to that partial tree
        root.right = buildTreePostorder(postorder, posInorder + 1, last, rootIdx, inorderPositions);
        // build tree left of the current root and set left successor of root to that partial tree
        root.left = buildTreePostorder(postorder, first, posInorder - 1, rootIdx, inorderPositions);
        // successors have been set
        return root;
    }

    /**
     * Reconstructs a binary search tree (BST) from preorder traversal.
     *
     * @implNote Implementation does not check if the elements actually form a tree.
     *
     * @param preorder preorder traversal
     * @return reconstructed BST
     */
    public static BinarySearchTree reconstructBstFromPreorder(Integer[] preorder){
        var tree = new BinarySearchTree();
        for (var cur: preorder) tree.insert(cur);
        return tree;
    }

    /**
     * Reconstructs a binary search tree (BST) from postorder traversal.
     *
     * @implNote Implementation does not check if the elements actually form a tree.
     *
     * @param postorder postorder traversal
     * @return reconstructed BST
     */
    public static BinarySearchTree reconstructBstFromPostorder(Integer[] postorder){
        var tree = new BinarySearchTree();
        for (int i = postorder.length - 1; i >= 0; i--) tree.insert(postorder[i]);
        return tree;
    }

}