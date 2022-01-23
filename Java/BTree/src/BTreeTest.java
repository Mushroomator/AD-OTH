public class BTreeTest {
    public static void main(String[] args) {
        var btree = new BTree<Integer>(4);
        btree.print();
        btree.insert(12, new Object());
        btree.print();
        btree.insert(13, new Object());
        btree.print();
        btree.insert(14, new Object());
        btree.print();
        btree.insert(15, new Object());
        btree.print();
    }
}
