import java.util.Objects;

public class Node {

    private int key;
    private Node right;
    private Node left;
    private Object anyData;

    public Node(int key) {
        this(key, null);
    }

    public Node(int key, Object anyData) {
        this.key = key;
        this.anyData = anyData;
        this.left = null;
        this.right = null;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Object getAnyData() {
        return anyData;
    }

    public void setAnyData(Object anyData) {
        this.anyData = anyData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return key == node.key && Objects.equals(right, node.right) && Objects.equals(left, node.left) && Objects.equals(anyData, node.anyData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, right, left, anyData);
    }

    @Override
    public String toString() {
        return "Node{" +
                "key=" + key +
                ", right=" + right +
                ", left=" + left +
                ", anyData=" + anyData +
                '}';
    }
}
