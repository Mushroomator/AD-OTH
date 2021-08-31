public class Element {
    public int key;
    public Object data;

    public Element(int key, Object data) {
        this.key = key;
        this.data = data;
    }

    @Override
    public String toString() {
        // only print key
        return Integer.toString(key);
    }
}
