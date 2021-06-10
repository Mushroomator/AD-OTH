import java.util.Arrays;

public class Test {

    public static void main(String[] args){
        // generate random values
        int[] randValues = fillArrayRand(5, 1, 10);
        // pre-defined values
        int[] values = new int[]{35, 12, 45, 23, 10, 15, 85};

        // values to insert into the tree
        int[] curVals = values;

        System.out.println("""
                --------------------------------------
                | Linked binary search tree
                --------------------------------------
                """);

        // Create tree
        LinkedBinaryTree tree = new LinkedBinaryTree();
        System.out.println("Tree has been created.");

        // fill tree
        for (var cur: curVals) {
            tree.insertIter(new Node(cur));
        }
        System.out.println("Tree was filled with the following values: %s".formatted(Arrays.toString(curVals)));

        System.out.println("""
                
                --------------------------------------
                | Test printing of binary tree
                --------------------------------------
                """);

        // print tree recursively
        System.out.println("Recursive Inorder:");
        tree.printRecursive(Traversal.INORDER);
        System.out.println("Recursive Preorder:");
        tree.printRecursive(Traversal.PREORDER);
        System.out.println("Recursive Postorder:");
        tree.printRecursive(Traversal.POSTORDER);

        // Print tree iteratively
        System.out.println("Iterative Inorder:");
        tree.printIterInorder();
        System.out.println("Iterative Preorder:");
        tree.printIterPreorder();

        System.out.println("""
                
                --------------------------------------
                | Test deletion of nodes form binary tree
                --------------------------------------
                """);
        System.out.println("Before deletion: ");
        tree.printRecursive(Traversal.INORDER); // Expected: (((n,10,n),12,((n,15,n),23,n)),35,(n,45,(n,85,n)))

        // delete from tree
        Node toBeDeleted = new Node(12); // 12 is an inner node with two successors
        System.out.println("Deleting node %d".formatted(toBeDeleted.getKey()));
        tree.delete(toBeDeleted);
        System.out.println("Result after delete:");
        tree.printRecursive(Traversal.INORDER); // Expected: (((n,10,n),15,(n,23,n)),35,(n,45,(n,85,n)))


        toBeDeleted.setKey(35); // 35 is root node with two successors
        System.out.println("Deleting node %d".formatted(toBeDeleted.getKey()));
        tree.delete(toBeDeleted);
        System.out.println("Result after delete:");
        tree.printRecursive(Traversal.INORDER); // Expected: (((n,10,n),15,(n,23,n)),45,(n,85,n))

        toBeDeleted.setKey(23); // 23 is a leaf node at this point
        System.out.println("Deleting node %d".formatted(toBeDeleted.getKey()));
        tree.delete(toBeDeleted);
        System.out.println("Result after delete:");
        tree.printRecursive(Traversal.INORDER); // Expected: (((n,10,n),15,n),45,(n,85,n))

        toBeDeleted.setKey(15); // 15 is an inner node with one successor at this point
        System.out.println("Deleting node %d".formatted(toBeDeleted.getKey()));
        tree.delete(toBeDeleted);
        System.out.println("Result after delete:");
        tree.printRecursive(Traversal.INORDER); // Expected: ((n,10,n),45,(n,85,n))

    }

    public static int[] fillArrayRand(int size, int randStart, int randEnd){
        int[] randArray = new int[size];
        for(int i = 0; i < size; i++){
            randArray[i] = (int)( (randEnd - randStart) * Math.random() + randStart);
        }
        return randArray;
    }
}
