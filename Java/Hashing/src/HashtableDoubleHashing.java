import java.util.function.Function;

public class HashtableDoubleHashing extends HashtableOpenAdressing {
    private Function<Integer, Integer> secondHashFunction;

    public HashtableDoubleHashing(Function<Integer, Integer> hashFunc, Function<Integer, Integer> secondHashFunction) {
        this(INITIAL_SIZE, hashFunc, secondHashFunction);
    }

    public HashtableDoubleHashing(int size, Function<Integer, Integer> hashFunc, Function<Integer, Integer> secondHashFunction) {
        super(size, hashFunc);
        this.secondHashFunction = secondHashFunction;
    }

    @Override
    public int hashPosition(int key, int attempt) {
        return (hashFunc.apply(key) + attempt * secondHashFunction.apply(key)) % hashtable.length;
    }
}
