import java.util.Objects;

public class AvlNode {

    private int height;
    private AvlNode left;
    private AvlNode right;
    private int key;
    private Object anyData;

    public AvlNode(int key) {
        this.key = key;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public AvlNode getLeft() {
        return left;
    }

    public void setLeft(AvlNode left) {
        this.left = left;
    }

    public AvlNode getRight() {
        return right;
    }

    public void setRight(AvlNode right) {
        this.right = right;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
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
        AvlNode node = (AvlNode) o;
        return height == node.height && key == node.key && Objects.equals(left, node.left) && Objects.equals(right, node.right) && Objects.equals(anyData, node.anyData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, left, right, key, anyData);
    }

    @Override
    public String toString() {
        return "Node{" +
                "height=" + height +
                ", left=" + left +
                ", right=" + right +
                ", key=" + key +
                ", anyData=" + anyData +
                '}';
    }
}
