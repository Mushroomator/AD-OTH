import java.util.function.Function;

public class HashtableLinearProbing extends HashtableOpenAdressing {

    public HashtableLinearProbing(Function<Integer, Integer> hashFunc) {
        this(INITIAL_SIZE, hashFunc);
    }

    public HashtableLinearProbing(int size, Function<Integer, Integer> hashFunc) {
        super(size, hashFunc);
    }

    @Override
    public int hashPosition(int key, int attempt) {
       return (hashFunc.apply(key) + attempt) % hashtable.length;
    }
}
