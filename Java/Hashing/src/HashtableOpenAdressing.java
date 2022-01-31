import java.util.Arrays;
import java.util.function.Function;

public abstract class HashtableOpenAdressing {
    protected Function<Integer, Integer> hashFunc;
    protected Integer[] hashtable;
    protected int size;
    protected static final int INITIAL_SIZE = 11;

    public HashtableOpenAdressing(int size, Function<Integer, Integer> hashFunc) {
        this.hashtable = new Integer[size];
        this.hashFunc = hashFunc;
    }

    public HashtableOpenAdressing(Function<Integer, Integer> hashFunc) {
        this(INITIAL_SIZE, hashFunc);
    }

    public boolean put(int key, int value, boolean print){
        // this is not the way one would implement a hashtable. Resizing should happen after hashtable is filled to at most 75%
        // but to solve specific exercises it is required that resizing only happens if it is absolutely necessary as no more values can be fitted into the array
        if(size == hashtable.length) hashtable = Arrays.copyOf(hashtable, size << 1);
        int attempt = 0, position = 0;
        if(print) System.out.printf("> Inserting %d...%n", key);
        while (true) {
            position = hashPosition(key, attempt);
            if(print) System.out.printf("\tAttempting to place at %d... ", position);
            if (isOccupied(position)) {
                if(print) System.out.println("Occupied!");
                if(value == hashtable[position]) {
                    if(print) System.out.printf("Value %s already in hashtable! (Failed attempts: %d)%n", attempt + 1, value);
                    return false;
                }
                attempt++;
            } else {
                hashtable[position] = value;
                if(print) {
                    System.out.printf("Success!\n\tFailed attempts: %d%n", attempt);
                }
                return true;
            }
        }

    }

    public boolean isOccupied(int position){
        return hashtable[position] != null;
    }

    public abstract int hashPosition(int key, int attempt);

    @Override
    public String toString() {
        return Arrays.toString(hashtable);
    }
}
