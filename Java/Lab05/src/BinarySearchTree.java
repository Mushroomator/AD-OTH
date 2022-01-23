import java.util.Stack;

public class BinarySearchTree {
    public BinaryNode root;

    public BinarySearchTree() {
    }

    public void insert(int key) {
        root = insertRec(root, key);
    }

    private BinaryNode insertRec(BinaryNode node, int key){
        if(node == null) return new BinaryNode(key);
        if(node.key == key) throw new IllegalArgumentException("Cannot insert %s. Node %s already in tree".formatted(key, key));
        else if(key < node.key) {
            node.left = insertRec(node.left, key);
            return node;
        }
        else{
            node.right = insertRec(node.right, key);
            return node;
        }
    }

    public void inorder(){
        inorder(root);
        System.out.println();
    }

    private void inorder(BinaryNode node){
        if(node == null) return;
        System.out.print("(");
        inorder(node.left);
        System.out.print(",");
        System.out.print(node.key);
        System.out.print(",");
        inorder(node.right);
        System.out.print(")");
    }
}
