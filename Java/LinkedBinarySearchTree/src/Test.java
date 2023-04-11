import java.util.Arrays;

public class Test {

    public static void main(String[] args){
        // generate random values
        int[] randValues = fillArrayRand(20, 1, 200);
        // pre-defined values
        int[] values = new int[]{35, 12, 45, 23, 10, 15, 85};

        // values to insert into the tree
        int[] curVals = randValues;

        System.out.println("""
                --------------------------------------
                | Linked binary search tree
                --------------------------------------
                """);

        // Create tree
        BinarySearchTree tree = new BinarySearchTree();
        System.out.println("Tree has been created.");

        // fill tree
        for (var cur: curVals) {
            tree.insertIter(new Node(cur));
        }
        System.out.println("Tree was filled with the following values: %s".formatted(Arrays.toString(curVals)));
        tree.printRec(Traversal.INORDER);

        var sorted = Arrays.stream(curVals).sorted().distinct().toArray();
        // always start at root node
        var startNode = tree.find(curVals[0]);

        for (int i = 0; i < sorted.length; i++) {
            var result = tree.findNthLargestKey(startNode, sorted.length - i);
            // if key in i-th position of sorted array is the same as the get of result => success

            System.out.printf("#%d largest value:\t%d (expected)\t-\t%s (result)\t", sorted.length - i, sorted[i], result == null ? "not found": result.getKey());
            if (result != null && sorted[i] == result.getKey()) {
                System.out.println("SUCCESS");
            } else System.out.println("FAILED");
        }

//        System.out.println("""
//
//                --------------------------------------
//                | Test printing of binary tree
//                --------------------------------------
//                """);
//
//        // print tree recursively
//        System.out.println("Recursive Inorder:");
//        tree.printRec(Traversal.INORDER);
//        System.out.println("Recursive Preorder:");
//        tree.printRec(Traversal.PREORDER);
//        System.out.println("Recursive Postorder:");
//        tree.printRec(Traversal.POSTORDER);
//
//        // Print tree iteratively
//        System.out.println("Iterative Inorder:");
//        tree.printIterInorder();
//        System.out.println("Iterative Preorder:");
//        tree.printIterPreorder();
//
//        System.out.println("""
//
//                --------------------------------------
//                | Test deletion of nodes form binary tree
//                --------------------------------------
//                """);
//        System.out.println("Before deletion: ");
//        tree.printRec(Traversal.INORDER); // Expected: (((n,10,n),12,((n,15,n),23,n)),35,(n,45,(n,85,n)))
//
//        // delete from tree
//        Node toBeDeleted = new Node(12); // 12 is an inner node with two successors
//        System.out.println("Deleting node %d".formatted(toBeDeleted.getKey()));
//        tree.removeRec(toBeDeleted);
//        System.out.println("Result after delete:");
//        tree.printRec(Traversal.INORDER); // Expected: (((n,10,n),15,(n,23,n)),35,(n,45,(n,85,n)))
//
//
//
//        toBeDeleted.setKey(35); // 35 is root node with two successors
//        System.out.println("Deleting node %d".formatted(toBeDeleted.getKey()));
//        tree.removeRec(toBeDeleted);
//        System.out.println("Result after delete:");
//        tree.printRec(Traversal.INORDER); // Expected: (((n,10,n),15,(n,23,n)),45,(n,85,n))

        /*
        toBeDeleted.setKey(23); // 23 is a leaf node at this point
        System.out.println("Deleting node %d".formatted(toBeDeleted.getKey()));
        tree.removeRec(toBeDeleted);
        System.out.println("Result after delete:");
        tree.printRec(Traversal.INORDER); // Expected: (((n,10,n),15,n),45,(n,85,n))

        toBeDeleted.setKey(15); // 15 is an inner node with one successor at this point
        System.out.println("Deleting node %d".formatted(toBeDeleted.getKey()));
        tree.removeRec(toBeDeleted);
        System.out.println("Result after delete:");
        tree.printRec(Traversal.INORDER); // Expected: ((n,10,n),45,(n,85,n))
        */

    }

    public static int[] fillArrayRand(int size, int randStart, int randEnd){
        int[] randArray = new int[size];
        for(int i = 0; i < size; i++){
            randArray[i] = (int)( (randEnd - randStart) * Math.random() + randStart);
        }
        return randArray;
    }
}
