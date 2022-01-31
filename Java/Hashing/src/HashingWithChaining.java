import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.function.Function;

public class HashingWithChaining<K, V> {
    private ArrayList<LinkedList<V>> hashtable;
    private Function<K, Integer> hashFunc;
    private static final int INITIAL_SIZE = 11;


    public HashingWithChaining(Function<K, Integer> hashFunc) {
        this(INITIAL_SIZE, hashFunc);
    }

    public HashingWithChaining(int size, Function<K, Integer> hashFunc) {
        this.hashtable = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            hashtable.add(new LinkedList<>());
        }
        this.hashFunc = hashFunc;
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public boolean put(K key, V value){
        if(key == null) throw new IllegalArgumentException("Key must not be null!");
        int pos = hashFunc.apply(key) % hashtable.size();
        if (!hashtable.get(pos).contains(value)) {
            hashtable.get(pos).addLast(value);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (int i = 0; i < hashtable.size(); i++) {
            sb.append("%3d".formatted(i))
                    .append("  ")
                    .append(hashtable.get(i).toString())
                    .append("\n");
        }
        return sb.toString();
    }
}
