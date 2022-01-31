import java.util.function.Function;

public class HashtableQuadraticProbing extends HashtableOpenAdressing {
    private int c1;
    private int c2;

    public HashtableQuadraticProbing(Function<Integer, Integer> hashFunc, int c1, int c2) {
        this(INITIAL_SIZE, hashFunc, c1, c2);
    }

    public HashtableQuadraticProbing(int size, Function<Integer, Integer> hashFunc, int c1, int c2) {
        super(size, hashFunc);
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public int hashPosition(int key, int attempt) {
        return (hashFunc.apply(key) + attempt * c1 + attempt * attempt * c2) % hashtable.length;
    }
}
